package com.danilkomyshev.byteshare.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danilkomyshev.byteshare.TasksViewModel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ViewModelFactory @Inject constructor(
        private val viewModelSubComponent: ViewModelSubComponent
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when(modelClass) {
        TasksViewModel::class.java -> viewModelSubComponent.tasksViewModel as T
        else -> throw RuntimeException("Unknown model class ${modelClass?.canonicalName}")
    }
}
