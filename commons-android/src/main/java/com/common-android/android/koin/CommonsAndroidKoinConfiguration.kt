package commons.android

import com.commons.android.NetworkManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

class CommonsAndroidKoinConfiguration(private val environment: String) {

    fun getModule() = module {
        single { AndroidSecurityUtils(androidContext()) }
        single { SystemInformation(androidContext()) }
        single { NetworkManager(androidContext()) }
/*
        single { AndroidSecurityUtils(androidContext()) }
        single { BitmapUtils() }
        single { FileManager(androidContext()) }
        single { SystemInformation(androidContext()) }
        single { SessionManager() }
        single { SmsBroadcastReceiver() }
        single { CipherManager(isEncryptionEnabled = getEncryptionEnviroment()) }
        */
    }
}