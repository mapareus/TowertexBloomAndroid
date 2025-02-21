package com.towertex.sdk.sdk.service

import com.towertex.sdk.datastore.JsonDataStoreContract
import com.towertex.sdk.network.model.Success
import com.towertex.sdk.network.model.UIConfigurationForm
import com.towertex.sdk.network.service.ConfigurationApiContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ConfigurationSdk(
    private val jsonDataStore: JsonDataStoreContract,
    private val configurationApi: ConfigurationApiContract,
): ConfigurationSdkContract {

    private suspend fun loadNew(configurationId: Int) {
        val result = configurationApi.getConfigurationForm(configurationId)
        val response: UIConfigurationForm = (result as? Success)?.data ?: return
        jsonDataStore.store(Json.encodeToString(response))
    }

    override fun getUIConfigurationForm(
        coroutineScope: CoroutineScope?,
        configurationId: Int
    ): Flow<UIConfigurationForm> {
        coroutineScope?.launch { loadNew(configurationId) }
        return jsonDataStore.jsonFlow.map { Json.decodeFromString(it) }
    }
}