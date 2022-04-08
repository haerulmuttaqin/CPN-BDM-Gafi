package co.id.cpn.entity

data class DataBody<T>(
    val status: String,
    val message: String,
    val data: T,
    val date: String
)
 