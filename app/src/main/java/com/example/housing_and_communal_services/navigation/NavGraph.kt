package com.example.housing_and_communal_services.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.housing_and_communal_services.screens.StartScreen
import com.example.housing_and_communal_services.screens.authorization.LoginPage
import com.example.housing_and_communal_services.screens.authorization.RegistrationPage

@Composable
fun SetupNavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Screen.screenSplash.route) {
        composable(Screen.screenSplash.route) {
            StartScreen(navHostController)
        }
        composable(Screen.Login.route) {
            LoginPage(navController = navHostController)
        }
        composable(Screen.Registration.route) {
            RegistrationPage(navController = navHostController)
        }
    }
}