package com.github.catomon.tsukaremi.util

import com.sun.jna.Native
import com.sun.jna.platform.win32.Shell32
import com.sun.jna.ptr.IntByReference

interface MyShell32 : Shell32 {
    fun SHQueryUserNotificationState(int: IntByReference): Int

    companion object {
        val INSTANCE = Native.load("shell32", MyShell32::class.java)

        const val QUNS_NOT_PRESENT = 1
        const val QUNS_BUSY = 2
        const val QUNS_RUNNING_D3D_FULL_SCREEN = 3
        const val QUNS_PRESENTATION_MODE = 4
        const val QUNS_ACCEPTS_NOTIFICATIONS = 5
        const val QUNS_QUIET_TIME = 6
        const val QUNS_APP = 7
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
        return quns == MyShell32.QUNS_ACCEPTS_NOTIFICATIONS ||
                quns == MyShell32.QUNS_APP
    }
    return false
}

fun shouldShowNotification(): Boolean {
    return isNotificationAllowed() && canAlwaysOnTop()
}
