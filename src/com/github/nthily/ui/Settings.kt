package com.github.nthily.ui

import com.github.nthily.state.AppSettingsState
import com.github.nthily.utils.Utils.hideComboList
import com.github.nthily.utils.Utils.showComboList
import com.intellij.openapi.options.BoundConfigurable
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.layout.CellBuilder
import com.intellij.ui.layout.panel

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

    private lateinit var keyboardSoundCheckBox: CellBuilder<JBCheckBox>
    private lateinit var openProjectCheckBox: CellBuilder<JBCheckBox>
    private lateinit var closeProjectCheckBox: CellBuilder<JBCheckBox>

    private var panel = panel { // Kotlin UI DSL

        titledRow("General") {
            row { modeCheckBox = checkBox("Enable Osu! Mode") }
            row { comboCounterCheckBox = checkBox("Enable Combo Counter") }
        }

        titledRow("Audio") {
            row { keyboardSoundCheckBox = checkBox("Enable Keyboard Sound") }
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

    override fun createPanel(): DialogPanel { return panel }

    override fun isModified(): Boolean {

        /*
            重写 isModified 方法，当控件状态改变时，ide 会调用此方法, 如果返回 true, apply 按钮将可以被点击
            Override the isModified method so that ide will call this method when the com.github.nthily.state of the control changes
            and if it returns true, apply button will be clickable.
         */

        if(!modeCheckBoxIsSelected()) {
            comboCounterCheckBox.enabled(false)
            keyboardSoundCheckBox.enabled(false)
            openProjectCheckBox.enabled(false)
            closeProjectCheckBox.enabled(false)
        } else {
            comboCounterCheckBox.enabled(true)
            keyboardSoundCheckBox.enabled(true)
            openProjectCheckBox.enabled(true)
            closeProjectCheckBox.enabled(true)
        }

        return (
                settings.enableMode != modeCheckBoxIsSelected() ||
                settings.enableComboCounter != comboCounterCheckBoxIsSelected() ||
                settings.enableKeyboardSound != keyboardSoundCheckBoxIsSelected() ||
                settings.enableOpenProjectSound != openProjectSoundCheckBoxIsSelected() ||
                settings.enableCloseProjectSound != closeProjectSoundCheckBoxIsSelected()
        )
    }

    override fun apply() {

        settings.enableMode = modeCheckBoxIsSelected()
        settings.enableComboCounter = comboCounterCheckBoxIsSelected()

        settings.enableKeyboardSound = keyboardSoundCheckBoxIsSelected()
        settings.enableOpenProjectSound = openProjectSoundCheckBoxIsSelected()
        settings.enableCloseProjectSound = closeProjectSoundCheckBoxIsSelected()

        if(settings.enableMode && settings.enableComboCounter) showComboList()
        else hideComboList()

    }

    override fun reset() {

        /*
            在初始化界面的时候，会执行一次此函数，如果要将上面写的控件的值与本地存储的值对应，请在此函数中初始化控件的值
            This function is executed once when the interface is initialized.
            If you want to correspond the value of the control written above to the locally stored value, initialize the value of the control in this function
         */

        modeCheckBox.component.isSelected = settings.enableMode
        comboCounterCheckBox.component.isSelected = settings.enableComboCounter

        keyboardSoundCheckBox.component.isSelected = settings.enableKeyboardSound
        openProjectCheckBox.component.isSelected = settings.enableOpenProjectSound
        closeProjectCheckBox.component.isSelected = settings.enableCloseProjectSound
    }

    private fun modeCheckBoxIsSelected(): Boolean { return modeCheckBox.component.isSelected }
    private fun comboCounterCheckBoxIsSelected(): Boolean { return comboCounterCheckBox.component.isSelected }
    private fun keyboardSoundCheckBoxIsSelected(): Boolean { return keyboardSoundCheckBox.component.isSelected }
    private fun openProjectSoundCheckBoxIsSelected(): Boolean { return openProjectCheckBox.component.isSelected }
    private fun closeProjectSoundCheckBoxIsSelected(): Boolean { return closeProjectCheckBox.component.isSelected }

}
