package com.towertex.sdk.network.model

import kotlinx.serialization.Serializable

@Serializable
data class UIConfigurationText(
    val title: String? = null,
    val placeholder: String? = null,
    val style: UIConfigurationStyle? = null,
)