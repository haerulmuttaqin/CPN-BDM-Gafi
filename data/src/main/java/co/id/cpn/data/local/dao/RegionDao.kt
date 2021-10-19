package co.id.cpn.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.id.cpn.entity.Distribution
import co.id.cpn.entity.Region

@Dao
interface RegionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(region: Region)

    @Query("SELECT * FROM Region WHERE distribution_sid =:distributionSID")
    fun getBy(distributionSID: String) : LiveData<List<Region>>

    @Query("DELETE FROM Region")
    fun deleteAll()
}

