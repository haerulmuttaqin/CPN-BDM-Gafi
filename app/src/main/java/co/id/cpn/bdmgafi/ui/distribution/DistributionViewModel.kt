package co.id.cpn.bdmgafi.ui.distribution

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.cpn.domain.distribution.DistUseCase
import co.id.cpn.entity.Distribution
import co.id.cpn.entity.Region

class DistributionViewModel constructor(
    private val distUseCase: DistUseCase,
) : ViewModel() {

    private val _distributionSelected = MutableLiveData<String>()
    val distributionSelected: LiveData<String> get() = _distributionSelected
    fun submitSelectedDistribution(customerDistribution: String) {
        _distributionSelected.value = customerDistribution
    }

    val distributions: LiveData<List<Distribution>> = distUseCase.getDistributions()
    fun regions(distributionSID: String): LiveData<List<Region>> = distUseCase.getRegionsBy(distributionSID = distributionSID)
}