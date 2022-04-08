package co.id.cpn.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    @PrimaryKey
    @ColumnInfo(name = "userSID") val userSID: String,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "userRole") val userRole: String,

)
