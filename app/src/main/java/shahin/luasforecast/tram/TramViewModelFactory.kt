package shahin.luasforecast.tram

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import shahin.luasforecast.network.Tram
import java.lang.IllegalArgumentException

/**
 * Simple ViewModel factory that provides the Tram and context to the ViewModel.
 */

class TramViewModelFactory(private val tram: Tram, private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TramViewModel::class.java)){
            return TramViewModel(tram,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}