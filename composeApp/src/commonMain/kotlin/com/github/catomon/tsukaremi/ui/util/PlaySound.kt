package com.github.catomon.tsukaremi.ui.util

import com.github.catomon.tsukaremi.util.logWarn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

suspend fun playSound(fileName: String) {
    try {
        withContext(Dispatchers.IO) {
            val inputStream = Unit.javaClass.getResourceAsStream("/composeResources/tsukaremi.composeapp.generated.resources/files/$fileName")
            if (inputStream != null) {
                val bufferedIn = BufferedInputStream(inputStream)
                val audioInputStream = AudioSystem.getAudioInputStream(bufferedIn)
                val clip: Clip = AudioSystem.getClip()
                clip.open(audioInputStream)
                clip.start()
            } else {
                logWarn("Audio resource not found")
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}