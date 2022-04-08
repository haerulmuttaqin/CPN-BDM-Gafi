package co.id.cpn.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.id.cpn.entity.UserData

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserData)

    @Query("select * from UserData")
    fun getUserData(): LiveData<UserData>

    @Query("delete from UserData")
    fun deleteAll()
}
