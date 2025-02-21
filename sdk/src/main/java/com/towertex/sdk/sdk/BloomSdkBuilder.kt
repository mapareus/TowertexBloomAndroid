package com.towertex.sdk.sdk

import android.content.Context
import com.towertex.sdk.datastore.JsonDataStore
import com.towertex.sdk.datastore.JsonDataStoreContract
import com.towertex.sdk.network.BloomApiBuilder
import com.towertex.sdk.network.service.ConfigurationApiContract
import com.towertex.sdk.network.service.ReportingApiContract
import com.towertex.sdk.room.BloomDatabase
import com.towertex.sdk.room.service.BloomDaoContract
import com.towertex.sdk.sdk.model.BloomSDKConfiguration

class BloomSdkBuilder(
    private val context: Context,
    private val configuration: BloomSDKConfiguration
) {
    private lateinit var configurationApi: ConfigurationApiContract
    private lateinit var reportingApi: ReportingApiContract
    private lateinit var dao: BloomDaoContract
    private lateinit var dataStore: JsonDataStoreContract

    fun build(): BloomSdk {
        BloomApiBuilder {
            setEngineToMock()
        }.build().also {
            configurationApi = it
            reportingApi = it
        }
        dao = BloomDatabase.buildDatabase(context).dao
        dataStore = JsonDataStore(context)
        return BloomSdk(
            dataStore,
            dao,
            configurationApi,
            reportingApi,
            configuration
        )
    }

    companion object {
        private const val DEFAULT_THRESHOLD = 3
        val defaultConfiguration = BloomSDKConfiguration(reportThreshold = DEFAULT_THRESHOLD)
    }
}