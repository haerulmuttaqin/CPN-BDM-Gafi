package co.id.cpn.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asset")
data class Asset(
    @PrimaryKey
    @ColumnInfo(name = "asset_id") val assetID: String,
    @ColumnInfo(name = "asset_type") val assetType: String,
    @ColumnInfo(name = "customer_sid") val customerSID: String,
)