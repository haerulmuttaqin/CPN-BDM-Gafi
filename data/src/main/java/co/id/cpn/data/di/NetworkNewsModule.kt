package co.id.cpn.data.di

import co.id.cpn.data.BuildConfig
import co.id.cpn.data.remote.NewsServices
import com.github.slashrootv200.retrofithtmlconverter.HtmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val networkNewsModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        val okhhtpclient = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .connectTimeout(20L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(20L, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_NEWS)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(HtmlConverterFactory.create(BuildConfig.BASE_URL_NEWS))
            .client(okhhtpclient)
            .build()

        retrofit.create(NewsServices::class.java)   
    }
}