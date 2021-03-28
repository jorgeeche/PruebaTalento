package pruebatalento.operations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(
): ViewModel(){
    val legalTermsChanged = MutableLiveData<Boolean>()

}