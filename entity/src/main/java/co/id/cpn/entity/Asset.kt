package co.id.cpn.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "asset")
data class Asset(
    @PrimaryKey
    @ColumnInfo(name = "asset_id") @SerializedName("asset_id") val assetID: String,
    @ColumnInfo(name = "asset_sid") @SerializedName("asset_sid") val assetSID: String,
    @ColumnInfo(name = "asset_type") @SerializedName("asset_type") val assetType: String,
    @ColumnInfo(name = "customer_sid") @SerializedName("customer_sid") val customerSID: String,
    @ColumnInfo(name = "last_inspected") @SerializedName("last_inspected") val lastInspected: String,
    @ColumnInfo(name = "asset_merk") @SerializedName("asset_merk") val assetMerk: String,
    @ColumnInfo(name = "asset_name") @SerializedName("asset_name") val assetName: String,
    @ColumnInfo(name = "asset_noseri") @SerializedName("asset_noseri") val assetNoSeri: String,
    @ColumnInfo(name = "asset_ownership") @SerializedName("asset_ownership") val assetOwnership: String,
    @ColumnInfo(name = "asset_size") @SerializedName("asset_size") val assetSize: String,
    @ColumnInfo(name = "dist_status") @SerializedName("dist_status") val distStatus: String,
    @ColumnInfo(name = "status_color") @SerializedName("status_color") val statusColor: String,
    @ColumnInfo(name = "launch_time") @SerializedName("launch_time") val launchTime: String,
)