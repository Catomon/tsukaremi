import androidx.compose.ui.window.application
import com.github.catomon.tsukaremi.di.appModule
import com.github.catomon.tsukaremi.di.desktopModule
import com.github.catomon.tsukaremi.ui.TsukaremiApp
import com.github.catomon.tsukaremi.util.setComposeExceptionHandler
import com.github.catomon.tsukaremi.util.setDefaultExceptionHandler
import com.github.catomon.tsukaremi.util.setSkikoRenderApi
import org.koin.core.context.GlobalContext.startKoin

fun main() {
    setDefaultExceptionHandler()

    application {
        setComposeExceptionHandler()

        startKoin {
            modules(appModule, desktopModule)
        }

        setSkikoRenderApi()

        TsukaremiApp()
    }
}

