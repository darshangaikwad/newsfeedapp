package com.darshangaikwad.mvvm_kotlin_with_koin

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.multidex.MultiDex
import androidx.preference.PreferenceManager
import com.darshangaikwad.mvvm_kotlin_with_koin.koin.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


/**
 * Created by Darshan Gaikwad on 14,May,2021
 */
class ApplicationClass : Application() {

    lateinit var appContext: Context
    var PREFERENCE_NAME = "applicationClass"
    private var preference: SharedPreferences? = null


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        mInstance = this
        app = this
        preference = PreferenceManager.getDefaultSharedPreferences(this)
        startKoin {
            androidContext(this@ApplicationClass)
            modules(appModules)
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun appVersion(): String {
        val manager = packageManager
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = manager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return packageInfo!!.versionName
    }

    fun getPrefs(): SharedPreferences? {
        return preference
    }


    companion object {
        lateinit var mInstance: ApplicationClass
        lateinit var app: ApplicationClass

        @Synchronized
        fun getInstance(): ApplicationClass {
            return mInstance
        }

        fun getApps(): ApplicationClass {
            return app
        }

    }
}