package com.towertex.bloom.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.towertex.bloom.compose.HomeScreen
import com.towertex.sdkcompose.BloomSdkComposable

@Composable
fun BloomNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onClick = { navController.navigate(Screen.UserInputs.route) }
            )
        }
        composable(route = Screen.UserInputs.route) {
            BloomSdkComposable.UserInputsScreen()
        }
    }
}