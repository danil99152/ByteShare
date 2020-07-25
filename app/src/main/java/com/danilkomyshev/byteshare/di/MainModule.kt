package com.danilkomyshev.byteshare.di

import com.danilkomyshev.byteshare.AddTaskDialogFragment
import com.danilkomyshev.byteshare.MainActivity
import com.danilkomyshev.byteshare.ReceiveActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainModule {
    @ContributesAndroidInjector
    abstract fun contributeToMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeToReceiveActivity(): ReceiveActivity

    @ContributesAndroidInjector
    abstract fun contributeToAddTaskDialog(): AddTaskDialogFragment
}
