package co.id.cpn.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.id.cpn.entity.Module

@Dao
interface ModuleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: Module)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: List<Module>)

    @Query("select * from Module")
    fun getAll(): LiveData<List<Module>>

    @Query("select * from module where moduleID in (:roles)")
    fun getBy(roles: List<Int>): LiveData<List<Module>>

    @Query("delete from UserData")
    fun deleteAll()
}
