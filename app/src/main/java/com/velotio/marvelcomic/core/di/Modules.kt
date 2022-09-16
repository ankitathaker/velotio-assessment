package com.velotio.marvelcomic.core.di

import com.velotio.marvelcomic.core.provideAppDatabase
import com.velotio.marvelcomic.core.provideRetrofitService
import com.velotio.marvelcomic.data_layer.MarvelDataRepositoryImpl
import com.velotio.marvelcomic.data_layer.cache.ApplicationDatabase
import com.velotio.marvelcomic.data_layer.remote.MarvelRemoteService
import com.velotio.marvelcomic.domain.MarvelDataRepository
import com.velotio.marvelcomic.domain.interactors.GetCharacter
import com.velotio.marvelcomic.domain.interactors.GetCharacters
import com.velotio.marvelcomic.domain.interactors.GetComics
import com.velotio.marvelcomic.domain.interactors.ToggleCharacterBookmarkStatus
import com.velotio.marvelcomic.presentation.view_model.CharactersListViewModel
import com.velotio.marvelcomic.presentation.view_model.ComicListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun commonDependencies() = module {

    /*
    * Initialized retrofit service
    * */
    single {
        provideRetrofitService<MarvelRemoteService>()
    }

    /*
    * Initialized local database
    * */
    single {
        provideAppDatabase(
            context = get()
        )
    }

    /*
    * Initialized repository
    * */
    single<MarvelDataRepository> {
        MarvelDataRepositoryImpl(
            marvelRemoteService = get(),
            charactersDao = get<ApplicationDatabase>().charactersDao(),
            comicsDao = get<ApplicationDatabase>().comicsDao()
        )
    }
}

fun charactersModule() = module {

    single {
        GetCharacters(
            marvelDataRepository = get()
        )
    }

    single {
        ToggleCharacterBookmarkStatus(
            marvelDataRepository = get()
        )
    }

    viewModel {
        CharactersListViewModel(
            getCharacters = get(),
            toggleCharacterBookmarkStatus = get()
        )
    }
}

fun comicsModule() = module {

    single {
        GetComics(
            marvelDataRepository = get()
        )
    }

    single {
        GetCharacter(
            marvelDataRepository = get()
        )
    }

    viewModel { parameters ->
        ComicListViewModel(
            characterId = parameters.get(),
            getComics = get(),
            getCharacter = get()
        )
    }
}