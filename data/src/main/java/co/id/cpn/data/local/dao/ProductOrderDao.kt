package co.id.cpn.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.id.cpn.entity.ProductOrder

@Dao
interface ProductOrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productOrder: ProductOrder)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productOrder: List<ProductOrder>)

    @Query("select * from product_order")
    fun getAll(): LiveData<List<ProductOrder>>

    @Query("DELETE FROM product_order")
    fun deleteAll()
}
