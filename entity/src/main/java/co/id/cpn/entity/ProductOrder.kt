package co.id.cpn.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_order")
data class ProductOrder(
    @PrimaryKey
    @ColumnInfo(name = "product_sid") val productSID: String,
    @ColumnInfo(name = "customer_sid") val customerSID: String,
    @ColumnInfo(name = "product_id") val productID: String,
    @ColumnInfo(name = "product_name") val productName: String,
    @ColumnInfo(name = "product_qty1") val productQTY1: Float,
    @ColumnInfo(name = "product_uom1") val productUOM1: String,
    @ColumnInfo(name = "product_qty2") val productQTY2: Float,
    @ColumnInfo(name = "product_uom2") val productUOM2: String,
)
