package co.id.cpn.data.remote

import org.jsoup.nodes.Document
import retrofit2.http.GET

interface NewsServices {
    
    @GET("blog")
    suspend fun getNews(): Document
}