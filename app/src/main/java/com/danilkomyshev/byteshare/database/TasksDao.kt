package com.danilkomyshev.byteshare.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TasksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg taskEntity: TaskEntity)

    @Update
    fun update(taskEntity: TaskEntity)

    @Query("SELECT * FROM task")
    fun getAll(): LiveData<List<TaskEntity>>
}
