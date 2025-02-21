package com.towertex.sdk.network.service

import com.towertex.sdk.network.model.BloomApiResult
import com.towertex.sdk.network.model.UserInputPost
import com.towertex.sdk.network.model.toResult
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ReportingApi(
    private val httpClient: HttpClient,
    private val baseUrl: String,
): ReportingApiContract {

    override suspend fun postUserInputs(
        post: UserInputPost
    ): BloomApiResult<Unit> = httpClient.toResult<Unit> {
        post {
            url(StringBuilder()
                .append(baseUrl)
                .append("user_inputs")
                .toString()
            )
            contentType(ContentType.Application.Json)
            setBody(post)
        }
    }
}