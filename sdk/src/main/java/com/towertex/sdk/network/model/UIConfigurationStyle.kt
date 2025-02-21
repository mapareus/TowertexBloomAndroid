package com.towertex.sdk.network.model

import kotlinx.serialization.Serializable

@Serializable
class UIConfigurationStyle(
    val fontSizeSP: Int? = null,
    val fontColorHex: String? = null,
    val backgroundColorHex: String? = null,
    val backgroundImageUrl: String? = null,
)