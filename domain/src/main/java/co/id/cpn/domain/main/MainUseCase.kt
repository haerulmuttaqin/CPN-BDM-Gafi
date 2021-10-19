package co.id.cpn.domain.main

 import androidx.lifecycle.LiveData
 import co.id.cpn.entity.*
 import co.id.cpn.entity.login.LoginRequest
 import co.id.cpn.entity.login.LoginResponse
 import com.google.gson.JsonObject
 import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    suspend fun getInfo(keyH: String, type: String): Flow<Resource<DataBody<JsonObject>>>
    suspend fun postLogin(loginRequest: LoginRequest): Flow<Resource<DataBody<LoginResponse>>>
    suspend fun getToken(auth: String): Flow<Resource<DataBody<JsonObject>>>
    suspend fun getCustomerSQLite(body: JsonObject, token: String): Flow<Resource<DataBody<JsonObject>>>

    suspend fun insertAsset(asset: Asset)
    suspend fun insertCustomer(customer: Customer)
    suspend fun insertCustomerType(customerType: CustomerType)
    suspend fun insertProductOrder(productOrder: ProductOrder)
    
    suspend fun insertAsset(asset: List<Asset>)
    suspend fun insertCustomer(customer: List<Customer>)
    suspend fun insertCustomerType(customerType: List<CustomerType>)
    suspend fun insertProductOrder(productOrder: List<ProductOrder>)

    fun getAssets(): LiveData<List<Asset>>
    fun getAssetsBy(customerSid: String): LiveData<List<Asset>>
    fun getCustomers(): LiveData<List<Customer>>
    fun getCustomerTypes(): LiveData<List<CustomerType>>
    fun getProductsOrders(): LiveData<List<ProductOrder>>
    
    fun getCustomersItems(): LiveData<List<CustomerItem>>
    fun getCustomerBy(customerSID: String): LiveData<CustomerItem>

    suspend fun deleteAssets()
    suspend fun deleteCustomers()
    suspend fun deleteCustomerTypes()
    suspend fun deleteProductsOrders()

    fun getAssetsTemp(): LiveData<List<Asset>>
    fun getCustomersTemp(): LiveData<List<Customer>>
    fun getCustomerTypesTemp(): LiveData<List<CustomerType>>
    fun getProductsOrdersTemp(): LiveData<List<ProductOrder>>
}