package pruebatalento.operations.characterdetail

import android.opengl.Visibility
import android.os.Bundle
import android.view.View.GONE
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.`common-android`.android.android.navigateTo
import com.pruebatalento.R
import com.pruebatalento.databinding.FragmentCharacterDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.toolbar_lay.*
import pruebatalento.StatusBarController
import pruebatalento.base.BaseFragment
import pruebatalento.views.MessageDialog
import kotlin.reflect.KClass

class CharacterDetailFragment : BaseFragment<CharacterDetailViewModel, FragmentCharacterDetailBinding>() {

    override fun getLayout(): Int = R.layout.fragment_character_detail
    override fun getViewModel(): KClass<CharacterDetailViewModel> = CharacterDetailViewModel::class


    companion object {
        private const val CHARACTER = "char"
        fun createBundle(char: CharacterApp) = bundleOf(
            CHARACTER to char
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getParcelable<CharacterApp>(CHARACTER)?.apply {
            viewModel.char = this
        }
        initNavigationObserver()
        StatusBarController().translucentScreen(activity!!)

        initObservers()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initView(){
        Picasso.get().load(viewModel.char?.thumbnail?.path+"."+viewModel.char?.thumbnail?.extension).into(binding.marvelImgDetail)
        binding.tvSeriesDetail.text = viewModel.char?.series?.returned.toString()
        binding.tvComicsDetail.text = viewModel.char?.comics?.returned.toString()
        binding.tvStoriesDetail.text = viewModel.char?.stories?.returned.toString()
        binding.tvEventsDetail.text = viewModel.char?.events?.returned.toString()

        if (!viewModel.char?.description!!.isEmpty()) binding.tvDescDetail.text = viewModel.char?.description

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
        toolbar_title_textView.setText(viewModel.char?.name)
        (toolbar as Toolbar).setNavigationOnClickListener { popFragment() }
        initView()

    }

    private fun initObservers(){

    }

    private fun showError(customError: Boolean){
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