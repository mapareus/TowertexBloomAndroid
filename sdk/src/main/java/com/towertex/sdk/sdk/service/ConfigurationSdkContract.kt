package com.towertex.sdk.sdk.service

import com.towertex.sdk.network.model.UIConfigurationForm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ConfigurationSdkContract {
    fun getUIConfigurationForm(
        coroutineScope: CoroutineScope?,
        configurationId: Int
    ): Flow<UIConfigurationForm>
}