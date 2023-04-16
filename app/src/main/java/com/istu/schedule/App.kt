package com.istu.schedule

import android.app.Application
import com.istu.schedule.data.model.User
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var user: User

    override fun onCreate() {
        super.onCreate()
    }
}
