package com.danilkomyshev.byteshare.di

import com.danilkomyshev.byteshare.TasksViewModel
import dagger.Subcomponent


@Subcomponent
interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }
    val tasksViewModel: TasksViewModel
}