package com.danilkomyshev.byteshare

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.danilkomyshev.byteshare.di.DaggerAppComponent
import com.danilkomyshev.byteshare.di.InjectableFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class BeamSampleApp : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .setApplication(this)
                .build()
                .inject(this)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(p0: Activity) {
                TODO("Not yet implemented")
            }

            override fun onActivityStarted(p0: Activity) {
                TODO("Not yet implemented")
            }

            override fun onActivityDestroyed(p0: Activity) {
                TODO("Not yet implemented")
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
                TODO("Not yet implemented")
            }

            override fun onActivityStopped(p0: Activity) {
                TODO("Not yet implemented")
            }

            override fun onActivityCreated(p0: Activity, bundle: Bundle?) {
                if (p0 is HasActivityInjector) {
                    AndroidInjection.inject(p0)
                }
                if (p0 is FragmentActivity) {
                    p0.supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if (f is InjectableFragment) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true)
                }
            }

            override fun onActivityResumed(p0: Activity) {
                TODO("Not yet implemented")
            }
            // endregion
        })
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
}
