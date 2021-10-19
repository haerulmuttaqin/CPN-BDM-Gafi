package co.id.cpn.domain.distribution

import androidx.lifecycle.LiveData
import co.id.cpn.entity.Distribution
import co.id.cpn.entity.Region

class DistUseCaseImpl constructor(private val repository: DistRepository) : DistUseCase {
    override suspend fun insertDistribution(distribution: Distribution) = repository.insertDist(distribution)
    override suspend fun insertRegion(region: Region) = repository.insertRegion(region)
    override fun getDistributions(): LiveData<List<Distribution>> = repository.getDistributions()
    override fun getRegionsBy(distributionSID: String): LiveData<List<Region>> = repository.getRegionsBy(distributionSID = distributionSID)
}
