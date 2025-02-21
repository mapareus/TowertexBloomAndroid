package com.towertex.sdk.network.model

import kotlinx.serialization.Serializable

@Serializable
class UserInputPost(
    val userInputs: List<_UserInput>
) {
    @Suppress("ClassName")
    @Serializable
    class _UserInput(
        val key: String?,
        val value: String?,
        val epoch: Long?
    )
}