package com.danilkomyshev.byteshare.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TaskEntity::class), version = 1)
abstract class TasksDatabase : RoomDatabase() {
    companion object {
        fun create(context: Context): TasksDatabase =
                Room.databaseBuilder(context, TasksDatabase::class.java, "beam-sample.db").build()
    }

    abstract val taskDao: TasksDao
}
