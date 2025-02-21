package com.towertex.sdk

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.towertex.sdk.datastore.JsonDataStore
import com.towertex.sdk.datastore.JsonDataStoreContract
import com.towertex.sdk.network.BloomApiBuilder
import com.towertex.sdk.network.service.ConfigurationApiContract
import com.towertex.sdk.network.service.ReportingApiContract
import com.towertex.sdk.room.BloomDatabase
import com.towertex.sdk.room.model.UserInput
import com.towertex.sdk.room.service.BloomDao
import com.towertex.sdk.room.service.BloomDaoContract
import com.towertex.sdk.sdk.BloomSdk
import com.towertex.sdk.sdk.model.BloomSDKConfiguration
import com.towertex.sdk.sdk.service.ConfigurationSdk
import com.towertex.sdk.sdk.service.ConfigurationSdkContract
import com.towertex.sdk.sdk.service.ReportingSdkContract
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class BloomSdkInstrumentedTest {

    companion object {
        private const val REPORT_THRESHOLD = 3

        private lateinit var appContext: Context
        private lateinit var configurationApi: ConfigurationApiContract
        private lateinit var reportingApi: ReportingApiContract
        private lateinit var dao: BloomDaoContract
        private lateinit var dataStore: JsonDataStoreContract
        private lateinit var configurationSdk: ConfigurationSdkContract
        private lateinit var bloomSdkConfiguration: BloomSDKConfiguration
        private lateinit var reportingSdk: ReportingSdkContract
    }

    @Before
    fun init(): Unit = runBlocking {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        BloomApiBuilder {
            setLogAll()
            setDebugLogger()
            setEngineToMock()
        }.build().also {
            configurationApi = it
            reportingApi = it
        }
        dao = BloomDatabase.buildDatabase(appContext).dao
        dataStore = JsonDataStore(appContext)
        bloomSdkConfiguration = BloomSDKConfiguration(reportThreshold = REPORT_THRESHOLD)
        BloomSdk(
            dataStore,
            dao,
            configurationApi,
            reportingApi,
            bloomSdkConfiguration
        ).also {
            configurationSdk = it
            reportingSdk = it
        }
    }

    @Test
    fun dummyTest() {
        // Context of the app under test.
        assertEquals("com.towertex.sdk.test", appContext.packageName)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getUIConfigurationFormTest(): Unit = runTest {
        val flowOfConfigurations = configurationSdk.getUIConfigurationForm(this, 1)
        launch {
            val data = flowOfConfigurations.first()
            assertTrue("UIConfigurationForm1 should be there", data.submitButtonLabel == "Submit1")
        }
        advanceUntilIdle()
    }

    @Test
    fun reportUserInputsTest(): Unit = runTest {
        var successCalled = 0
        var failureCalled = 0
        val successCallback = { successCalled = successCalled.inc() }
        val failureCallback = { failureCalled = failureCalled.inc() }

        //report below threshold
        reportingSdk.reportUserInputs(
            inputs = listOf(
                UserInput("key1", "val1", 1001),
                UserInput("key2", "val2", 1002),
            ),
            successCallback = successCallback,
            failureCallback = failureCallback,
        )

        assertTrue("successCallback should be called", successCalled == 1)
        assertTrue("failureCallback should not be called", failureCalled == 0)

        //check the database
        assertEquals(2, dao.countUserInputs())

        //report over threshold
        reportingSdk.reportUserInputs(
            inputs = listOf(
                UserInput("key3", "val3", 1003),
                UserInput("key4", "val4", 1004),
            ),
            successCallback = successCallback,
            failureCallback = failureCallback,
        )

        assertTrue("successCallback should be called", successCalled == 2)
        assertTrue("failureCallback should not be called", failureCalled == 0)

        //check the database
        assertEquals(0, dao.countUserInputs())
    }
}