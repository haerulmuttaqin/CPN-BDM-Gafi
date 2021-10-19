package co.id.cpn.domain.news

import androidx.lifecycle.LiveData
import co.id.cpn.entity.News
import co.id.cpn.entity.util.ResultState
import kotlinx.coroutines.flow.Flow
import org.jsoup.nodes.Document

class NewsUseCaseImpl constructor(private val repository: NewsRepository) : NewsUseCase {
    override fun provideNews(): Flow<ResultState<Document>> = repository.provideNews()
    override fun getNews(): LiveData<List<News>> = repository.getNews()
    override suspend fun insert(news: News) = repository.insert(news)

}