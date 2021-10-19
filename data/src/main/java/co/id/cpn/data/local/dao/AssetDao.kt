package co.id.cpn.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.id.cpn.entity.Asset

@Dao
interface AssetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(asset: Asset)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(asset: List<Asset>)

    @Query("select * from asset")
    fun getAll(): LiveData<List<Asset>>

    @Query("select * from asset where customer_sid=:custSid")
    fun getBy(custSid: String): LiveData<List<Asset>>

    @Query("DELETE FROM asset")
    fun deleteAll()
}
