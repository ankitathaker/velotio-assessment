package com.velotio.marvelcomic.core.di

import com.velotio.marvelcomic.core.provideRetrofitService
import com.velotio.marvelcomic.data_layer.MarvelDataRepositoryImpl
import com.velotio.marvelcomic.data_layer.cache.ApplicationDatabase
import com.velotio.marvelcomic.data_layer.remote.MarvelRemoteService
import com.velotio.marvelcomic.domain.MarvelDataRepository
import com.velotio.marvelcomic.domain.interactors.GetCharacters
import com.velotio.marvelcomic.domain.interactors.ToggleCharacterBookmarkStatus
import com.velotio.marvelcomic.presentation.view_model.CharactersListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun charactersModule() = module {

    single {
        provideRetrofitService<MarvelRemoteService>()
    }

    single<MarvelDataRepository> {
        MarvelDataRepositoryImpl(
            marvelRemoteService = get(),
            charactersDao = get<ApplicationDatabase>().charactersDao()
        )
    }

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