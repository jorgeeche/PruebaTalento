package pruebatalento.operations.splash

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.domain.operations.marvel.GetCharactersDomainFailure
import com.domain.operations.marvel.GetCharactersDomainResponse
import com.domain.operations.login.Logout
import com.domain.operations.marvel.GetCharacters
import com.pruebatalento.R
import commons.android.SystemInformation
import pruebatalento.PhonesNumbers
import pruebatalento.base.BaseViewModel
import pruebatalento.operations.dashboard.DashboardFragment

class SplashViewModel(
        application: Application,
        phonesNumbers: PhonesNumbers,
        logout: Logout,
        private val systemInformation: SystemInformation,
        private val getCharacters: GetCharacters
    ) : BaseViewModel(application, phonesNumbers, logout) {

    var destination = MutableLiveData<Pair<Int, Bundle>>()
    var error = MutableLiveData<Boolean>()

    fun showLoading(){
        loading.value = true
    }

    fun dismissLoading(){
        loading.value = false
    }

    fun init(){
        getCharacters(Unit) {
            it.fold(::handleSplashDataError, ::handleSplashDataSuccess)
        }
    }

    private fun handleSplashDataSuccess(getCharactersDomainResponse: GetCharactersDomainResponse){
        continueInitViews()
    }

    private fun handleSplashDataError(splashDataDomainFailure: GetCharactersDomainFailure){
        error.value = true
    }

    private fun continueInitViews(){
        goDashboardNavigation()
    }

    fun goDashboardNavigation() {
        destination.value = R.id.inicio_to_dashboard to DashboardFragment.createBundle()
    }
}