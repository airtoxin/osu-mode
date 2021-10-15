package com.github.nthily.listener

import com.intellij.openapi.editor.event.VisibleAreaEvent
import com.intellij.openapi.editor.event.VisibleAreaListener
import com.intellij.openapi.editor.ex.util.EditorScrollingPositionKeeper
import groovy.xml.Entity.not
import javax.swing.JLabel

/*
    监听编辑器区域大小变化
    Listening for editor area size changes
 */

class OsuModeVisibleAreaListener(private val combo: JLabel): VisibleAreaListener {

    override fun visibleAreaChanged(event: VisibleAreaEvent) {
        combo.setLocation(event.newRectangle.width - combo.width - 30, event.newRectangle.location.y + 10)
    }
}
