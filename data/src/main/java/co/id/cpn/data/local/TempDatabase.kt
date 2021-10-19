package co.id.cpn.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import co.id.cpn.data.local.dao.*
import co.id.cpn.entity.*

@Database(
    entities = [
        Asset::class,
        Customer::class,
        CustomerType::class,
        ProductOrder::class,
    ], version = 1, exportSchema = false
)
abstract class TempDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
    abstract fun customerDao(): CustomerDao
    abstract fun customerTypeDao(): CustomerTypeDao
    abstract fun productOrderDao(): ProductOrderDao
}