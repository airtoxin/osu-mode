package com.github.nthily.listener

import com.github.nthily.state.AppSettingsState
import com.github.nthily.utils.Utils.playAudio
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener

class OsuModeDocumentListener: DocumentListener {

    private val settings = AppSettingsState.instance
    private var before = 0

    override fun documentChanged(event: DocumentEvent) {

        if(settings.enableMode && settings.enableInputSound) {
            val current = event.document.textLength
            if(before == current - 1 || (current - before >= 15)) playAudio("click.wav")
        }
    }

    override fun beforeDocumentChange(event: DocumentEvent) {
        before = event.document.text.length
    }

}
