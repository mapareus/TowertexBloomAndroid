package com.towertex.sdk.datastore

import kotlinx.coroutines.flow.Flow

interface JsonDataStoreContract {
    val jsonFlow: Flow<String>
    suspend fun store(formJson: String)
}