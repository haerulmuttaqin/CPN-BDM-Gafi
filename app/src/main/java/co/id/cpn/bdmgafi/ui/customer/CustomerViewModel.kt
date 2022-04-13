package co.id.cpn.bdmgafi.ui.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import co.id.cpn.domain.main.MainUseCase
import co.id.cpn.entity.Asset
import co.id.cpn.entity.CustomerItem

class CustomerViewModel
constructor(
    private val mainUseCase: MainUseCase,
) : ViewModel() {
    val customers: LiveData<List<CustomerItem>> = mainUseCase.getCustomersItems()
    fun customerBy(customerSID: String): LiveData<CustomerItem> = mainUseCase.getCustomerBy(customerSID)
    fun customerAssetsBy(customerSID: String): LiveData<List<Asset>> = mainUseCase.getAssetsBy(customerSID)

}

