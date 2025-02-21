package com.towertex.sdk.network.service

import com.towertex.sdk.network.model.BloomApiResult
import com.towertex.sdk.network.model.UIConfigurationForm

interface ConfigurationApiContract {

    suspend fun getConfigurationForm(
        id: Int
    ): BloomApiResult<UIConfigurationForm>
}