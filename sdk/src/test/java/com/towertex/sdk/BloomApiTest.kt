package com.towertex.sdk

import com.towertex.sdk.network.BloomApiBuilder
import com.towertex.sdk.network.model.Success
import com.towertex.sdk.network.model.UIConfigurationForm
import com.towertex.sdk.network.model.UserInputPost
import com.towertex.sdk.network.service.ConfigurationApiContract
import com.towertex.sdk.network.service.ReportingApiContract
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class BloomApiTest {
    @Test
    fun dummyTest() {
        assertEquals(4, 2 + 2)
    }

    private lateinit var mockedConfigurationApi: ConfigurationApiContract
    private lateinit var mockedReportingApi: ReportingApiContract

    @Before
    fun init() {
        BloomApiBuilder {
            setLogAll()
            setDebugLogger()
            setEngineToMock()
        }
            .build()
            .also {
                mockedConfigurationApi = it
                mockedReportingApi = it
            }
    }

    @Test
    fun getUIConfigurationMockedTest(): Unit = runBlocking {
        val result = mockedConfigurationApi.getConfigurationForm(1)
        assertNotNull("result should not be null", result)
        assertTrue("result should be Success", result is Success)
        val response = (result as? Success<*>)?.data as? UIConfigurationForm
        assertNotNull("result should have data", response)
    }

    @Test
    fun postUserInputsTest(): Unit = runBlocking {
        val post = UserInputPost(listOf(
            UserInputPost._UserInput("key1", "value1", 1234567890),
            UserInputPost._UserInput("key2", "value2", 1234567890)
        ))
        val result = mockedReportingApi.postUserInputs(post)
        assertNotNull("result should not be null", result)
        assertTrue("result should be Success", result is Success)
    }
}