package co.id.cpn.data.remote

import android.app.Application
import co.id.cpn.data.local.spref.SharedPref
import co.id.cpn.entity.util.Constants
import co.id.cpn.entity.util.Utils
import com.google.gson.JsonObject
import okhttp3.*
import okhttp3.internal.http2.ConnectionShutdownException
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import kotlin.jvm.internal.Intrinsics


class AccessTokenAuthenticator(val application: Application) : Interceptor {

    private fun newRequestWithAccessToken(request: Request, accessToken: String): Request {
        return request.newBuilder().header(Constants.CONTENT_TYPE, Constants.APP_JSON)
            .header(Constants.KEY_TOKEN, accessToken).build()
    }

    private fun defaultRequest(request: Request): Request {
        return request.newBuilder().header(Constants.CONTENT_TYPE, Constants.APP_JSON).build()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val msg: String
        var proceed: Response
        var request: Request = chain.request()
         return try {
            val currentPathUrl: String =
                chain.request().url().pathSegments()[chain.request().url().pathSegments().size - 1]
            val accessToken = SharedPref(application).token
            request = newRequestWithAccessToken(chain.request(), accessToken)
            val response: Response = chain.proceed(request)
            if (!Utils.isNetworkAvailable(application)) {
                return response
            }
            else if (response.code() == 401) {
                synchronized(this) {
                    try {
                        val responseToken = getApi().getTokenAuth(
                            appId = SharedPref(application).user.rctlSid,
                            auth = SharedPref(application).user.authorization,
                        ).execute()
                        val sharedPref = SharedPref(application)
                        val body: JsonObject? = responseToken.body()
                        Intrinsics.checkNotNull(body)
                        val asString = body!!.getAsJsonObject("data").get("TokenAuth").asString
                        sharedPref.token = asString
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    proceed = chain.proceed(
                        newRequestWithAccessToken(
                            request,
                            SharedPref(application).token
                        )
                    )
                }
                return proceed
            }
            else if (currentPathUrl == "login" && currentPathUrl == "token" && currentPathUrl == "info") {
                return response
            } else {
                return response
                /*response.close()
                chain.proceed(defaultRequest(request))*/
            }
        } catch (e3: Exception) {
            e3.printStackTrace()
            msg = if (e3 is SocketTimeoutException) {
                "Timeout - Please check your internet connection"
            } else if (e3 is UnknownHostException) {
                "Unable to make a connection. Please check your internet"
            } else if (e3 is ConnectionShutdownException) {
                "Connection shutdown. Please check your internet"
            } else if (e3 is IOException) {
                "Server is unreachable, please try again later."
            } else (e3 as? IllegalStateException)?.message ?: e3.message.toString()
            val build = Response.Builder().request(request).protocol(Protocol.HTTP_1_1)
                .code(900).message(msg).body(
                    ResponseBody.create(
                        null as MediaType?,
                        StringBuilder().append('{').append(e3).append('}').toString()
                    )
                ).build()
            Intrinsics.checkNotNullExpressionValue(
                build,
                "Builder()\n .request(request)\n                .protocol(Protocol.HTTP_1_1)\n                .code(999)\n                .message(msg)\n                .body(ResponseBody.create(null, \"{\${e}}\")).build()"
            )
            return build
        }
    }

    fun getAPIClient(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .client(provideOkHttp())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApi(): ApiServices {
        return getAPIClient().create(ApiServices::class.java)
    }

    private fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .addInterceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val builder = chain.request().newBuilder()
                builder.addHeader(Constants.CONTENT_TYPE, Constants.APP_JSON)
                builder.method(original.method(), original.body())
                chain.proceed(builder.build())
            }
            .addNetworkInterceptor(provideLoggingInterceptor())
            .build()
    }

    private fun provideLoggingInterceptor(): Interceptor {
        val localHttpLoggingInterceptor = HttpLoggingInterceptor()
        localHttpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return localHttpLoggingInterceptor
    }
}
