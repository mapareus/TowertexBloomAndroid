package com.towertex.bloom.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.towertex.bloom.R

@Composable
fun HomeScreen(
    onClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = { FloatingButton(onClick) }
    ) { innerPadding ->
        Greeting(
            name = stringResource(R.string.app_name),
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun FloatingButton(onClick: () -> Unit) {
    LargeFloatingActionButton(
        onClick = { onClick() },
        shape = CircleShape,
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}