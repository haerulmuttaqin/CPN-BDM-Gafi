package co.id.cpn.domain.news

 import androidx.lifecycle.LiveData
 import co.id.cpn.entity.News
 import co.id.cpn.entity.util.ResultState
 import kotlinx.coroutines.flow.Flow
 import org.jsoup.nodes.Document

interface NewsUseCase {
    fun provideNews(): Flow<ResultState<Document>>
    fun getNews(): LiveData<List<News>>
    suspend fun insert(news: News)
}