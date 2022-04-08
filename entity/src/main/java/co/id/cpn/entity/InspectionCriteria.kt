package co.id.cpn.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inspection_criteria")
data class InspectionCriteria(
    @PrimaryKey
    val moduleID: Int,
    val moduleIcon: Int,
    val moduleName: String,
)
