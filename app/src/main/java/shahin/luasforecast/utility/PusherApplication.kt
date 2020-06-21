package shahin.luasforecast.utility

import android.app.Application
import timber.log.Timber

/***
 * Timber for logs
 * Initialization
 */
class PusherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}