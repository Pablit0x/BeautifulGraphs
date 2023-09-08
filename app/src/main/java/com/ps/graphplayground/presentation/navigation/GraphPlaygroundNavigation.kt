package com.ps.graphplayground.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ps.graphplayground.presentation.graph_screen.GraphScreen

@Composable
fun GraphPlaygroundNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.GraphScreen.route) {
        composable(route = Screen.GraphScreen.route) {
            GraphScreen()
        }
    }
}