package shahin.luasforecast.tram

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import shahin.luasforecast.R
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

    //Variables Declaration for data
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

    /**
     * Formatting the data coming from the argument passed
     */
    val formatDue: LiveData<String> = Transformations.map(selectedTram) {
        String.format(
            when (_selectedTram.value?.due) {
                "DUE" -> app.resources.getString(R.string.coming_now)
                else -> "${_selectedTram.value?.due} ${app.resources.getString(R.string.minutes)}"
            }
        )
    }

    val formatStopDestination: LiveData<String> = Transformations.map(selectedTram) {
        String.format("${app.resources.getString(R.string.destination_is)} ${_selectedTram.value?.destination}")
    }

    private fun getStopImageUrl() {
        when (_selectedTram.value?.destination) {
            "Broombridge" -> _stopPhoto.value = BROOMBRIDGE
            "Sandyford" -> _stopPhoto.value = SANDYFORD
            "Bride's Glen" -> _stopPhoto.value = BRIDES_GLEN
            "Parnell" -> _stopPhoto.value = PARNELL
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("StopDetailViewModel is destroyed")
    }

}