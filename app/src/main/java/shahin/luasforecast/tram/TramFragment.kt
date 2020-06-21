package shahin.luasforecast.tram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import shahin.luasforecast.databinding.FragmentTramBinding

/**
 * TramFragment to display the details of a single tram and its stop image
 */
class TramFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //Initialization
        val binding = FragmentTramBinding.inflate(inflater)
        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        binding.lifecycleOwner = this
        val selectedTram = TramFragmentArgs.fromBundle(requireArguments()).selectedTram //for the passed parcelable Tram class
        val viewModelFactory = TramViewModelFactory(selectedTram, application)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(TramViewModel::class.java)
        return binding.root
    }

}


