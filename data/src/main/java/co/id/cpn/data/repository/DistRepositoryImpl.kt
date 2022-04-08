package co.id.cpn.data.repository

import androidx.lifecycle.LiveData
import co.id.cpn.data.local.AppDatabase
import co.id.cpn.domain.distribution.DistRepository
import co.id.cpn.entity.Distribution
import co.id.cpn.entity.Region
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DistRepositoryImpl constructor(
    private val appDatabase: AppDatabase
) : DistRepository {
    override suspend fun insertDist(distribution: Distribution) {
        withContext(Dispatchers.IO) { appDatabase.distributionDao().insert(distribution) }
    }
    override suspend fun insertRegion(region: Region) {
        withContext(Dispatchers.IO) { appDatabase.regionDao().insert(region) }
    }
    override fun getDistributions(): LiveData<List<Distribution>> = appDatabase.distributionDao().getAll()
    override fun getRegionsBySID(regionSID: String): LiveData<Region> = appDatabase.distributionDao().getRegionBySID(regionSID)
    override fun getRegionsBy(distributionSID: String): LiveData<List<Region>> = appDatabase.regionDao().getBy(distributionSID)
    override fun getRegionsListBy(distributionSID: String): List<Region> = appDatabase.regionDao().getListBy(distributionSID)
}