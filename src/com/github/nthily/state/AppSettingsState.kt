package com.github.nthily.state

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/*
    存储设置中的值到本地
    Store the values in the settings locally
 */

@State(
    name = "osu-mode",
    storages = [Storage("osu-mode.xml")]
)
class AppSettingsState: PersistentStateComponent<AppSettingsState> {


    var enableMode = true
    var enableComboCounter = true

    var enableKeyboardSound = true
    var enableOpenProjectSound = true
    var enableCloseProjectSound = true

    companion object {
        val instance: AppSettingsState
            get() = ApplicationManager.getApplication().getService(AppSettingsState::class.java)
    }

    override fun getState(): AppSettingsState {
        return this
    }

    override fun loadState(appSettingState: AppSettingsState) {
        XmlSerializerUtil.copyBean(appSettingState, this)
    }
}
