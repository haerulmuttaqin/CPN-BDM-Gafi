package co.id.cpn.data.repository

import androidx.lifecycle.LiveData
import co.id.cpn.data.local.AppDatabase
import co.id.cpn.data.remote.NewsServices
import co.id.cpn.domain.news.NewsRepository
import co.id.cpn.entity.News
import co.id.cpn.entity.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.jsoup.nodes.Document

class NewsRepositoryImpl constructor(
    private val newsServices: NewsServices,
    private val appDatabase: AppDatabase
) : NewsRepository {
    override fun provideNews(): Flow<ResultState<Document>> = flow {
        try {
            val response = newsServices.getNews()
            emit(ResultState.Success(response))
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    override fun getNews(): LiveData<List<News>> = appDatabase.newsDao().getNewsCache()

    override suspend fun insert(news: News) {
        withContext(Dispatchers.IO) { appDatabase.newsDao().insert(news) }
    }
}