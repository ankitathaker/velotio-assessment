package com.velotio.marvelcomic.data_layer

sealed class DataSource {
    object Network : DataSource()
    object Cache : DataSource()
}
