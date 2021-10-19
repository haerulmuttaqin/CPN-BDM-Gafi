package co.id.cpn.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
    @PrimaryKey
    val link: String,
    val date: String,
    val title: String,
    val content: String
)
