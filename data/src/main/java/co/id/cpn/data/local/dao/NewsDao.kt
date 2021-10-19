package co.id.cpn.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.id.cpn.entity.News

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(news: News)
    @Query("select * from News")
    fun getNewsCache(): LiveData<List<News>>
}
