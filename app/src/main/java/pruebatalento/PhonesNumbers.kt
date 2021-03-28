package pruebatalento

import android.content.Context
import com.domain.operations.AssistanceNumber
import com.domain.operations.contact.GetAssistanceNumber
import com.pruebatalento.R
import commons.android.Either

class PhonesNumbers(private val context: Context, private val getAssistanceNumber: GetAssistanceNumber) {

    fun getAssistanceNumber(callback: (String) -> Unit) {
        getAssistanceNumber(Unit) { either: Either<Unit, AssistanceNumber> ->
            val number: AssistanceNumber = either.toRightValueOrNull() ?: getDefaultAssistanceNumber()
            callback(number)
        }
    }

    fun getDefaultAssistanceNumber(): AssistanceNumber = context.getString(R.string.default_assistance_number)
}