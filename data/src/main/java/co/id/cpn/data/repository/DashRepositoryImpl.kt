package co.id.cpn.data.repository

import co.id.cpn.data.remote.ApiServices
import co.id.cpn.domain.dashboard.DashRepository
import co.id.cpn.entity.DataBody
import co.id.cpn.entity.Resource
import co.id.cpn.entity.util.Constants
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DashRepositoryImpl constructor(
    private val apiServices: ApiServices
) : DashRepository {

    override fun dashboardProductivity(rctlSID: String): Flow<Resource<DataBody<JsonArray>>> = flow {
        val response = apiServices.getDashProductivity(rctlSID)
        try {
            if (response.body()?.status == Constants.RESPONSE_OK) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Failed(response.code(), response.body()!!.message))
            }
        }
        catch (t: Throwable) {
            if (response.code() == Constants.CODE_UNAUTHORIZED) {
                emit(Resource.Failed(response.code(), "You are not authorized"))
            } else {
                emit(Resource.Error(response.code(), t))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun dashboardReasonOrderLost(rctlSID: String): Flow<Resource<DataBody<JsonArray>>> = flow {
        val response = apiServices.getDashReasonOfOrderLost(rctlSID)
        try {
            if (response.body()?.status == Constants.RESPONSE_OK) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Failed(response.code(), response.body()!!.message))
            }
        }
        catch (t: Throwable) {
            if (response.code() == Constants.CODE_UNAUTHORIZED) {
                emit(Resource.Failed(response.code(), "You are not authorized"))
            } else {
                emit(Resource.Error(response.code(), t))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun dashboardRegisterOutlet(rctlSID: String): Flow<Resource<DataBody<JsonObject>>> = flow {
        val response = apiServices.getDashRegisterOutlet(rctlSID)
        try {
            if (response.body()?.status == Constants.RESPONSE_OK) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Failed(response.code(), response.body()!!.message))
            }
        }
        catch (t: Throwable) {
            if (response.code() == Constants.CODE_UNAUTHORIZED) {
                emit(Resource.Failed(response.code(), "You are not authorized"))
            } else {
                emit(Resource.Error(response.code(), t))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun dashboardSoVsDo(rctlSID: String): Flow<Resource<DataBody<JsonArray>>> = flow {
        val response = apiServices.getDashSoVsDo(rctlSID)
        try {
            if (response.body()?.status == Constants.RESPONSE_OK) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Failed(response.code(), response.body()!!.message))
            }
        }
        catch (t: Throwable) {
            if (response.code() == Constants.CODE_UNAUTHORIZED) {
                emit(Resource.Failed(response.code(), "You are not authorized"))
            } else {
                emit(Resource.Error(response.code(), t))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun dashboardSoVsDoCust(
        rctlSID: String,
        customerSID: String
    ): Flow<Resource<DataBody<JsonArray>>> = flow {
        val response = apiServices.getDashSoVsDoByCust(rctlSID, customerSID)
        try {
            if (response.body()?.status == Constants.RESPONSE_OK) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Failed(response.code(), response.body()!!.message))
            }
        }
        catch (t: Throwable) {
            if (response.code() == Constants.CODE_UNAUTHORIZED) {
                emit(Resource.Failed(response.code(), "You are not authorized"))
            } else {
                emit(Resource.Error(response.code(), t))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun dashboardTagging(rctlSID: String): Flow<Resource<DataBody<JsonObject>>> = flow {
        val response = apiServices.getDashTagging(rctlSID)
        try {
            if (response.body()?.status == Constants.RESPONSE_OK) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Failed(response.code(), response.body()!!.message))
            }
        }
        catch (t: Throwable) {
            if (response.code() == Constants.CODE_UNAUTHORIZED) {
                emit(Resource.Failed(response.code(), "You are not authorized"))
            } else {
                emit(Resource.Error(response.code(), t))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun dashboardTotalFreezer(rctlSID: String): Flow<Resource<DataBody<JsonArray>>> = flow {
        val response = apiServices.getDashTotalFreezer(rctlSID)
        try {
            if (response.body()?.status == Constants.RESPONSE_OK) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Failed(response.code(), response.body()!!.message))
            }
        }
        catch (t: Throwable) {
            if (response.code() == Constants.CODE_UNAUTHORIZED) {
                emit(Resource.Failed(response.code(), "You are not authorized"))
            } else {
                emit(Resource.Error(response.code(), t))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun dashboardVisitPerformance(rctlSID: String): Flow<Resource<DataBody<JsonObject>>> = flow {
        val response = apiServices.getDashVisitPerformance(rctlSID)
        try {
            if (response.body()?.status == Constants.RESPONSE_OK) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Failed(response.code(), response.body()!!.message))
            }
        }
        catch (t: Throwable) {
            if (response.code() == Constants.CODE_UNAUTHORIZED) {
                emit(Resource.Failed(response.code(), "You are not authorized"))
            } else {
                emit(Resource.Error(response.code(), t))
            }
        }
    }.flowOn(Dispatchers.IO)
}