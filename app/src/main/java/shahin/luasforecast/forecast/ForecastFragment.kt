package shahin.luasforecast.forecast

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import shahin.luasforecast.R
import shahin.luasforecast.databinding.FragmentForecastBinding
import shahin.luasforecast.utility.TramsAdapter

/**
 * ForecastFragment to display the real time Luas stop data
 */
class ForecastFragment : Fragment() {

    //Variables Declaration
    private val viewModel: ForecastViewModel by lazy {
        ViewModelProvider(this).get(ForecastViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentForecastBinding.inflate(inflater)

        //Initialize Data Binding LifeCycle & with ForecastViewModel
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //TODO (As study) -> I need to know how to initialize the adapter without the click listener implementation
        binding.rvTrams.adapter = TramsAdapter(TramsAdapter.OnClickListener{
            viewModel.displayTramDetails(it)
        })

        //Navigate to the TramFragment
        viewModel.navigateToSelectedTram.observe(viewLifecycleOwner, Observer {
            if(it != null){
                this.findNavController().navigate(ForecastFragmentDirections.actionForecastFragmentToTramFragment(it))
                viewModel.displayTramDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.refresh_menu -> viewModel.refreshData()
        }
        return true
    }
}
