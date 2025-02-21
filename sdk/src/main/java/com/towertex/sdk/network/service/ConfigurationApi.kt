package com.towertex.sdk.network.service

import com.towertex.sdk.network.model.UIConfigurationForm
import com.towertex.sdk.network.model.toResult
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url

class ConfigurationApi(
    private val httpClient: HttpClient,
    private val baseUrl: String,
): ConfigurationApiContract {

    override suspend fun getConfigurationForm(
        id: Int
    ) = httpClient.toResult<UIConfigurationForm> {
        get {
            url(StringBuilder()
                .append(baseUrl)
                .append("ui_configuration_form/")
                .append(id)
                .toString()
            )
        }
    }
}