package com.madarsoft.usermanagementapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.madarsoft.usermanagementapp.presentation.screens.DisplayUsersScreen
import com.madarsoft.usermanagementapp.presentation.screens.InputScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "input"
    ) {
        composable("input") {
            InputScreen(
                onNavigateToDisplay = {
                    navController.navigate("display")
                }
            )
        }

        composable("display") {
            DisplayUsersScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}