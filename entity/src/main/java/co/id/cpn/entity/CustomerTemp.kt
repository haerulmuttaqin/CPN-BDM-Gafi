package co.id.cpn.entity

import com.google.gson.annotations.SerializedName

data class CustomerTemp(
    @SerializedName("customer_address")
    val customerAddress: String,
    @SerializedName("customer_id")
    val customerID: String,
    @SerializedName("customer_inspection")
    val customerInspection: String,
    @SerializedName("customer_name")
    val customerName: String,
    @SerializedName("customer_order")
    val customerOrder: String,
    @SerializedName("customer_phone")
    val customerPhone: String,
    @SerializedName("customer_sid")
    val customerSID: String,
    @SerializedName("customer_type_sid")
    val customerTypeSid: String,
    @SerializedName("customer_vipcode")
    val customerVipCode: String,
    @SerializedName("last_order")
    val lastOrder: String,
    @SerializedName("last_status_visit")
    val lastStatusVisit: Int,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
)
