package shahin.luasforecast.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.simpleframework.xml.*

/**
 * Classes that mapped for XML response and represent the data coming from the calls
 */

@Root(name = "stopInfo", strict = false)
class StopInfo{
    @field:Attribute(name = "created", required = false)
    var created: String = ""

    @field:Attribute(name = "stop", required = false)
    var stop: String = ""

    @field:Attribute(name = "stopAbv", required = false)
    var stopAbv: String = ""

    @field:Element(name = "message", required = false)
    var message: String = ""

    @field:ElementList(entry = "direction", inline = true, required = false)
    lateinit var directions: List<Direction>

}

@Root(name = "stopInfo", strict = false)
class Direction{
    @field:Attribute(name = "name", required = false)
    var name: String = ""

    @field:ElementList(entry = "tram", inline = true, required = false)
    lateinit var trams: List<Tram>
}

/**
 * Parcelize'd' in order to pass the argument in TramFragment
 */
@Parcelize
@Root(name = "direction", strict = false)
class Tram : Parcelable {
    @field:Attribute(name = "dueMins", required = false)
    var due: String = ""
    @field:Attribute(name = "destination", required = false)
    var destination: String = ""
}