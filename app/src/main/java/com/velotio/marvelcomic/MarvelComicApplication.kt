package com.velotio.marvelcomic

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.velotio.marvelcomic.core.di.charactersModule
import com.velotio.marvelcomic.core.di.comicsModule
import com.velotio.marvelcomic.core.di.commonDependencies
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

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
            * Initialized dependency modules
            * */
            modules(
                commonDependencies(),
                charactersModule(),
                comicsModule()
            )
        }
    }
}