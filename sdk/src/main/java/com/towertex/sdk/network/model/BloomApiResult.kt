package com.towertex.sdk.network.model

import com.towertex.sdk.network.model.BloomApiErrorType.NO_INTERNET
import com.towertex.sdk.network.model.BloomApiErrorType.SERIALIZATION
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> HttpClient.toResult(
    getResponseBlock: HttpClient.() -> HttpResponse
): BloomApiResult<T> =
    try {
        getResponseBlock().run {
            status.value.let {
                when (it) {
                    in 200..299 -> Success(body<T>())
                    else -> Error(BloomApiErrorType.fromHttpCode(it), it, body())
                }
            }
        }
    } catch(e: UnresolvedAddressException) {
        Exception(NO_INTERNET)
    } catch(e: SerializationException) {
        Exception(SERIALIZATION)
    }

sealed interface BloomApiResult<out D>
data class Success<out D>(val data: D): BloomApiResult<D>
data class Error<out D>(val apiErrorType: BloomApiErrorType, val httpCode: Int, val error: BloomApiErrorResponse):
    BloomApiResult<D>
data class Exception<out D>(val apiErrorType: BloomApiErrorType): BloomApiResult<D>