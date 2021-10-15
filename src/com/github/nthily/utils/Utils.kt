package com.github.nthily.utils

import java.io.BufferedInputStream
import javax.sound.sampled.AudioSystem
import javax.swing.JLabel

object Utils {

    fun playAudio(audioName: String) {
        /*
        val audio = this.javaClass.getResourceAsStream("/sounds/$audioName")
        val clip = AudioSystem.getClip()
        val inputStream = AudioSystem.getAudioInputStream(audio)
         */
        // https://stackoverflow.com/questions/5529754/java-io-ioexception-mark-reset-not-supported
        val audio = this.javaClass.getResourceAsStream("/sounds/$audioName")
        val clip = AudioSystem.getClip()
        val bufferedIn = BufferedInputStream(audio!!)
        val audioStream = AudioSystem.getAudioInputStream(bufferedIn)
        clip.open(audioStream)
        clip.start()
    }

    val comboList = mutableListOf<JLabel>()
    var comboCount = 0

    fun updateComboList() {
        comboCount++
        comboList.forEachIndexed { _, jLabel ->
            jLabel.text = comboCount.toString() + "x"
        }
    }

    fun hideComboList() {
        comboList.forEachIndexed { _, jLabel ->
            jLabel.isVisible = false
        }
    }

    fun showComboList() {
        comboList.forEachIndexed { _, jLabel ->
            jLabel.isVisible = true
        }
    }

}
