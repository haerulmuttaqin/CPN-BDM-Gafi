package co.id.cpn.domain.distribution

 import androidx.lifecycle.LiveData
 import co.id.cpn.entity.Distribution
 import co.id.cpn.entity.Region

interface DistUseCase {
    suspend fun insertDistribution(distribution: Distribution)
    suspend fun insertRegion(region: Region)
    fun getDistributions(): LiveData<List<Distribution>>
    fun getRegionsBy(distributionSID: String): LiveData<List<Region>>
}