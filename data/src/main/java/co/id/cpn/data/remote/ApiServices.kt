package co.id.cpn.data.remote

import co.id.cpn.entity.DataBody
import co.id.cpn.entity.login.GetSqliteResponse
import co.id.cpn.entity.login.LoginRequest
import co.id.cpn.entity.login.LoginResponse
import co.id.cpn.entity.util.Constants
import com.google.gson.JsonObject
import org.jsoup.nodes.Document
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiServices {

    @GET("info/{keyH}/{type}")
    suspend fun getInfo(
        @Header(Constants.KEY_RCTL_SID) appId: Int = Constants.RCTL_SID,
        @Path("keyH") keyH: String,
        @Path("type") type: String,
    ): Response<DataBody<JsonObject>>

    @POST("user/login")
    suspend fun postLogin(
        @Header(Constants.KEY_RCTL_SID) appId: Int = Constants.RCTL_SID,
        @Body loginRequest: LoginRequest,
    ): Response<DataBody<LoginResponse>>

    @POST("token")
    suspend fun getToken(
        @Header(Constants.KEY_RCTL_SID) appId: Int = Constants.RCTL_SID,
        @Header(Constants.KEY_AUTHORIZATION) auth: String,
    ): Response<DataBody<JsonObject>>

    @POST("customer")
    suspend fun getCustomerSqlite(
        @Header(Constants.KEY_RCTL_SID) appId: Int = Constants.RCTL_SID,
        @Header(Constants.KEY_TOKEN) token: String,
        @Body body: JsonObject,
    ): Response<DataBody<JsonObject>>

    @POST("customer")
    suspend fun getCustomerSqliteWork(
        @Header(Constants.KEY_RCTL_SID) appId: Int = Constants.RCTL_SID,
        @Header(Constants.KEY_TOKEN) token: String,
        @Body body: JsonObject,
    ): Response<DataBody<GetSqliteResponse>>
}