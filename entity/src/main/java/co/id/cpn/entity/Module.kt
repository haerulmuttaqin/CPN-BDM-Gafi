package co.id.cpn.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Module(
    @PrimaryKey
    @ColumnInfo(name = "moduleID") val moduleID: Int,
    @ColumnInfo(name = "moduleName") val moduleName: String,
    @ColumnInfo(name = "moduleIcon") val moduleIcon: Int,
)
