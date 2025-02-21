package com.towertex.bloom.navigation

sealed class Screen(
    val route: String,
) {
    companion object {
        private const val ROUTE_HOME = "home"
        private const val ROUTE_USER_INPUTS = "userInputs"
    }

    data object Home: Screen(ROUTE_HOME)

    data object UserInputs: Screen(ROUTE_USER_INPUTS)
}