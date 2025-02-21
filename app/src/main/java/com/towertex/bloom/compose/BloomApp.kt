package com.towertex.bloom.compose

import androidx.compose.runtime.Composable
import com.towertex.bloom.navigation.BloomNavHost
import com.towertex.bloom.theme.TowertexBloomTheme

@Composable
fun BloomApp() {
    TowertexBloomTheme {
        BloomNavHost()
    }
}