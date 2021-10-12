package com.github.nthily.utils

import javax.sound.sampled.AudioSystem

object Utils {
    fun playAudio(audioName: String) {
        val audio = this.javaClass.getResourceAsStream("/sounds/$audioName")
        val clip = AudioSystem.getClip()
        val inputStream = AudioSystem.getAudioInputStream(audio)
        clip.open(inputStream)
        clip.start()
    }
}