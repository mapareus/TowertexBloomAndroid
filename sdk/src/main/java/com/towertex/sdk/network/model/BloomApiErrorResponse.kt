package com.towertex.sdk.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BloomApiErrorResponse(
    val message: String?
)