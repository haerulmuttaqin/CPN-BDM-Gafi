package co.id.cpn.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.id.cpn.entity.Distribution

@Dao
interface DistributionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(distribution: Distribution)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(distribution: List<Distribution>)

    @Query("SELECT * FROM Distribution")
    fun getAll() : LiveData<List<Distribution>>
    
    @Query("DELETE FROM Distribution")
    fun deleteAll()
}