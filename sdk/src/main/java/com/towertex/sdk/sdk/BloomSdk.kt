package com.towertex.sdk.sdk

import com.towertex.sdk.datastore.JsonDataStoreContract
import com.towertex.sdk.network.service.ConfigurationApiContract
import com.towertex.sdk.network.service.ReportingApiContract
import com.towertex.sdk.room.service.BloomDaoContract
import com.towertex.sdk.sdk.model.BloomSDKConfiguration
import com.towertex.sdk.sdk.service.ConfigurationSdk
import com.towertex.sdk.sdk.service.ConfigurationSdkContract
import com.towertex.sdk.sdk.service.ReportingSdk
import com.towertex.sdk.sdk.service.ReportingSdkContract

class BloomSdk internal constructor(
    private val jsonDataStore: JsonDataStoreContract,
    private val dao: BloomDaoContract,
    private val configurationApi: ConfigurationApiContract,
    private val reportingApi: ReportingApiContract,
    private val sdkConfiguration: BloomSDKConfiguration,
):
        ConfigurationSdkContract by ConfigurationSdk(jsonDataStore, configurationApi),
        ReportingSdkContract by ReportingSdk(dao, reportingApi, sdkConfiguration)