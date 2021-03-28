package pruebatalento.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.`common-android`.android.android.createLoading
import com.`common-android`.android.android.hideKeyboard
import com.`common-android`.android.android.navigateTo
import com.domain.RepositoryFailure
import com.pruebatalento.BR
import com.pruebatalento.MainActivity
import com.pruebatalento.R
import commons.android.Try
import org.koin.androidx.viewmodel.ext.android.viewModelByClass
import pruebatalento.operations.login.LoginFragment
import pruebatalento.views.MessageDialog
import kotlin.reflect.KClass


abstract class BaseFragment<VM : BaseViewModel, B : ViewDataBinding> : Fragment() {

    protected val viewModel: VM by viewModelByClass(getViewModel())
    protected lateinit var binding: B

    abstract fun getLayout(): Int
    abstract fun getViewModel(): KClass<VM>

    private var loadingView: AlertDialog? = null
    protected var retryAction: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.loading.observe(this, Observer { showLoading ->
            if (showLoading) showLoading()
            else dismissLoading()
        })


        viewModel.onLogout.observe(this, Observer {
            if (it != null) {
                Try { while (popFragment()); }
                //sessionManager.stopSession()
                navigateTo(R.id.loginFragment, LoginFragment.createBundle())
            }
        })
    }

    private fun clearStackTo(id: Int, bundle: Bundle) {
        Try { while (popFragment()); }
        navigateTo(id, bundle)
    }

    private fun showLoading() {
        context?.let {
            view?.hideKeyboard(it)
        }

        if (loadingView == null) {
            loadingView = createLoading()
            loadingView?.show()
        }
    }

    private fun dismissLoading() {
        loadingView?.dismiss()
        loadingView = null
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.executePendingBindings()
    }


    override fun onResume() {
        super.onResume()
    }


    override fun onStop() {
        super.onStop()
        loadingView?.dismiss()
        context?.let {
            view?.hideKeyboard(it)
        }
    }


    fun handleRepositoryError(repositoryFailure: RepositoryFailure, retryAction: (() -> Unit)? = null) {
        context?.let {
            this.retryAction = retryAction
//TODO: Mostrar errores de repositorio
            /*
            when (repositoryFailure) {
                NoInternet -> BannerDialog.showErrorBanner(it, getString(R.string.error_network_connection_fail))

                Unknown -> viewModel.getAssistanceNumber { assistanceNumber ->
                    context?.let { context ->
                        val genericError: String = formatString(R.string.error_generic, assistanceNumber)
                        if (retryAction == null) {
                            BannerDialog.showErrorBanner(context, genericError)
                        } else {
                            BannerDialog.showErrorRetryBanner(context, genericError, retryAction)
                        }
                    }
                }
                Unauthorized -> {
                    val message = getString(R.string.logout_inactivity_alert)
                    val positiveMessage = getString(R.string.logout_inactivity_alert_accept_button)

                    MessageDialog(context!!).showNotCancelableNativeAlert("", message, positiveMessage, "", {
                        viewModel.logout()
                    }, {})
                }

                AccessibilityMatrixError -> viewModel.getAssistanceNumber { assistanceNumber ->
                    context?.let { context ->
                        val genericError: String = formatString(R.string.accessibility_matrix_error_text, assistanceNumber)
                        if (retryAction == null) {
                            BannerDialog.showErrorBanner(context, genericError)
                        } else {
                            BannerDialog.showErrorRetryBanner(context, genericError, retryAction)
                        }
                    }
                }
            }.exhaustive
       */ }
    }

    fun showMenu() = getMainActivity()?.showMenu()

    fun hideMenu() = getMainActivity()?.hideMenu()

    private fun getMainActivity(): MainActivity? {
        return if (activity is MainActivity) activity as MainActivity else null
    }

    fun showToolbarBackArrow(toolbar: Toolbar) {
        toolbar.setNavigationOnClickListener { popFragment() }
        toolbar.setNavigationIcon(R.mipmap.arrow_back)
    }

    fun popFragment(): Boolean = findNavController().popBackStack()

    fun exitApp(): Unit? = activity?.finish()

    fun logout(callback: (() -> Unit)? = null) {
        val message = getString(R.string.logout_text)
        val positiveMessage = getString(R.string.logout_accept_button)
        val negativeMessage = getString(R.string.logout_cancel_button)

        MessageDialog(context!!).showCancelableNativeAlert("", message, positiveMessage, negativeMessage, {
            callback?.invoke()
            //viewModel.logout()
        })
    }
}