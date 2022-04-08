package co.id.cpn.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "customer_type")
data class CustomerType(
    @PrimaryKey
    @ColumnInfo(name = "customer_type_sid") @SerializedName("customer_type_sid") val customerTypeSid: String,
    @ColumnInfo(name = "customer_type_name") @SerializedName("customer_type_name") val customerTypeName: String,
    @ColumnInfo(name = "customer_type_image") @SerializedName("customer_type_image") val customerTypeImage: String,
)
