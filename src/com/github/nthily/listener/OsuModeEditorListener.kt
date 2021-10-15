package com.github.nthily.listener

import com.github.nthily.state.AppSettingsState
import com.github.nthily.utils.Utils.comboCount
import com.github.nthily.utils.Utils.comboList
import com.github.nthily.utils.Utils.playAudio
import com.github.nthily.utils.Utils.updateComboList
import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener
import com.intellij.psi.PsiDocumentManager
import java.awt.Color
import java.awt.Font
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.*
import kotlin.random.Random

/*
    IDE 的编辑器界面监听
    IDE's Editor Interface Listening
 */

class OsuModeEditorListener: EditorFactoryListener {

    override fun editorCreated(event: EditorFactoryEvent) {

        // 判断是否为代码区域
        // Determine if it is a code area

        val fileType = event.editor.project?.let { PsiDocumentManager.getInstance(it).getPsiFile(event.editor.document)?.originalFile?.virtualFile?.fileSystem?.protocol }

        if(fileType == "file") {

            val settings = AppSettingsState.instance
            val editor = event.editor.contentComponent

            val combo = JLabel("0x")

            comboList.add(combo)

            combo.setSize(400, 400)
            combo.verticalAlignment = SwingConstants.TOP
            combo.horizontalAlignment = SwingConstants.RIGHT
            combo.font = Font("Seria", Font.BOLD, 60)
            combo.foreground = Color.WHITE
            combo.isVisible = settings.enableMode && settings.enableComboCounter

            editor.add(combo)

            event.editor.scrollingModel.addVisibleAreaListener(OsuModeVisibleAreaListener(combo))

            editor.addKeyListener(object : KeyListener {
                override fun keyTyped(e: KeyEvent?) {
                    if (settings.enableMode && settings.enableKeyboardSound)
                        playAudio("${Random.nextInt(1, 6)}.wav")
                    if (settings.enableMode && settings.enableComboCounter)
                        updateComboList()
                }

                override fun keyPressed(e: KeyEvent?) { }

                override fun keyReleased(e: KeyEvent?) {
                    if (settings.enableMode && settings.enableKeyboardSound) {
                        when(e?.keyCode) {
                            KeyEvent.VK_BACK_SPACE -> {
                                comboCount = 0
                                combo.text = comboCount.toString() + "x"
                                playAudio("back_space.wav")
                            }
                            KeyEvent.VK_SPACE -> playAudio("space.wav")
                            KeyEvent.VK_ENTER -> playAudio("enter.wav")
                        }
                    }
                }
            })
        }
    }

    override fun editorReleased(event: EditorFactoryEvent) {
        event.editor.contentComponent.removeAll()
    }

}
