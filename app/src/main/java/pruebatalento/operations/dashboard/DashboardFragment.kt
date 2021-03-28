package pruebatalento.operations.dashboard

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.`common-android`.android.android.navigateTo
import com.pruebatalento.R
import com.pruebatalento.databinding.FragmentDashboardBinding
import kotlinx.android.synthetic.main.toolbar_lay.*
import pruebatalento.BackPressedListener
import pruebatalento.StatusBarController
import pruebatalento.base.BaseFragment
import pruebatalento.views.MessageDialog
import kotlin.reflect.KClass

class DashboardFragment: BaseFragment<DashboardViewModel, FragmentDashboardBinding>(), BackPressedListener {

    override fun getLayout(): Int = R.layout.fragment_dashboard
    override fun getViewModel(): KClass<DashboardViewModel> = DashboardViewModel::class

    companion object{
        fun createBundle() = bundleOf()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StatusBarController().fullScreen(activity!!)
        initObservers()
        initView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar_title_textView.setText(R.string.toolbar_dashboard)
        toolbar.navigationIcon = null
    }

    private fun initView(){
        //En esta segunda llamada recoge los datos guardados
        if (viewModel.isInitialized()){
            val adapter = MarvelAdapter(context!!, viewModel.getCharactersDomainResponse.data?.results!!.toList()) { goDetail(it)}
            binding.charactersListRecyclerView.layoutManager = LinearLayoutManager(context!!).apply { orientation = RecyclerView.VERTICAL }
            binding.charactersListRecyclerView.adapter = adapter
        }else viewModel.getCharacters()

    }

    private fun initObservers(){
        viewModel.destination.observe(this, Observer { destination ->
            destination?.apply {
                navigateTo(first, second)
            }
        })
        viewModel.success.observe(this, Observer { it ->
            if (it.data!!.results!!.isNotEmpty()){
                val adapter = MarvelAdapter(context!!, it.data?.results!!.toList()) { goDetail(it)}
                binding.charactersListRecyclerView.layoutManager = LinearLayoutManager(context!!).apply { orientation = RecyclerView.VERTICAL }
                binding.charactersListRecyclerView.adapter = adapter
            }else{
                Toast.makeText(context, "Algo ha ido mal", Toast.LENGTH_SHORT).show()
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

    private fun showToast(){
        Toast.makeText(context, "Detalle de personaje", Toast.LENGTH_SHORT).show()
    }
    override fun onBackPressed() {
        logout()
    }

    private fun goDetail(id:Int){
        viewModel.getCharacterById(id)
    }
}
