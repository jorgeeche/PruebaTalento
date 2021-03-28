package pruebatalento.operations.characterdetail

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.domain.operations.login.CallLoginFailure
import com.domain.operations.login.Logout
import com.domain.operations.marvel.GetCharacters
import pruebatalento.PhonesNumbers
import pruebatalento.base.BaseViewModel

class CharacterDetailViewModel(
    application: Application,
    phonesNumbers: PhonesNumbers,
    logout: Logout
    ) : BaseViewModel(application, phonesNumbers, logout) {

    var char: CharacterApp? = null

    var destination = MutableLiveData<Pair<Int, Bundle>>()



}