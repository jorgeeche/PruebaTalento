package pruebatalento.operations.login

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import commons.android.AndroidSecurityUtils
import commons.android.SystemInformation
import pruebatalento.PhonesNumbers
import pruebatalento.base.BaseViewModel
import com.domain.operations.login.*
import com.pruebatalento.R
import pruebatalento.operations.dashboard.DashboardFragment

class LoginViewModel(
    application: Application,
    phonesNumbers: PhonesNumbers,
    logout: Logout,
    private val environment: String,
    private val systemInformation: SystemInformation,
    private val callLogin: CallLogin,
    private val androidSecurityUtils: AndroidSecurityUtils
    ) : BaseViewModel(application, phonesNumbers, logout) {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val onLoginSuccess = MutableLiveData<Boolean>()
    val onLoginFailure = MutableLiveData<CallLoginFailure>()

    var destination = MutableLiveData<Pair<Int, Bundle>>()

    private fun isDeviceUnsecured(): Boolean = if (environment == "pro") {
        androidSecurityUtils.isRooted() ||
                androidSecurityUtils.checkXposed() ||
                !androidSecurityUtils.isDownloadedFromStore() ||
                androidSecurityUtils.isEmulator()
    } else {
        androidSecurityUtils.isRooted() ||
                androidSecurityUtils.checkXposed() ||
                androidSecurityUtils.isEmulator()
    }

    fun callLogin() {
        loading.value = true

        callLogin(LoginCredentials(username.value ?: "",password.value ?: "")) {
            it.fold(::handleLoginError, ::handleLoginSuccess)
        }
    }

    private fun handleLoginSuccess(loginDomainResponse: LoginDomainResponse) {
        loading.value = false
        destination.value = R.id.action_loginFragment_to_dashboardFragment to DashboardFragment.createBundle()

    }
    private fun handleLoginError(callLoginFailure: CallLoginFailure) {
        onLoginFailure.value = callLoginFailure
        loading.value = false
    }

}