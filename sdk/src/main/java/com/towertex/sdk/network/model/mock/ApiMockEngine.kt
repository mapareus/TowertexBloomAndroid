package com.towertex.sdk.network.model.mock

import io.ktor.client.HttpClient
import io.ktor.http.ContentType
import io.ktor.http.headersOf
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpStatusCode

object ApiMockEngine {
    fun get() = client.engine

    private val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
    private val client = HttpClient(MockEngine) {
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    "/ui_configuration_form/1" -> respond(uiConfigurationFormMock1Json, HttpStatusCode.OK, responseHeaders)
                    "/ui_configuration_form/2" -> respond(uiConfigurationFormMock2Json, HttpStatusCode.OK, responseHeaders)
                    "/user_inputs" -> respond("", HttpStatusCode.OK, responseHeaders)
                    else -> error("Unhandled ${request.url.encodedPath}")
                }
            }
        }
    }
}