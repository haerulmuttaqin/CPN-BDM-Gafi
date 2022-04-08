package co.id.cpn.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product_order")
data class ProductOrder(
    @PrimaryKey
    @ColumnInfo(name = "_id") @SerializedName("_id") var _id: String,
    @ColumnInfo(name = "product_sid") @SerializedName("product_sid") val productSID: String,
    @ColumnInfo(name = "customer_sid") @SerializedName("customer_sid") val customerSID: String,
    @ColumnInfo(name = "product_id") @SerializedName("product_id") val productID: String,
    @ColumnInfo(name = "product_name") @SerializedName("product_name") val productName: String,
    @ColumnInfo(name = "product_qty1") @SerializedName("product_qty1") val productQTY1: Float,
    @ColumnInfo(name = "product_qty2") @SerializedName("product_qty2") val productQTY2: Float,
    @ColumnInfo(name = "product_uom1") @SerializedName("product_uom1") val productUOM1: String,
    @ColumnInfo(name = "product_uom2") @SerializedName("product_uom2") val productUOM2: String,
    @ColumnInfo(name = "price_1") @SerializedName("price_1") val price1: Double,
    @ColumnInfo(name = "price_2") @SerializedName("price_2") val price2: Double,
    @ColumnInfo(name = "total_amount_item") @SerializedName("total_amount_item") val totalAmountItem: Float,
)
