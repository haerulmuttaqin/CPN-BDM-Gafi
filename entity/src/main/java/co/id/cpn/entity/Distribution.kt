package co.id.cpn.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Distribution(
    @PrimaryKey
    @ColumnInfo(name = "distribution_sid") var distributionSID: String,
    @ColumnInfo(name = "distribution_name")var distributionName: String
)
