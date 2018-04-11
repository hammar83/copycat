package me.hammarstrom.copycat

import android.content.Context
import me.hammarstrom.copycat.models.Envelope
import me.hammarstrom.copycatlibrary.CopycatInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

class NewsApi(context: Context) {

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        @Volatile
        private var INSTANCE: NewsApi? = null

        fun getInstance(context: Context): NewsApi =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: NewsApi(context).also { INSTANCE = it }
                }
    }

    private var service: NewsService

    init {
        val retrofit = Retrofit.Builder()
                .client(getClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

        service = retrofit.create(NewsService::class.java)
    }

    fun getTopHeadlines(): Call<Envelope> {
        return service.getTopHeadlines(BuildConfig.ApiKey)
    }

    private fun getClient(context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        val copycat = CopycatInterceptor(context)

        val builder = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(copycat)

        return builder.build()
    }

    interface NewsService {
        @GET("top-headlines?country=us")
        fun getTopHeadlines(@Header("X-Api-Key") apiKey: String): Call<Envelope>
    }

}
