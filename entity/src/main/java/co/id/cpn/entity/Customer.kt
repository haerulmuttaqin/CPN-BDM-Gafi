package co.id.cpn.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey
    @ColumnInfo(name = "_id") @SerializedName("_id") var _id: String,
    @ColumnInfo(name = "customer_sid") @SerializedName("customer_sid") val customerSID: String,
    @ColumnInfo(name = "customer_id") @SerializedName("customer_id") val customerID: String,
    @ColumnInfo(name = "distribution_sid") @SerializedName("distribution_sid") var distributionSID: String,
    @ColumnInfo(name = "region_sid") @SerializedName("region_sid") var regionSID: String,
    @ColumnInfo(name = "customer_vipcode") @SerializedName("customer_vipcode") val customerVipCode: String,
    @ColumnInfo(name = "customer_name") @SerializedName("customer_name") val customerName: String,
    @ColumnInfo(name = "customer_address") @SerializedName("customer_address") val customerAddress: String,
    @ColumnInfo(name = "customer_type_sid") @SerializedName("customer_type_sid") val customerTypeSid: String,
    @ColumnInfo(name = "customer_order") @SerializedName("customer_order") val customerOrder: String,
    @ColumnInfo(name = "customer_inspection") @SerializedName("customer_inspection") val customerInspection: String,
    @ColumnInfo(name = "longitude") @SerializedName("longitude") val longitude: Double,
    @ColumnInfo(name = "latitude") @SerializedName("latitude") val latitude: Double,
    @ColumnInfo(name = "last_order") @SerializedName("last_order") val lastOrder: String,
    @ColumnInfo(name = "customer_phone") @SerializedName("customer_phone") val customerPhone: String,
    @ColumnInfo(name = "last_status_visit") @SerializedName("last_status_visit") val lastStatusVisit: Int,
)
