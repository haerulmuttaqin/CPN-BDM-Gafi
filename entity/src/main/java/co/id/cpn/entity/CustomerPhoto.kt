package co.id.cpn.entity

import com.google.gson.annotations.SerializedName

data class CustomerPhoto(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("customer_sid")
    val customerSID: String,
    @SerializedName("data")
    val photoData: String,
    @SerializedName("photo_type_sid")
    val photoTypeSID: String,
)