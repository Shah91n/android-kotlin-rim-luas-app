package shahin.luasforecast.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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
    @GET("xml/get.ashx?action=forecast")
    suspend fun getStopInfo(@Query("stop") stop: String,
                            @Query("encrypt") encrypt: String)
            : StopInfo
}

/**
 * Initialize API
 */
object LuasApi{
    val retrofitService: LuasApiService by lazy {
        retrofit.create(LuasApiService::class.java)
    }
}