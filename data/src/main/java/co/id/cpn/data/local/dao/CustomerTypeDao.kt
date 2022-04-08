package co.id.cpn.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.id.cpn.entity.CustomerType

@Dao
interface CustomerTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(customerType: CustomerType)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(customerType: List<CustomerType>)

    @Query("select * from customer_type")
    fun getAll(): LiveData<List<CustomerType>>

    @Query("DELETE FROM customer_type")
    fun deleteAll()
}
