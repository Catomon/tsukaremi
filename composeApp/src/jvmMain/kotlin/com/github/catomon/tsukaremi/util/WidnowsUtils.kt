package com.github.catomon.tsukaremi.util

import com.sun.jna.Native
import com.sun.jna.platform.win32.Shell32
import com.sun.jna.ptr.IntByReference

interface MyShell32 : Shell32 {
    fun SHQueryUserNotificationState(int: IntByReference): Int

    companion object {
        val INSTANCE = Native.load("shell32", MyShell32::class.java)

        var QUNS_NOT_PRESENT: Int = 1
        var QUNS_BUSY: Int = 2
        var QUNS_RUNNING_D3D_FULL_SCREEN: Int = 3
        var QUNS_PRESENTATION_MODE: Int = 4
        var QUNS_ACCEPTS_NOTIFICATIONS: Int = 5
        var QUNS_QUIET_TIME: Int = 6
    }
}

fun isNotificationAllowed(): Boolean {
    return true
}

fun canAlwaysOnTop(): Boolean {
    val state = IntByReference()
    val hr: Int = MyShell32.INSTANCE.SHQueryUserNotificationState(state)
    if (hr == 0) { // S_OK
        val quns = state.value
        return quns !in listOf(
            MyShell32.QUNS_RUNNING_D3D_FULL_SCREEN,
            MyShell32.QUNS_PRESENTATION_MODE,
            MyShell32.QUNS_BUSY
        )
    }

    return false
}