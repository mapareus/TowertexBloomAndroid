package com.towertex.sdk.network

import com.towertex.sdk.network.model.mock.ApiMockEngine
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class BloomApiBuilder(buildBlock: BloomApiBuilder.() -> Unit) {
    private var baseUrl: String = BASE_URL
    private var logLevel: LogLevel = LogLevel.HEADERS
    private var logLogger: Logger? = null
    private var mockedEngine: Boolean = false

    init {
        buildBlock.invoke(this)
    }

    companion object {
        private const val BASE_URL = "/"
    }

    fun setBaseUrl(baseUrl: String) = this.apply { this.baseUrl = baseUrl }
    fun setLogAll() = this.apply { logLevel = LogLevel.ALL }
    fun setLogBody() = this.apply { logLevel = LogLevel.BODY }
    fun setLogHeaders() = this.apply { logLevel = LogLevel.HEADERS }
    fun setLogInfo() = this.apply { logLevel = LogLevel.INFO }
    fun setLogNone() = this.apply { logLevel = LogLevel.NONE }
    fun setDebugLogger() = this.apply { logLogger = object : Logger {
        override fun log(message: String) {
            println(message)
        }
    } }
    fun setReleaseLogger() = this.apply { logLogger = null }
    fun setEngineToAndroid() = this.apply { mockedEngine = false }
    fun setEngineToMock() = this.apply { mockedEngine = true }

    fun build(): BloomApi = BloomApi(client, baseUrl)

    private val client: HttpClient get() =
        if (mockedEngine) HttpClient(ApiMockEngine.get(), httpConfigurationBlock)
        else HttpClient(Android, httpConfigurationBlock)

    private val httpConfigurationBlock: HttpClientConfig<*>.() -> Unit get() = {
        install(Logging) {
            level = logLevel
            logLogger?.also { logger = it }
        }
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
            )
        }
    }
}