package com.towertex.sdk.network

import com.towertex.sdk.network.service.ConfigurationApi
import com.towertex.sdk.network.service.ConfigurationApiContract
import com.towertex.sdk.network.service.ReportingApi
import com.towertex.sdk.network.service.ReportingApiContract
import io.ktor.client.HttpClient

class BloomApi internal constructor(
    httpClient: HttpClient,
    baseUrl: String,
):
    ConfigurationApiContract by ConfigurationApi(httpClient, baseUrl),
    ReportingApiContract by ReportingApi(httpClient, baseUrl)