package pruebatalento.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.domain.operations.AssistanceNumber
import com.domain.operations.login.Logout
import commons.android.Either
import pruebatalento.PhonesNumbers

abstract class BaseViewModel(
    application: Application,
    private val phonesNumbers: PhonesNumbers,
    private val logout: Logout
) : AndroidViewModel(application) {

    val app: Application = application

    val loading = MutableLiveData<Boolean>()

    val onLogout = MutableLiveData<Either<Unit, Unit>>()

    val onSessionExpired = MutableLiveData<Either<Unit, Unit>>()

    fun getAssistanceNumber(callback: (AssistanceNumber) -> Unit) {
        phonesNumbers.getAssistanceNumber { callback(it) }
    }

    fun logout() {
        logout(Unit) {
            it.fold(::handleLogoutError, ::handleLogoutSuccess) }
    }

    private fun handleLogoutError(unit: Unit) {
        onLogout.value = Either.Left(unit)
    }

    private fun handleLogoutSuccess(unit: Unit) {
        onLogout.value = Either.Right(unit)
    }
}
