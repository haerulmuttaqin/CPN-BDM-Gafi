package co.id.cpn.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Region(
    @PrimaryKey
    @ColumnInfo(name = "region_sid") val regionSID: String,
    @ColumnInfo(name = "region_name") val regionName: String,
    @ColumnInfo(name = "distribution_sid") val distributionSid: String,
    @ColumnInfo(name = "is_selected") var isSelected: Boolean,
    @ColumnInfo(name = "sqlite_link") var sqLiteLink: String,
    @ColumnInfo(name = "download_status") var downloadStatus: String,
    @ColumnInfo(name = "download_info") var downloadInfo: String,
    @ColumnInfo(name = "size") var size: Int,
    @ColumnInfo(name = "progress") var progress: Int
)
