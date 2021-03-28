package pruebatalento.views

import android.app.AlertDialog

object BannerDialog {

    private var currentBanner: AlertDialog? = null
//TODO: Crear estilos para el banner y vistas
/*
    fun showErrorBlockedUserBanner(context: Context, message: String, linkMessage: String, onBlockClicked: () -> Unit) {
        currentBanner?.dismiss()

        val view: View = LayoutInflater.from(context).inflate(R.layout.banner_error_lay, null)
        view.findViewById<TextView>(R.id.banner_message_textView)?.text = message
        view.findViewById<ImageButton>(R.id.banner_close_imageView)?.setOnClickListener { currentBanner?.dismiss() }
        view.findViewById<TextView>(R.id.link_button)?.apply {
            text = linkMessage
            setOnClickListener { onBlockClicked() }
        }

        showBanner(context, R.style.BannerDialog_Error_Style, view)
    }

    fun showErrorBanner(context: Context, message: String) {
        currentBanner?.dismiss()

        val view: View = initErrorBannerLayout(context, message)
        showBanner(context, R.style.BannerDialog_Error_Style, view)
    }

    fun showErrorRetryBanner(context: Context, message: String, onRetryClick: (() -> Unit)) {
        currentBanner?.dismiss()

        val view: View = initErrorRetryBannerLayout(context, message, onRetryClick)
        showBanner(context, R.style.BannerDialog_Error_Style, view)
    }

    fun showWarningBanner(context: Context, message: String, buttonText: String? = null, onButtonClick: (() -> Unit)? = null) {
        currentBanner?.dismiss()

        val view: View = initWarningBannerLayout(context, message, buttonText, onButtonClick)
        showBanner(context, R.style.BannerDialog_Warning_Style, view)
    }

    private fun initWarningBannerLayout(context: Context, message: String, buttonText: String?, onButtonClick: (() -> Unit)?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.banner_warning_lay, null)
        view.findViewById<TextView>(R.id.banner_message_textView)?.text = message
        view.findViewById<ImageButton>(R.id.banner_close_imageView)?.setOnClickListener { currentBanner?.dismiss() }

        val button = view.findViewById<TextView>(R.id.warning_button)
        if (buttonText != null && onButtonClick != null) {
            button.visibility = View.VISIBLE
            button.text = buttonText
            button.setOnClickListener {
                onButtonClick()
                currentBanner?.dismiss()
            }
        } else {
            button.visibility = View.GONE
        }

        return view
    }

    private fun initErrorBannerLayout(context: Context, message: String): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.banner_error_lay, null)
        view.findViewById<TextView>(R.id.banner_message_textView)?.text = message
        view.findViewById<ImageButton>(R.id.banner_close_imageView)?.setOnClickListener { currentBanner?.dismiss() }
        return view
    }

    private fun initErrorRetryBannerLayout(context: Context, message: String, onRetryClick: (() -> Unit)): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.banner_error_retry_lay, null)
        view.findViewById<TextView>(R.id.banner_message_textView)?.text = message
        view.findViewById<TextView>(R.id.banner_retry_button).setOnClickListener {
            onRetryClick()
            currentBanner?.dismiss()
        }
        view.findViewById<ImageButton>(R.id.banner_close_imageView).setOnClickListener { currentBanner?.dismiss() }
        return view
    }

    private fun showBanner(context: Context, @StyleRes dialogStyleResId: Int, view: View?) {
        val dialog = AlertDialog.Builder(context, dialogStyleResId).create()

        currentBanner = dialog.apply {
            setCanceledOnTouchOutside(true)
            setCancelable(true)
            setView(view, 0, 0, 0, 0)
            window?.attributes?.windowAnimations = R.style.BannerDialog_Error_Animation

            setOnShowListener {
                window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                window?.setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM, WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            }
            show()

            // Do this after calling method show(). Otherway, it won't get the properties correctly.
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            window?.setGravity(Gravity.TOP)
        }
    }


    fun hideCurrentBanner() {
        currentBanner?.dismiss()
    }
*/
}