package pruebatalento.operations.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.`common-android`.android.android.navigateTo
import com.`common-android`.android.android.removeKeyboardOnTouch
import com.commons.utils.FieldValidator
import com.domain.operations.login.CallLoginFailure
import com.domain.operations.login.LoginError
import com.pruebatalento.R
import com.pruebatalento.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.*
import pruebatalento.StatusBarController
import pruebatalento.base.BaseFragment
import pruebatalento.views.MessageDialog
import kotlin.reflect.KClass

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    override fun getLayout(): Int = R.layout.fragment_login
    override fun getViewModel(): KClass<LoginViewModel> = LoginViewModel::class


    companion object {
        fun createBundle() = bundleOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNavigationObserver()
        StatusBarController().translucentScreen(activity!!)

        initLoginObservers()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogin.isEnabled = false

        initView()
        initListeners()
    }

    private fun initView(){
        if (view !is EditText) view?.removeKeyboardOnTouch(activity)
    }
    private fun initListeners(){
        binding.etUsername.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?){
                btnLogin.isEnabled = isLoginButtonEnabled()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
             }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
             }
        })

        binding.etPwd.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?){
                btnLogin.isEnabled = isLoginButtonEnabled()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.etPwd.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus){
                binding.btnLogin.requestFocus()
            }
        }
    }

    private fun initNavigationObserver() {
        viewModel.destination.observe(this, Observer { destination ->
            destination?.apply {
                navigateTo(first, second)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        hideMenu()
        //viewModel.init()
    }

    private fun isLoginButtonEnabled(): Boolean {
        return FieldValidator().run {
            validateAliasLogin(etUsername.text.toString()) && validatePasswordLogin(etPwd.text.toString())
        }
    }

    private fun initLoginObservers(){
        viewModel.onLoginFailure.observe(this, Observer { callLoginFailure ->
            when (callLoginFailure){
                is CallLoginFailure.Repository -> {
                    showLoginError(false)
                }
                is CallLoginFailure.Known -> when(callLoginFailure.error){
                    LoginError.UserNotFoundError,
                    LoginError.InvalidCredentialsError -> {
                        showLoginError(true)
                    }
                }
                else -> {
                        showLoginError(false)
                }
            }
        })
    }

    private fun showLoginError(customError: Boolean){
        val title: String
        val body: String
        val button :String

        if (customError){
            title = context!!.getString(R.string.login_credential_custom_error_title)
            body =  context!!.getString(R.string.login_credential_custom_error_body)
            button =  context!!.getString(R.string.login_credential_custom_error_button)
        }else{
            title = context!!.getString(R.string.generic_error_title)
            body =  context!!.getString(R.string.generic_error_body)
            button =  context!!.getString(R.string.generic_error_button)
        }
        MessageDialog(context!!).showDialog(
                title,
                body,
                button
        ) { dialog -> dialog.dismiss() }

    }
}