package com.towertex.bloom.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.towertex.bloom.R
import com.towertex.bloom.theme.TowertexBloomTheme

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.hello_message, name),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TowertexBloomTheme {
        Greeting("Android")
    }
}