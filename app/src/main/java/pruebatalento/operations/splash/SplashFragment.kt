package pruebatalento.operations.splash

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.`common-android`.android.android.navigateTo
import com.pruebatalento.R
import com.pruebatalento.databinding.FragmentSplashBinding
import pruebatalento.StatusBarController
import pruebatalento.base.BaseFragment
import pruebatalento.views.MessageDialog
import kotlin.reflect.KClass

class SplashFragment : BaseFragment<SplashViewModel, FragmentSplashBinding>() {

    override fun getLayout(): Int = R.layout.fragment_splash
    override fun getViewModel(): KClass<SplashViewModel> = SplashViewModel::class


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
        StatusBarController().fullScreen(activity!!)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hideMenu()
        viewModel.init()

    }

    private fun initObservers() {
        viewModel.destination.observe(this, Observer { destination ->
            destination?.apply {
                navigateTo(first, second)
            }
        })

        viewModel.error.observe(this, Observer {it ->
            val title: String
            val body: String
            val button :String

            title = getString(R.string.generic_error_title)
            body =  context!!.getString(R.string.generic_error_body)
            button =  context!!.getString(R.string.generic_error_button)

            MessageDialog(context!!).showDialog(
                title,
                body,
                button
            ) { dialog -> dialog.dismiss() }
        })
    }

}
