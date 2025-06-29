package com.github.catomon.tsukaremi.ui.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember

class LazyListStateHijacker(
    private val listState: LazyListState
) {
    private val scrollPositionField = listState.javaClass
        .getDeclaredField("scrollPosition").apply {
            isAccessible = true
        }

    private val scrollPositionObj = scrollPositionField.get(listState)

    private val lastKeyRemover: () -> Unit = scrollPositionField.type
        .getDeclaredField("lastKnownFirstItemKey").run {
            isAccessible = true

            fun() { set(scrollPositionObj, null) }
        }

    private val indexField = scrollPositionField.type
        .getDeclaredField("index\$delegate").apply {
            isAccessible = true
        }

    init {
        setProps()
    }

    private fun setProps() {
        val mutableIntState = IntStateHijacker(
            state = mutableIntStateOf(listState.firstVisibleItemIndex),
            keyRemover = lastKeyRemover
        )

        indexField.set(scrollPositionObj, mutableIntState)
    }
}

class IntStateHijacker(
    private val state: MutableIntState,
    private val keyRemover: () -> Unit
) : MutableIntState by state {
    override var intValue: Int
        get() {
            keyRemover()
            return state.intValue
        }
        set(value) {
            state.intValue = value
        }

    override var value: Int
        get() {
            keyRemover()
            return state.value
        }
        set(value) {
            state.value = value
        }
}

@Composable
fun rememberLazyListStateHijacker(
    listState: LazyListState
): LazyListStateHijacker {
    return remember(listState) {
        LazyListStateHijacker(listState)
    }
}