package co.id.cpn.data.di

import co.id.cpn.data.remote.ApiServices
import co.id.cpn.data.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val certificatePinner = CertificatePinner.Builder()
            .add("api.themoviedb.org", "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .build()

        val okhhtpclient = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .connectTimeout(20L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(20L, TimeUnit.SECONDS)
//            .certificatePinner(certificatePinner)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhhtpclient)
            .build()

        retrofit.create(ApiServices::class.java)   
    }
}