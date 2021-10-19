package co.id.cpn.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey
    @ColumnInfo(name = "customer_sid") val customerSID: String,
    @ColumnInfo(name = "customer_id") val customerID: String,
    @ColumnInfo(name = "customer_vipcode") val customerVipCode: String,
    @ColumnInfo(name = "customer_name") val customerName: String,
    @ColumnInfo(name = "customer_address") val customerAddress: String,
    @ColumnInfo(name = "customer_type_sid") val customerTypeSid: String,
    @ColumnInfo(name = "customer_order") val customerOrder: String,
    @ColumnInfo(name = "customer_inspection") val customerInspection: String,
)
