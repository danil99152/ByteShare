package com.danilkomyshev.byteshare.di

import android.app.Application
import com.danilkomyshev.byteshare.BeamSampleApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        AppModule::class,
        MainModule::class
))
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun setApplication(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(beamSampleApp: BeamSampleApp)
}
