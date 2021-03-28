package pruebatalento.koin

import com.pruebatalento.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import pruebatalento.PhonesNumbers
import pruebatalento.operations.characterdetail.CharacterDetailViewModel
import pruebatalento.operations.dashboard.DashboardViewModel
import pruebatalento.operations.login.LoginViewModel
import pruebatalento.operations.splash.SplashViewModel


class AppKoinConfiguration {

    fun getModule() = module {

        single { PhonesNumbers(androidContext(), get()) }

        // View Models
        viewModel { SplashViewModel( androidApplication(),  get(), get(), get(), get()) }
        viewModel { LoginViewModel( androidApplication(),  get(),  get(),  BuildConfig.FLAVOR,  get(),  get(), get()) }
        viewModel { DashboardViewModel( androidApplication(),  get(), get(), get(), get()) }
        viewModel { CharacterDetailViewModel( androidApplication(),  get(), get()) }

    }
}