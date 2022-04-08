package co.id.cpn.domain.distribution

import androidx.lifecycle.LiveData
import co.id.cpn.entity.Distribution
import co.id.cpn.entity.Region

interface DistRepository {
    suspend fun insertDist(distribution: Distribution)
    suspend fun insertRegion(region: Region)
    fun getDistributions() : LiveData<List<Distribution>>
    fun getRegionsBySID(regionSID: String): LiveData<Region>
    fun getRegionsBy(distributionSID: String) : LiveData<List<Region>>
    fun getRegionsListBy(distributionSID: String): List<Region>
}