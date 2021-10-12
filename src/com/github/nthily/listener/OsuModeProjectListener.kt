package com.github.nthily.listener

import com.github.nthily.state.AppSettingsState
import com.github.nthily.utils.Utils.playAudio
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

class OsuModeProjectListener: ProjectManagerListener {

    private val settings = AppSettingsState.instance

    override fun projectOpened(project: Project) { if(settings.enableMode && settings.enableOpenProjectSound) playAudio("welcome.wav") }

    override fun projectClosing(project: Project) { if(settings.enableMode && settings.enableCloseProjectSound) playAudio("seeya.wav") }
}
