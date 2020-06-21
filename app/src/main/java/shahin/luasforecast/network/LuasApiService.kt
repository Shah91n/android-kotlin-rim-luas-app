package shahin.luasforecast.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://luasforecasts.rpa.ie/"

/**
 * Retrofit with Simple XML Converter in order to fetch the data mapped by XML
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(SimpleXmlConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

/**
 * Fetching data for only 2 stops
 */
interface LuasApiService{
    @GET("xml/get.ashx?action=forecast&stop=sti&encrypt=false")
    fun getStillorganStopInfo(): Deferred<StopInfo>

    @GET("xml/get.ashx?action=forecast&stop=mar&encrypt=false")
    fun getMarlboroughStopInfo(): Deferred<StopInfo>
}

/**
 * Initialize API
 */
object LuasApi{
    val retrofitService: LuasApiService by lazy {
        retrofit.create(LuasApiService::class.java)
    }
}

/**
 *
 *  -- Using suspend as I was not using Deferred value which is non blocking


interface LuasApiService{
@GET("xml/get.ashx?action=forecast&stop=sti&encrypt=false")
suspend fun getStillorganStopInfo(): StopInfo

@GET("xml/get.ashx?action=forecast&stop=mar&encrypt=false")
suspend fun getMarlboroughStopInfo(): StopInfo
}


 *
 */