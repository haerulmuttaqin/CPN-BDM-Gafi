package co.id.cpn.entity

data class CommonSurveyTransaction(
    val beginVisitTime: String,
    val commonSID: String,
    val customerSID: String,
    val endVisitTime: String,
    val formID: String,
    val geoLat: Double,
    val geoLong: Double,
    val salesSID: String,
    val status: Int,
)
