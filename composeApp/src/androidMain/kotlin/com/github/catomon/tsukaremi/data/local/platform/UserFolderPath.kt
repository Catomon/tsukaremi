package com.github.catomon.tsukaremi.data.local.platform

import com.github.catomon.tsukaremi.AppActivity
import com.github.catomon.tsukaremi.mainActivityContext
import java.io.File

actual val userFolderPath: String = File((mainActivityContext as AppActivity).filesDir.toURI()).absolutePath