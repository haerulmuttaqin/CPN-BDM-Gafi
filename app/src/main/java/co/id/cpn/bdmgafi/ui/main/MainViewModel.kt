package co.id.cpn.bdmgafi.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import co.id.cpn.bdmgafi.ui.worker.DownloadRegionOperations
import co.id.cpn.domain.distribution.DistUseCase
import co.id.cpn.domain.main.MainUseCase
import co.id.cpn.domain.news.NewsUseCase
import co.id.cpn.entity.*
import co.id.cpn.entity.util.Constants
import co.id.cpn.entity.util.ResultState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.jvm.internal.Intrinsics



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

    internal fun applyDownloadCustomer(downloadRegionOperations: DownloadRegionOperations) {
        downloadRegionOperations.continuation.enqueue()
    }

    internal fun cancelWork() {
        workManager.cancelUniqueWork(Constants.DOWNLOAD_WORK)
    }

    internal fun cancelWorkByTag(tag: String) {
        workManager.cancelUniqueWork(tag)
    }

    fun updateDownloadStatus(regionSid: String, downloadStatus: String, downloadInfo: String, size: Int, progress: Int) {
        viewModelScope.launch {
            mainUseCase.updateDownloadStatus(regionSid, downloadStatus, downloadInfo, progress, size)
        }
    }

    val distributions: LiveData<List<Distribution>> = distUseCase.getDistributions()
    fun regions(distributionSID: String): LiveData<List<Region>> = distUseCase.getRegionsBy(distributionSID = distributionSID)
    fun regionsList(distributionSID: String): List<Region> = distUseCase.getRegionsListBy(distributionSID = distributionSID)

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

    private val rolesData = arrayListOf<Int>()

    fun getRolesData(): ArrayList<Int> {
        return rolesData
    }

    fun getUserModule(roles: String) {
        Intrinsics.checkNotNullParameter(roles, "roles")
        try {
            val `$this$forEachIndexed$iv` = roles.toCharArray()
            var `index$iv` = 0
            val length = `$this$forEachIndexed$iv`.size
            var i = 0
            while (i < length) {
                val `index$iv2` = `index$iv` + 1
                if (Intrinsics.areEqual(
                        `$this$forEachIndexed$iv`[i].toString() as Any,
                        "1" as Any
                    )
                ) {
                    getRolesData().add(Integer.valueOf(`index$iv`))
                }
                i++
                `index$iv` = `index$iv2`
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun modules(roles: List<Int>): LiveData<List<Module>> {
        return mainUseCase.getModule(roles)
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

    fun regionsBySID(regionSID: String): LiveData<Region> {
        return distUseCase.getRegionsBySID(regionSID)
    }
}

