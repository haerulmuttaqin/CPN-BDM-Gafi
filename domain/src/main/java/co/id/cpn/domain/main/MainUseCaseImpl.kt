package co.id.cpn.domain.main

import androidx.lifecycle.LiveData
import co.id.cpn.entity.*
import co.id.cpn.entity.login.LoginRequest
import co.id.cpn.entity.login.LoginResponse
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

class MainUseCaseImpl constructor(private val repository: MainRepository) : MainUseCase {
    override suspend fun postLogin(loginRequest: LoginRequest): Flow<Resource<DataBody<LoginResponse>>> = repository.postLogin(loginRequest)
    override suspend fun getCustomerSQLite(body: JsonObject, token: String) : Flow<Resource<DataBody<JsonObject>>> = repository.getCustomerSQLite(body, token)
    override suspend fun updateDownloadStatus(
        regionSid: String,
        downloadStatus: String,
        downloadInfo: String,
        progress: Int, size: Int
    ) {
        repository.updateDownloadStatus(regionSid, downloadStatus, downloadInfo, progress, size)
    }

    override suspend fun insertUser(userData: UserData) = repository.insertUser(userData)
    override fun getUserData(): LiveData<UserData> = repository.getUserData()

    override suspend fun insertModules(list: List<Module>) = repository.insertModules(list)
    override fun getModule(roles: List<Int>): LiveData<List<Module>> = repository.getModule(roles)

    override suspend fun getToken(auth: String) : Flow<Resource<DataBody<JsonObject>>> = repository.getToken(auth)
    override suspend fun getInfo(keyH: String, type: String): Flow<Resource<DataBody<JsonObject>>> = repository.getInfo(keyH, type)
    
    override suspend fun insertAsset(asset: Asset) = repository.insertAsset(asset)
    override suspend fun insertCustomer(customer: Customer) = repository.insertCustomer(customer)
    override suspend fun insertCustomerType(customerType: CustomerType) = repository.insertCustomerType(customerType)
    override suspend fun insertProductOrder(productOrder: ProductOrder) = repository.insertProductOrder(productOrder)
    
    override suspend fun insertAsset(asset: List<Asset>) = repository.insertAsset(asset)
    override suspend fun insertCustomer(customer: List<Customer>) = repository.insertCustomer(customer)
    override suspend fun insertCustomerType(customerType: List<CustomerType>) = repository.insertCustomerType(customerType)
    override suspend fun insertProductOrder(productOrder: List<ProductOrder>) = repository.insertProductOrder(productOrder)
    
    override fun getAssets(): LiveData<List<Asset>> = repository.getAssets()
    override fun getAssetsBy(customerSid: String): LiveData<List<Asset>> = repository.getAssetsBy(customerSid)

    override fun getCustomers(): LiveData<List<Customer>> = repository.getCustomers() 
    override fun getCustomerTypes(): LiveData<List<CustomerType>> = repository.getCustomerTypes() 
    override fun getProductsOrders(): LiveData<List<ProductOrder>> = repository.getProductsOrders()
    override fun getCustomersItems(): LiveData<List<CustomerItem>> = repository.getCustomersItems()
    override fun getCustomerBy(customerSID: String): LiveData<CustomerItem> = repository.getCustomerBy(customerSID)

    override suspend fun deleteAssets() = repository.deleteAssets()
    override suspend fun deleteCustomers() = repository.deleteCustomers()
    override suspend fun deleteCustomerTypes() = repository.deleteCustomerTypes()
    override suspend fun deleteProductsOrders() = repository.deleteProductsOrders()
    
    override fun getAssetsTemp(): LiveData<List<Asset>> = repository.getAssetsTemp()
    override fun getCustomersTemp(): LiveData<List<Customer>> = repository.getCustomersTemp()
    override fun getCustomerTypesTemp(): LiveData<List<CustomerType>> = repository.getCustomerTypesTemp()
    override fun getProductsOrdersTemp(): LiveData<List<ProductOrder>> = repository.getProductsOrdersTemp()
}
