package co.id.cpn.domain.main

import androidx.lifecycle.LiveData
import co.id.cpn.entity.*
import co.id.cpn.entity.login.LoginRequest
import co.id.cpn.entity.login.LoginResponse
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getInfo(keyH: String, type: String): Flow<Resource<DataBody<JsonObject>>>
    suspend fun postLogin(loginRequest: LoginRequest): Flow<Resource<DataBody<LoginResponse>>>
    suspend fun getToken(auth: String): Flow<Resource<DataBody<JsonObject>>>
    suspend fun getCustomerSQLite(body: JsonObject, token: String): Flow<Resource<DataBody<JsonObject>>>

    suspend fun updateDownloadStatus(regionSid: String, downloadStatus: String, downloadInfo: String, progress: Int, size: Int)

    suspend fun insertUser(userData: UserData)
    fun getUserData(): LiveData<UserData>

    suspend fun insertModules(list: List<Module>)
    fun getModule(roles: List<Int>): LiveData<List<Module>>

    suspend fun insertAsset(asset: Asset)
    suspend fun insertCustomer(customer: Customer)
    suspend fun insertCustomerType(customerType: CustomerType)
    suspend fun insertProductOrder(productOrder: ProductOrder)

    suspend fun insertAsset(asset: List<Asset>)
    suspend fun insertCustomer(customer: List<Customer>)
    suspend fun insertCustomerType(customerType: List<CustomerType>)
    suspend fun insertProductOrder(productOrder: List<ProductOrder>)
    
    suspend fun insertAssetTemp(asset: Asset)
    suspend fun insertCustomerTemp(customer: Customer)
    suspend fun insertCustomerTypeTemp(customerType: CustomerType)
    suspend fun insertProductOrderTemp(productOrder: ProductOrder)
    
    fun getAssets(): LiveData<List<Asset>>
    fun getAssetsBy(customerSid: String): LiveData<List<Asset>>
    fun getCustomers(): LiveData<List<Customer>>
    fun getCustomerBy(customerSID: String): LiveData<CustomerItem>
    fun getCustomerTypes(): LiveData<List<CustomerType>>
    fun getProductsOrders(): LiveData<List<ProductOrder>>
    
    fun getCustomersItems(): LiveData<List<CustomerItem>>
    
    fun getAssetsTemp(): LiveData<List<Asset>>
    fun getCustomersTemp(): LiveData<List<Customer>>
    fun getCustomerTypesTemp(): LiveData<List<CustomerType>>
    fun getProductsOrdersTemp(): LiveData<List<ProductOrder>>

    suspend fun deleteAssets()
    suspend fun deleteCustomers()
    suspend fun deleteCustomerTypes()
    suspend fun deleteProductsOrders()

    suspend fun deleteAssetsTemp()
    suspend fun deleteCustomersTemp()
    suspend fun deleteCustomerTypesTemp()
    suspend fun deleteProductsOrdersTemp()

}