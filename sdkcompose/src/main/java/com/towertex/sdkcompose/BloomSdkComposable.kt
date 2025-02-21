package com.towertex.sdkcompose

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.towertex.sdk.network.model.UIConfigurationForm
import com.towertex.sdk.network.model.UIConfigurationStyle
import com.towertex.sdk.room.model.UserInput
import com.towertex.sdk.sdk.BloomSdk
import com.towertex.sdk.sdk.BloomSdkBuilder
import com.towertex.sdk.sdk.model.BloomSDKConfiguration
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

object BloomSdkComposable {
    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun UserInputsScreen(
        modifier: Modifier = Modifier,
        configuration: BloomSDKConfiguration = BloomSdkBuilder.defaultConfiguration
    ) {
        val aContext = LocalContext.current

        var sdk by remember { mutableStateOf<BloomSdk?>(null) }
        var uiConfiguration by remember { mutableStateOf<UIConfigurationForm?>(null) }
        var userInputs by remember { mutableStateOf<Map<Int,String>>(emptyMap()) }
        val scope = rememberCoroutineScope()
        var stateOfSuccessMessage by remember { mutableStateOf<Boolean?>(null) }

        fun Map<Int,String>.toUserInputs(): List<UserInput> = map { UserInput(
            uiConfiguration?.textInputs?.get(it.key)?.title ?: "No title",
            it.value,
            System.currentTimeMillis()
        ) }

        fun getModifier(style: UIConfigurationStyle?): Modifier =
            style?.backgroundColorHex?.let { Modifier.background(color = Color(android.graphics.Color.parseColor(it))) } ?: Modifier
        fun getColor(style: UIConfigurationStyle?): Color? =
            style?.fontColorHex?.let { Color(android.graphics.Color.parseColor(it)) }
        fun getFontSize(style: UIConfigurationStyle?): Int? =
            style?.fontSizeSP

        LaunchedEffect(Unit) {
            if (sdk != null) return@LaunchedEffect
            sdk = BloomSdkBuilder(
                context = aContext,
                configuration = configuration
            ).build()
            uiConfiguration = sdk?.getUIConfigurationForm(this, 1)?.first()
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                uiConfiguration?.textInputs?.forEachIndexed { index, it ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = getModifier(it.style ?: uiConfiguration?.style)
                    ) {
                        Text(text = it.title ?: "No title",
                            color = getColor(it.style ?: uiConfiguration?.style) ?: MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = getFontSize(it.style ?: uiConfiguration?.style)?.sp ?: MaterialTheme.typography.bodyMedium.fontSize
                        )
                        TextField(
                            value = userInputs.getOrDefault(index, null) ?: it.placeholder ?: "",
                            onValueChange = { userInputs = userInputs + (index to it) },
                        )
                        (it.style?.backgroundImageUrl ?: uiConfiguration?.style?.backgroundImageUrl)?.also { url ->
                            GlideImage(
                                model = url,
                                contentDescription = it.title ?: "No title",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(40.dp)
                            )
                        }

                    }
                }
                Button(
                    onClick = {
                        scope.launch { sdk?.reportUserInputs(
                            userInputs.toUserInputs(),
                            { stateOfSuccessMessage = true },
                            { stateOfSuccessMessage = false }
                        ) }
                    },
                ) {
                    Text(text = uiConfiguration?.submitButtonLabel ?: "Submit")
                }
                when (stateOfSuccessMessage) {
                    true -> Text(text = "Last submission was a Success")
                    false -> Text(text = "Last submission was a Failure")
                    else -> Unit
                }
            }
        }
    }
}