package co.id.cpn.bdmgafi.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.work.WorkInfo
import androidx.work.WorkManager
import co.id.cpn.bdmgafi.ui.worker.DownloadOperations
import co.id.cpn.bdmgafi.ui.worker.DownloadOperations2
import co.id.cpn.domain.distribution.DistUseCase
import co.id.cpn.domain.main.MainUseCase
import co.id.cpn.domain.news.NewsUseCase
import co.id.cpn.entity.*
import co.id.cpn.entity.util.Constants
import co.id.cpn.entity.util.ResultState
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
class MainViewModel
constructor(
    private val mainUseCase: MainUseCase,
    private val distUseCase: DistUseCase,
    private val newsUseCase: NewsUseCase,
    private val application: Application,
) : ViewModel() {

    private val workManager: WorkManager = WorkManager.getInstance(application)

    private val _distributionSelected = MutableLiveData<String>()
    val distributionSelected: LiveData<String> get() = _distributionSelected
    fun submitSelectedDistribution(customerDistribution: String) {
        _distributionSelected.value = customerDistribution
    }
    
    fun outputStatus(tag: String) : LiveData<List<WorkInfo>> {
        return workManager.getWorkInfosByTagLiveData(tag)
    }

    internal fun apply(downloadOperations: DownloadOperations) {
        downloadOperations.continuation.enqueue()
    }

    internal fun apply2(downloadOperations: DownloadOperations2) {
        downloadOperations.continuation.enqueue()
    }

    internal fun cancel() {
        workManager.cancelUniqueWork(Constants.DOWNLOAD_WORK)
    }

    private val _sqLite = MutableLiveData<Resource<DataBody<JsonObject>>>()
    val sqLite: LiveData<Resource<DataBody<JsonObject>>> get() = _sqLite

    val distributions: LiveData<List<Distribution>> = distUseCase.getDistributions()
    fun regions(distributionSID: String) : LiveData<List<Region>> = distUseCase.getRegionsBy(distributionSID = distributionSID)
    
    fun getNews(): LiveData<List<News>> = newsUseCase.getNews()

    suspend fun provideNews() {
        newsUseCase.provideNews().collectLatest { res ->
            Log.w("TAG", "provideNews view model: $res")
            when (res) {
                is ResultState.Success -> {
                    val newsList = res.data.body().select("div.blogging")
                    for (list in newsList) {
                        for (item in list.select("div.item-blog")) {
                            val title = item.select("h2.title-blog").select("span").text()
                            val content = item.select("div.content-item-blog").select(".id").text()
                            val date = item.select("div.meta-title-blog").text()
                            val link =
                                item.select("div.content-item-blog").select("a.readmore").attr("href")
                            insertNews(
                                News(
                                    title = title,
                                    date = date,
                                    content = content,
                                    link = link
                                )
                            )
                        }
                    }
                }
                is ResultState.Error -> Log.e("ERR", res.throwable.localizedMessage)
                else -> Log.ASSERT
            }
        }
    }

    init {
        viewModelScope.launch {
            provideNews()
        }
    }

    private fun insertNews(news: News) {
        viewModelScope.launch {
            newsUseCase.insert(news)
        }
    }
    
    fun getCustomerSQLite(distributionSID: String, regionSID: String, token: String) {
        val body = JsonObject()
        val regions = JsonArray()
        regions.add(regionSID)
        body.addProperty("distribution_sid", distributionSID)
        body.add("list_region", regions)
        viewModelScope.launch {
            _sqLite.value = Resource.Loading()
            mainUseCase.getCustomerSQLite(
                body = body,
                token = token
            ).collect {
                _sqLite.value = it
            }
        }
    }

    val assetsTemp: LiveData<List<Asset>> = mainUseCase.getAssetsTemp()
    val customersTemp: LiveData<List<Customer>> = mainUseCase.getCustomersTemp()
    val customerTypesTemp: LiveData<List<CustomerType>> = mainUseCase.getCustomerTypesTemp()
    val productsTemp: LiveData<List<ProductOrder>> = mainUseCase.getProductsOrdersTemp()

    fun insertAsset(asset: List<Asset>) { viewModelScope.launch { mainUseCase.insertAsset(asset) } }
    fun insertCustomer(customer: List<Customer>) { viewModelScope.launch { mainUseCase.insertCustomer(customer) } }
    fun insertCustomerType(customerType: List<CustomerType>) { viewModelScope.launch { mainUseCase.insertCustomerType(customerType) } }
    fun insertProduct(productOrder: List<ProductOrder>) { viewModelScope.launch { mainUseCase.insertProductOrder(productOrder) } }
    
    fun deleteAllTransactionData() {
        viewModelScope.launch { 
            mainUseCase.deleteAssets()
            mainUseCase.deleteCustomers()
            mainUseCase.deleteCustomerTypes()
            mainUseCase.deleteProductsOrders()
        }
    }
}

