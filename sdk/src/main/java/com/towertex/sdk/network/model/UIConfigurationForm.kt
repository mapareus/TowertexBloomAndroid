package com.towertex.sdk.network.model

import kotlinx.serialization.Serializable

@Serializable
data class UIConfigurationForm(
    val textInputs: List<UIConfigurationText>,
    val submitButtonLabel: String?,
    val style: UIConfigurationStyle?,
)