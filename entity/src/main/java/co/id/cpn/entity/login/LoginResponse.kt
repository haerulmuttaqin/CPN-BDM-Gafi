package co.id.cpn.entity.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
	@field:SerializedName("list_distribution") val listDistribution: List<ListDistributionItem>,
	@field:SerializedName("authorization") val authorization: String,
	@field:SerializedName("rctl_sid") val rctlSid: String,
	@field:SerializedName("user_sid") val userSid: String,
	@field:SerializedName("user_role") val userRole: String,
	@field:SerializedName("user_name") val userName: String,
)

data class ListDistributionItem(
	@field:SerializedName("distribution_sid") val distributionSID: String,
	@field:SerializedName("distribution_name") val distributionName: String,
	@field:SerializedName("list_region") val listRegion: List<ListRegionItem>
)

data class ListRegionItem(
	@field:SerializedName("region_sid") val regionSID: String,
	@field:SerializedName("region_name") val regionName: String
)
