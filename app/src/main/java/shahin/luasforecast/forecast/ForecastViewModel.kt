package shahin.luasforecast.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import shahin.luasforecast.network.LuasApi
import shahin.luasforecast.network.Tram
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

//TODO (Must be done) Animation between fragments

/**
 * For displaying loading or error image in while fetching the data based on three status
 * LOADING -> While it's fetching, it will display loading icon
 * ERROR -> If no internet connection, it will display image no connection
 * DONE -> No image related to that but to update the status
 */
enum class LuasApiStatus { LOADING, ERROR, DONE }

/**
 * ForecastViewModel to handle live data
 */
class ForecastViewModel() : ViewModel() {

    //Variable Declaration and initialization
    //For background jobs
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //For data
    private val _upToDate = MutableLiveData<String>()
    val upToDate: LiveData<String>
        get() = _upToDate

    private val _stop = MutableLiveData<String>()
    val stop: LiveData<String>
        get() = _stop

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _direction = MutableLiveData<String>()
    val direction: LiveData<String>
        get() = _direction

    private val _trams = MutableLiveData<List<Tram>>()
    val trams: LiveData<List<Tram>>
        get() = _trams

    private val _status = MutableLiveData<LuasApiStatus>()
    val status: LiveData<LuasApiStatus>
        get() = _status

    //For Navigation when a tram is clicked
    private val _navigateToSelectedTram = MutableLiveData<Tram>()
    val navigateToSelectedTram: LiveData<Tram>
        get() = _navigateToSelectedTram

    /**
     * Display data once the ViewModel is created based on time the app launched upon
     */
    init {
        Timber.i("ForecastViewModel is created")
        isAfternoon()
    }

    /**
     * To cancel the coroutine execution on the ViewModel destroy state
     */
    override fun onCleared() {
        super.onCleared()
        Timber.i("ForecastViewModel is destroyed")
        viewModelJob.cancel()
    }

    /**
     * Get real time data information for Stillorgan Luas stop
     */
    private fun getStillorganStopInfo() {
        coroutineScope.launch {
            try {
                _status.value = LuasApiStatus.LOADING
                val stopInfo = LuasApi.retrofitService.getStillorganStopInfo()
                    .await() //TODO (As note remainder of the difference) If was not Deferred value, I would not need .await() as per suspend function in LuasApiService
                _status.value = LuasApiStatus.DONE
                val upToDate = stopInfo.created
                if (upToDate.isNotEmpty()) {
                    _upToDate.value = formatDate(upToDate)
                }

                val stop = stopInfo.stop
                if (stop.isNotEmpty()) {
                    _stop.value = stop
                }

                val message = stopInfo.message
                if (message.isNotEmpty()) {
                    _message.value = message
                }

                val direction = stopInfo.directions[0].name
                if (direction.isNotEmpty()) {
                    _direction.value = direction
                }

                val inboundTramList = stopInfo.directions[0].trams
                if (inboundTramList.isNotEmpty()) {
                    _trams.value = inboundTramList
                }
            } catch (e: Exception) {
                _status.value = LuasApiStatus.ERROR
                Timber.i("getStillorganStopInfo error at ----> ".plus(e))
            }
        }
    }

    /**
     * Get real time data information for Marlborough Luas stop
     */
    private fun getMarlboroughStopInfo() {
        coroutineScope.launch {
            try {
                _status.value = LuasApiStatus.LOADING
                val stopInfo = LuasApi.retrofitService.getMarlboroughStopInfo().await()
                _status.value = LuasApiStatus.DONE
                val upToDate = stopInfo.created
                if (upToDate.isNotEmpty()) {
                    _upToDate.value = formatDate(upToDate)
                }

                val stop = stopInfo.stop
                if (stop.isNotEmpty()) {
                    _stop.value = stop
                }

                val message = stopInfo.message
                if (message.isNotEmpty()) {
                    _message.value = message
                }

                val direction = stopInfo.directions[1].name
                if (direction.isNotEmpty()) {
                    _direction.value = direction
                }

                val inboundTramList = stopInfo.directions[1].trams
                if (inboundTramList.isNotEmpty()) {
                    _trams.value = inboundTramList
                }
            } catch (e: Exception) {
                _status.value = LuasApiStatus.ERROR
                Timber.i("getMarlboroughStopInfo error at ----> ".plus(e))
            }
        }
    }

    /**
     *  Check the current time then run the fun to extract data
     *  based on the scenario specified for RIM Employee Luas App
     */
    fun isAfternoon() {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        if (hour in 12..23) {
            Timber.i("Hours between 12:01 – 23:59")
            getStillorganStopInfo()
        } else {
            Timber.i("Hours between 00:00 – 12:00")
            getMarlboroughStopInfo()
        }
    }

    /**
     * To refresh and get latest real time data
     */
    fun refreshData() {
        isAfternoon()
    }

    /**
     * Format date to display a pattern I prefer
     */
    private fun formatDate(date: String): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd.MM.yyy HH:mm")
        return formatter.format(parser.parse(date))
    }

    //For Navigation upon argument passed
    fun displayTramDetails(tram: Tram){
        _navigateToSelectedTram.value = tram
    }
    //For Navigation once completed
    fun displayTramDetailsComplete(){
        _navigateToSelectedTram.value = null
    }

}