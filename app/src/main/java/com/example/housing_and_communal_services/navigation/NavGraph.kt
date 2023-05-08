package com.example.housing_and_communal_services.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.housing_and_communal_services.screens.HomeScreen
import com.example.housing_and_communal_services.screens.MeteringDevicesScreen
import com.example.housing_and_communal_services.screens.RequestsForServicesScreen
import com.example.housing_and_communal_services.screens.ServicesScreen
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
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.DevicesMetering.route) {
            MeteringDevicesScreen()
        }
        composable(Screen.Services.route) {
            ServicesScreen()
        }
        composable(Screen.RequestServices.route) {
            RequestsForServicesScreen()
        }
    }
}