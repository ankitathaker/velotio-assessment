package com.velotio.marvelcomic

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.velotio.marvelcomic.core.provideAppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MarvelComicApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        /*
        * Enable vector drawable support.
        * */
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        /*
        * Initialize dependency injection.
        * */
        startKoin {
            androidContext(this@MarvelComicApplication)
            /*
            * Initialized local database
            * */
            module {
                single {
                    provideAppDatabase(get())
                }
            }
        }
    }
}