package co.id.cpn.entity.login

data class LoginRequest(
    val username: String,
    val password: String,
    val keyS: String,
    val keyH: String,
    val appversion: String,
    val oprname: String,
    val geolang: Double,
    val geolat: Double,
    val clientdate: String,
    var isRemember: Boolean
)
