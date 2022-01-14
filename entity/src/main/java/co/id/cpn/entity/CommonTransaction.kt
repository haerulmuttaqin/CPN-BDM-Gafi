package co.id.cpn.entity

data class CommonTransaction(
    val assetID: String,
    val beginVisitTime: String,
    val commonSID: String,
    val customerSID: String,
    val endVisitTime: String,
    val geoLat: Double,
    val geoLong: Double,
    val salesSID: String,
    val status: Int,
)
