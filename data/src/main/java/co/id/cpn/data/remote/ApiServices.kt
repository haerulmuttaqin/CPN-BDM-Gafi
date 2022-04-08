package co.id.cpn.data.remote

import co.id.cpn.entity.DataBody
import co.id.cpn.entity.login.GetSqliteResponse
import co.id.cpn.entity.login.LoginRequest
import co.id.cpn.entity.login.LoginResponse
import co.id.cpn.entity.util.Constants
import com.google.gson.JsonArray
import com.google.gson.JsonObject
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

    @POST("token")
    fun getTokenAuth(
        @Header(Constants.KEY_RCTL_SID) appId: String,
        @Header(Constants.KEY_AUTHORIZATION) auth: String,
    ): Call<JsonObject>

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

    @GET("dashboard/productivity")
    suspend fun getDashProductivity(
        @Header("RctlSID") str: String?
    ): Response<DataBody<JsonArray>>

    @GET("dashboard/reasonsolost")
    suspend fun getDashReasonOfOrderLost(
        @Header("RctlSID") str: String?
    ): Response<DataBody<JsonArray>>

    @GET("dashboard/registeroutlet")
    suspend fun getDashRegisterOutlet(
        @Header("RctlSID") str: String?
    ): Response<DataBody<JsonObject>>

    @GET("dashboard/sovsdo")
    suspend fun getDashSoVsDo(
        @Header("RctlSID") str: String?,
    ): Response<DataBody<JsonArray>>

    @GET("dashboard/sovsdo/cust/{customerSID}")
    suspend fun getDashSoVsDoByCust(
        @Header("RctlSID") str: String?,
        @Path("customerSID") str2: String?
    ): Response<DataBody<JsonArray>>

    @GET("dashboard/tagging")
    suspend fun getDashTagging(
        @Header("RctlSID") str: String?
    ): Response<DataBody<JsonObject>>

    @GET("dashboard/totalfreezer")
    suspend fun getDashTotalFreezer(
        @Header("RctlSID") str: String?
    ): Response<DataBody<JsonArray>>

    @GET("dashboard/visitperfomance")
    suspend fun getDashVisitPerformance(
        @Header("RctlSID") str: String?
    ): Response<DataBody<JsonObject>>

}