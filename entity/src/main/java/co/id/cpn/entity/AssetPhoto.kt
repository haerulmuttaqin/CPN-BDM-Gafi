package co.id.cpn.entity

import com.google.gson.annotations.SerializedName

data class AssetPhoto(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("asset_sid")
    val assetSID: String,
    @SerializedName("data")
    val photoData: String,
)
