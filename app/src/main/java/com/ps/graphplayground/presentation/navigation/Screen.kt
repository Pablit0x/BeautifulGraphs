package com.ps.graphplayground.presentation.navigation

sealed class Screen(val route: String) {
    object GraphScreen : Screen(route = Routes.GRAPH_SCREEN_ROUTE)
}