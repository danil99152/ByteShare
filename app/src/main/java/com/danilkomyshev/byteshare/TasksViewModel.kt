package com.danilkomyshev.byteshare

import androidx.lifecycle.ViewModel
import com.danilkomyshev.byteshare.database.TaskEntity
import com.danilkomyshev.byteshare.database.TasksDao
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject


class TasksViewModel @Inject constructor(
        private val tasksDao: TasksDao
) : ViewModel() {

    private val job = Job()

    val tasks = tasksDao.getAll()

    fun saveTask(summary: String) {
        launch(CommonPool + job) {
            val entity = TaskEntity().apply { this.summary = summary }
            tasksDao.save(entity)
        }
    }

    fun updateTask(task: TaskEntity) {
        launch(CommonPool + job) {
            tasksDao.update(task)
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}
