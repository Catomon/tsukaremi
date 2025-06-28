package com.github.catomon.tsukaremi.data.local.platform

import com.github.catomon.tsukaremi.TsukaremiApp
import com.github.catomon.tsukaremi.appContext
import java.io.File

actual val userFolderPath: String = File((appContext as TsukaremiApp).filesDir.toURI()).absolutePath