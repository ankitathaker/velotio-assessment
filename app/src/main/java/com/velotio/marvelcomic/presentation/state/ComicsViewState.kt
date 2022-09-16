package com.velotio.marvelcomic.presentation.state

data class ComicsViewState(
    val characterViewState: CharacterViewState,
    val comicsList: List<ComicViewState>
)
