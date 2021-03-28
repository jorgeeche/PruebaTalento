package pruebatalento.views

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.pruebatalento.R

class MessageDialog(private val context: Context) {

    private var currentDialog: AlertDialog? = null

    fun showDialog(title: String, message: String, messageButton: String, callback: ((AlertDialog) -> Unit)? = null) {
        val view = LayoutInflater.from(context).inflate(R.layout.message_dialog_lay, null)
        val alertDialog = AlertDialog.Builder(context)

        val titleDialog = view.findViewById<TextView>(R.id.title_dialog_textView)
        val messageDialog = view.findViewById<TextView>(R.id.message_dialog_textView)
        val closeDialog = view.findViewById<TextView>(R.id.close_dialog_textView)

        titleDialog?.text = title
        messageDialog?.text = message
        closeDialog?.text = messageButton

        alertDialog.setView(view)
        currentDialog = alertDialog.create()

        callback?.let {
            currentDialog?.let { current ->
                closeDialog?.setOnClickListener { it(current) }
            }
        }
        currentDialog?.show()
    }

    fun showCancelableNativeAlert(title: String, message: String, messagePositive: String, messageNegative: String, callbackPositive: ((DialogInterface) -> Unit), callbackNegative: ((DialogInterface) -> Unit)? = null) {
        currentDialog = with(AlertDialog.Builder(context)) {
            setTitle(title)
            setMessage(message)
            setPositiveButton(messagePositive) { dialog, _ -> callbackPositive(dialog) }
            setNegativeButton(messageNegative) { dialog, _ -> if (callbackNegative == null) dialog.dismiss() else callbackNegative(dialog) }
            setCancelable(true)
            show()
        }
    }

    fun showNotCancelableNativeAlert(title: String, message: String, messagePositive: String, messageNegative: String, callbackPositive: ((DialogInterface) -> Unit), callbackNegative: ((DialogInterface) -> Unit)) {
        currentDialog = with(AlertDialog.Builder(context)) {
            setTitle(title)
            setMessage(message)
            setPositiveButton(messagePositive) { dialog, _ -> callbackPositive(dialog) }
            setNegativeButton(messageNegative) { dialog, _ -> callbackNegative(dialog) }
            setCancelable(false)
            show()
        }
    }

    fun dismissCurrentDialog() {
        currentDialog?.let {dialog ->
            if(dialog.isShowing) dialog.dismiss()
        }
    }

}
