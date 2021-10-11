import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "Osu! Mode",
    storages = [
        Storage("osuMode.xml")
    ]
)
class AppSettingsState: PersistentStateComponent<AppSettingsState> {

    var enableMode = true
    var enableCursorExplosions = true
    var enableComboCounter = true
    var explosion = "6"

    fun getInstance(): AppSettingsState {
        return ApplicationManager.getApplication().getService(AppSettingsState::class.java)
    }

    override fun getState(): AppSettingsState {
        return this
    }

    override fun loadState(appSettingState: AppSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }
}
