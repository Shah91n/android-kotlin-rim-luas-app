package shahin.luasforecast.tram

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import shahin.luasforecast.network.Tram
import shahin.luasforecast.utility.BRIDES_GLEN
import shahin.luasforecast.utility.BROOMBRIDGE
import shahin.luasforecast.utility.PARNELL
import shahin.luasforecast.utility.SANDYFORD
import timber.log.Timber

/**
 * TramViewModel to handle live data
 */
class TramViewModel(tram: Tram, app: Application) : AndroidViewModel(app) {

    //Variables Declaration
    //For data
    private val _selectedTram = MutableLiveData<Tram>()
    val selectedTram: LiveData<Tram>
        get() = _selectedTram

    private val _stopPhoto = MutableLiveData<String>()
    val stopPhoto: LiveData<String>
        get() = _stopPhoto

    init {
        Timber.i("StopDetailViewModel is created")
        _selectedTram.value = tram
        getStopImageUrl()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("StopDetailViewModel is destroyed")
    }

    /**
     * Formatting the data coming from the argument passed
     */
    val formatDue: LiveData<String> = Transformations.map(selectedTram) {
        String.format(
            when (_selectedTram.value?.due) {
                "DUE" -> "It's coming now"
                else -> "In ${_selectedTram.value?.due} minutes"
            }
        )
    }

    val formatStopDestination: LiveData<String> = Transformations.map(selectedTram) {
        String.format("Destination is:  ${_selectedTram.value?.destination}")
    }

    fun getStopImageUrl() {
        val stop = _selectedTram.value?.destination
        when (stop) {
            "Broombridge" -> _stopPhoto.value = BROOMBRIDGE
            "Sandyford" -> _stopPhoto.value = SANDYFORD
            "Bride's Glen" -> _stopPhoto.value = BRIDES_GLEN
            "Parnell" -> _stopPhoto.value = PARNELL
        }
    }
}