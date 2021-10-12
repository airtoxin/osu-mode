package com.github.nthily.ui

import com.github.nthily.state.AppSettingsState
import com.intellij.openapi.options.BoundConfigurable
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBTextField
import com.intellij.ui.layout.CellBuilder
import com.intellij.ui.layout.panel
import com.intellij.ui.layout.Row
import javax.swing.JLabel

/*
    实现设置界面
    Implementation of the settings interface
 */

class Settings: BoundConfigurable(
    displayName = "Osu! Mode",
    "osu mode"
) {

    private val settings = AppSettingsState.instance


    private lateinit var modeCheckBox: CellBuilder<JBCheckBox>
    private lateinit var comboCounterCheckBox: CellBuilder<JBCheckBox>
    private lateinit var cursorExplosionsCheckBox: CellBuilder<JBCheckBox>

    private lateinit var inputSoundCheckBox: CellBuilder<JBCheckBox>
    private lateinit var breakSoundCheckBox: CellBuilder<JBCheckBox>
    private lateinit var openProjectCheckBox: CellBuilder<JBCheckBox>
    private lateinit var closeProjectCheckBox: CellBuilder<JBCheckBox>

    private lateinit var explosion: CellBuilder<JBTextField>
    private lateinit var explosionLabel: CellBuilder<JLabel>
    private lateinit var explosionComment: CellBuilder<JLabel>

    private var panel = panel { // Kotlin UI DSL

        titledRow("General") {
            row { modeCheckBox = checkBox("Enable Osu! Mode") }
            row { comboCounterCheckBox = checkBox("Enable Combo Counter") }
            row { cursorExplosionsCheckBox = checkBox("Enable Cursor Explosions") }
            row {
                cell {
                    explosionLabel = label("Explosion size:")
                    explosion = intTextField(
                        getter = { 6 },
                        setter = { },
                        range = IntRange(1, 10)
                    )
                }
                row {
                    explosionComment = comment("The size of the explosions. For value X, the height is set to X rem and the width to X ch")
                }
            }
        }

        titledRow("Audio") {
            row { inputSoundCheckBox = checkBox("Enable Input Sound") }
            row { breakSoundCheckBox = checkBox("Enable Break Sound") }
            row { openProjectCheckBox = checkBox("Enable Open Project Sound") }
            row { closeProjectCheckBox = checkBox("Enable Close Project Sound") }
        }
        titledRow("Other") {
            row {
                cell {
                    label("Github:")
                    browserLink("intellij osu mode plugin", "https://github.com/Nthily/intellij-osu-mode-plugin")
                }
            }
            row {
                cell {
                    label("Author:")
                    label("Nthily")
                }
            }
            row {
                cell {
                    label("Version:")
                    label("1.0")
                }
            }
        }
    }

    override fun createPanel(): DialogPanel {
        return panel
    }

    override fun isModified(): Boolean {

        /*
            重写 isModified 方法，当控件状态改变时，ide 会调用此方法, 如果返回 true, apply 按钮将可以被点击
            Override the isModified method so that ide will call this method when the com.github.nthily.state of the control changes
            and if it returns true, apply button will be clickable.
         */

        val explosionValue = getExplosionValue()
        if(!modeCheckBoxIsSelected()) {
            cursorExplosionsCheckBox.enabled(false)
            comboCounterCheckBox.enabled(false)
            explosion.enabled(false)
            explosionLabel.enabled(false)
            explosionComment.enabled(false)
            inputSoundCheckBox.enabled(false)
            breakSoundCheckBox.enabled(false)
            openProjectCheckBox.enabled(false)
            closeProjectCheckBox.enabled(false)
        } else {
            cursorExplosionsCheckBox.enabled(true)
            comboCounterCheckBox.enabled(true)
            explosion.enabled(true)
            explosionLabel.enabled(true)
            explosionComment.enabled(true)
            inputSoundCheckBox.enabled(true)
            breakSoundCheckBox.enabled(true)
            openProjectCheckBox.enabled(true)
            closeProjectCheckBox.enabled(true)
        }

        return (
                (settings.enableMode != modeCheckBoxIsSelected() ||
                settings.enableCursorExplosions != cursorExplosionsCheckBoxIsSelected() ||
                settings.enableComboCounter != comboCounterCheckBoxIsSelected() ||
                settings.enableInputSound != inputSoundCheckBoxIsSelected() ||
                settings.enableBreakSound != breakSoundCheckBoxIsSelected() ||
                settings.enableOpenProjectSound != openProjectSoundCheckBoxIsSelected() ||
                settings.enableCloseProjectSound != closeProjectSoundCheckBoxIsSelected() ||
                settings.explosion != explosionValue) && explosionIsValid()
        )
    }

    override fun apply() {

        settings.enableMode = modeCheckBoxIsSelected()
        settings.enableCursorExplosions = cursorExplosionsCheckBoxIsSelected()
        settings.enableComboCounter = comboCounterCheckBoxIsSelected()

        settings.enableInputSound = inputSoundCheckBoxIsSelected()
        settings.enableBreakSound = breakSoundCheckBoxIsSelected()
        settings.enableOpenProjectSound = openProjectSoundCheckBoxIsSelected()
        settings.enableCloseProjectSound = closeProjectSoundCheckBoxIsSelected()

        settings.explosion = getExplosionValue()
    }

    override fun reset() {

        /*
            在初始化界面的时候，会执行一次此函数，如果要将上面写的控件的值与本地存储的值对应，请在此函数中初始化控件的值
            This function is executed once when the interface is initialized.
            If you want to correspond the value of the control written above to the locally stored value, initialize the value of the control in this function
         */

        modeCheckBox.component.isSelected = settings.enableMode
        cursorExplosionsCheckBox.component.isSelected = settings.enableCursorExplosions
        comboCounterCheckBox.component.isSelected = settings.enableComboCounter

        inputSoundCheckBox.component.isSelected = settings.enableInputSound
        breakSoundCheckBox.component.isSelected = settings.enableBreakSound
        openProjectCheckBox.component.isSelected = settings.enableOpenProjectSound
        closeProjectCheckBox.component.isSelected = settings.enableCloseProjectSound

        explosion.component.text = settings.explosion
    }

    private fun modeCheckBoxIsSelected(): Boolean { return modeCheckBox.component.isSelected }
    private fun cursorExplosionsCheckBoxIsSelected(): Boolean { return cursorExplosionsCheckBox.component.isSelected }
    private fun comboCounterCheckBoxIsSelected(): Boolean { return comboCounterCheckBox.component.isSelected }
    private fun inputSoundCheckBoxIsSelected(): Boolean { return inputSoundCheckBox.component.isSelected }
    private fun breakSoundCheckBoxIsSelected(): Boolean { return breakSoundCheckBox.component.isSelected }
    private fun openProjectSoundCheckBoxIsSelected(): Boolean { return openProjectCheckBox.component.isSelected }
    private fun closeProjectSoundCheckBoxIsSelected(): Boolean { return closeProjectCheckBox.component.isSelected }

    private fun getExplosionValue(): String { return explosion.component.text }

    private fun explosionIsValid(): Boolean {
        val text = explosion.component.text
        return (text.toInt() in 1..10 && text != "")
    }

}
