package co.id.cpn.entity.login

import com.google.gson.annotations.SerializedName

data class GetSqliteResponse(
	@field:SerializedName("dataZip") val dataZip: String,
	@field:SerializedName("data") val data: String,
)
