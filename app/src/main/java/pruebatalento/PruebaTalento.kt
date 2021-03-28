package pruebatalento

import com.commons.koin.CommonsKoinConfiguration
import com.data.koin.DataKoinConfiguration
import com.domain.koin.DomainKoinConfiguration
import commons.android.CommonsAndroidKoinConfiguration
import pruebatalento.koin.AppKoinConfiguration
import androidx.multidex.MultiDexApplication
import org.koin.android.ext.android.startKoin
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import com.pruebatalento.BuildConfig

class PruebaTalento : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin(
            this, listOf(
                CommonsKoinConfiguration().getModule(),
                CommonsAndroidKoinConfiguration(BuildConfig.FLAVOR).getModule(),
                DataKoinConfiguration(BuildConfig.BASE_URL).getModule(),
                DomainKoinConfiguration().getModule(),
                AppKoinConfiguration().getModule()
            )
        )

        Stetho.initializeWithDefaults(this)
        AndroidThreeTen.init(this)
    }
}