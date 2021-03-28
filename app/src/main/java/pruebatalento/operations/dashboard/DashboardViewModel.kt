package pruebatalento.operations.dashboard

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.data.operations.marvel.*
import com.domain.operations.login.Logout
import com.domain.operations.marvel.*
import com.pruebatalento.R
import pruebatalento.PhonesNumbers
import pruebatalento.base.BaseViewModel
import pruebatalento.operations.characterdetail.*

class DashboardViewModel(
        application: Application,
        phonesNumbers: PhonesNumbers,
        private val getCharacters: GetCharacters,
        private val getCharacterById: GetCharacterById,
        logout: Logout) : BaseViewModel(application, phonesNumbers, logout)   {

        var error = MutableLiveData<Boolean>()
        var success = MutableLiveData<GetCharactersDomainResponse>()
        var destination = MutableLiveData<Pair<Int, Bundle>>()
        lateinit var getCharactersDomainResponse: GetCharactersDomainResponse

        fun getCharacters(){
                loading.value = true
                getCharacters(Unit) {
                        it.fold(::handleGetCharactersError, ::handleGetCharactersSuccess)
                }
        }

        private fun handleGetCharactersSuccess(getCharactersDomainResponse: GetCharactersDomainResponse){
                this.getCharactersDomainResponse = getCharactersDomainResponse
                loading.value = false
                success.value =getCharactersDomainResponse
        }

        private fun handleGetCharactersError(splashDataDomainFailure: GetCharactersDomainFailure){
                loading.value = false
                error.value = true
        }

        fun getCharacterById(charId: Int){
                loading.value = true
                getCharacterById(charId) {
                        it.fold(::handleGetCharacterByIdError, ::handleGetCharacterByIdSuccess)
                }
        }

        private fun handleGetCharacterByIdSuccess(characterDomain: CharacterDomain){
                loading.value = false
                goCharacterDetail(characterDomain)
        }

        private fun handleGetCharacterByIdError(splashDataDomainFailure: GetCharactersDomainFailure){
                loading.value = false
                error.value = true
        }

        fun goCharacterDetail(characterDomain: CharacterDomain) {
                destination.value = R.id.dashboard_to_detail to CharacterDetailFragment.createBundle(characterDomain.toApp())
        }

        fun isInitialized(): Boolean{
                return this::getCharactersDomainResponse.isInitialized
        }

}