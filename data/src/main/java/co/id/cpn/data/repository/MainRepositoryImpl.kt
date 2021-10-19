package co.id.cpn.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import co.id.cpn.data.local.AppDatabase
import co.id.cpn.data.local.TempDatabase
import co.id.cpn.data.remote.ApiServices
import co.id.cpn.domain.main.MainRepository
import co.id.cpn.entity.*
import co.id.cpn.entity.login.LoginRequest
import co.id.cpn.entity.login.LoginResponse
import co.id.cpn.entity.util.Constants
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainRepositoryImpl constructor(
    private val apiServices: ApiServices,
    private val appDatabase: AppDatabase,
    private val tempDatabase: TempDatabase,
) : MainRepository {
    
    override fun getAssets(): LiveData<List<Asset>> = appDatabase.assetDao().getAll()
    override fun getAssetsBy(customerSid: String): LiveData<List<Asset>> = appDatabase.assetDao().getBy(customerSid)
    override fun getCustomers(): LiveData<List<Customer>> = appDatabase.customerDao().getAll()
    override fun getCustomerBy(customerSID: String): LiveData<CustomerItem> = appDatabase.customerDao().getCustomersItemsBy(customerSID)
    override fun getCustomersItems(): LiveData<List<CustomerItem>> = appDatabase.customerDao().getCustomersItems()
    override fun getCustomerTypes(): LiveData<List<CustomerType>> = appDatabase.customerTypeDao().getAll()

    override fun getProductsOrders(): LiveData<List<ProductOrder>> = appDatabase.productOrderDao().getAll()

    override suspend fun insertAsset(asset: Asset) = appDatabase.assetDao().insert(asset)
    override suspend fun insertCustomer(customer: Customer) = appDatabase.customerDao().insert(customer)
    override suspend fun insertCustomerType(customerType: CustomerType) = appDatabase.customerTypeDao().insert(customerType)
    override suspend fun insertProductOrder(productOrder: ProductOrder) = appDatabase.productOrderDao().insert(productOrder)

    override suspend fun insertAsset(asset: List<Asset>) = appDatabase.assetDao().insert(asset)
    override suspend fun insertCustomer(customer: List<Customer>) = appDatabase.customerDao().insert(customer)
    override suspend fun insertCustomerType(customerType: List<CustomerType>) = appDatabase.customerTypeDao().insert(customerType)
    override suspend fun insertProductOrder(productOrder: List<ProductOrder>) = appDatabase.productOrderDao().insert(productOrder)
    
    override suspend fun deleteAssets() = appDatabase.assetDao().deleteAll()
    override suspend fun deleteCustomers() = appDatabase.customerDao().deleteAll()
    override suspend fun deleteCustomerTypes() = appDatabase.customerTypeDao().deleteAll()
    override suspend fun deleteProductsOrders() = appDatabase.productOrderDao().deleteAll()


    override fun getAssetsTemp(): LiveData<List<Asset>> = tempDatabase.assetDao().getAll()
    override fun getCustomersTemp(): LiveData<List<Customer>> = tempDatabase.customerDao().getAll()
    override fun getCustomerTypesTemp(): LiveData<List<CustomerType>> = tempDatabase.customerTypeDao().getAll()
    override fun getProductsOrdersTemp(): LiveData<List<ProductOrder>> = tempDatabase.productOrderDao().getAll()
    
    override suspend fun insertAssetTemp(asset: Asset) = tempDatabase.assetDao().insert(asset)
    override suspend fun insertCustomerTemp(customer: Customer) = tempDatabase.customerDao().insert(customer)
    override suspend fun insertCustomerTypeTemp(customerType: CustomerType) = tempDatabase.customerTypeDao().insert(customerType)
    override suspend fun insertProductOrderTemp(productOrder: ProductOrder) = tempDatabase.productOrderDao().insert(productOrder)
    
    override suspend fun deleteAssetsTemp() = tempDatabase.assetDao().deleteAll()
    override suspend fun deleteCustomersTemp() = tempDatabase.customerDao().deleteAll()
    override suspend fun deleteCustomerTypesTemp() = tempDatabase.customerTypeDao().deleteAll()
    override suspend fun deleteProductsOrdersTemp() = tempDatabase.productOrderDao().deleteAll()
    
    override suspend fun postLogin(loginRequest: LoginRequest): Flow<Resource<DataBody<LoginResponse>>> = flow {
        val response = apiServices.postLogin(loginRequest = loginRequest)
        Log.i("TAG", "postLogin: $response")
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

    override suspend fun getCustomerSQLite(body: JsonObject, token: String): Flow<Resource<DataBody<JsonObject>>> = flow {
        val response = apiServices.getCustomerSqlite(body = body, token = token)
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

    override suspend fun getToken(auth: String): Flow<Resource<DataBody<JsonObject>>> = flow {
        val response = apiServices.getToken(auth = auth)
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

    override suspend fun getInfo(keyH: String, type: String): Flow<Resource<DataBody<JsonObject>>> = flow {
        val response = apiServices.getInfo(keyH = keyH, type = "1")
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