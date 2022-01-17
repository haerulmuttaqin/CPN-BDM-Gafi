package co.id.cpn.entity

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

data class CustomerItem(
    @ColumnInfo(name = "customer_address") val customerAddress: String?,
    @ColumnInfo(name = "customer_asset_id") val customerAssetID: String?,
    @ColumnInfo(name = "customer_asset_type") val customerAssetType: String?,
    @ColumnInfo(name = "customer_id") val customerID: String?,
    @ColumnInfo(name = "customer_inspection") val customerInspection: String?,
    @ColumnInfo(name = "customer_name") val customerName: String?,
    @ColumnInfo(name = "customer_order") val customerOrder: String?,
    @ColumnInfo(name = "customer_phone") val customerPhone: String?,
    @ColumnInfo(name = "customer_sid") val customerSID: String,
    @ColumnInfo(name = "customer_type_image") val customerTypeImage: String?,
    @ColumnInfo(name = "customer_type_name") val customerTypeName: String?,
    @ColumnInfo(name = "customer_type_sid") val customerTypeSid: String?,
    @ColumnInfo(name = "customer_vip_code") val customerVipCode: String?,
    @ColumnInfo(name = "distribution_sid") val distributionSID: String?,
    @ColumnInfo(name = "last_order") val lastOrder: String?,
    @ColumnInfo(name = "latitude") val latitude: Double?,
    @ColumnInfo(name = "longitude") val longitude: Double?,
    @ColumnInfo(name = "product_order") val productOrder: String?,
)
