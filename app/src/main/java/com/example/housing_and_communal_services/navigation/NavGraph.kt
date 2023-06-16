package com.example.housing_and_communal_services.navigation

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.housing_and_communal_services.AuthActivity
import com.example.housing_and_communal_services.screens.authorization.AccountNumberEntryPage
import com.example.housing_and_communal_services.screens.authorization.LoginPage
import com.example.housing_and_communal_services.screens.authorization.LoginViewModel
import com.example.housing_and_communal_services.screens.authorization.PersonalDataEntryScreen
import com.example.housing_and_communal_services.screens.authorization.RegistrationPage
import com.example.housing_and_communal_services.screens.authorization.RegistrationTelephonePage
import com.example.housing_and_communal_services.screens.screen.StartScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel
) {
    val context = LocalContext.current
    val intent = remember { Intent(context, AuthActivity::class.java) }
    NavHost(navController = navHostController, startDestination = Screen.screenSplash.route) {
        composable(Screen.screenSplash.route) {
            StartScreen(navController = navHostController)
        }
        navigation(
            startDestination = Screen.RegistrationTel.route,
            route = "auth"
        ) {
            composable(Screen.RegistrationTel.route) {
                RegistrationTelephonePage(
                    onHomePage = {
                        context.startActivity(intent)
                    /*navHostController.navigate(Screen.MainScreen.route) {
                        launchSingleTop = true
                        popUpTo(Screen.RegistrationTel.route) {
                            inclusive = true
                        }
                    }*/
                },
                    loginViewModel = loginViewModel,
                    onRegistrationPage = {
                        navHostController.navigate(Screen.AccountNumberEntryPage.route) {
                            launchSingleTop = true
                            popUpTo(Screen.RegistrationTel.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Screen.AccountNumberEntryPage.route) {
                AccountNumberEntryPage(
                    onNextPage = {
                        navHostController.navigate(Screen.PersonalDataEntryPage.route) {
                            /*launchSingleTop = true
                            popUpTo(Screen.RegistrationTel.route){
                                inclusive = true
                            }*/
                        }
                    }
                )
            }

            composable(Screen.AddressConfirmationPage.route) {

            }

            composable(Screen.PersonalDataEntryPage.route) {
                PersonalDataEntryScreen(onNextPage = {
                    navHostController.navigate("home") {
                        launchSingleTop = true
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                })
            }

            composable(Screen.Registration.route) {
                RegistrationPage(
                    onNavToHomePage = {
                        navHostController.navigate("home") {
                            popUpTo(Screen.Registration.route) {
                                inclusive = true
                            }
                        }
                    },
                    loginViewModel = loginViewModel
                ) {
                    navHostController.navigate(Screen.Login.route)
                }
            }

            //переход на страницу логина
            composable(Screen.Login.route) {
                LoginPage(
                    onNavToHomePage = {
                        navHostController.navigate("home") {
                            launchSingleTop = true
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    },
                    loginViewModel = loginViewModel
                ) {
                    navHostController.navigate(Screen.Registration.route) {
                        launchSingleTop = true
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }

       /* navigation(
            startDestination = Screen.MainScreen.route,
            route = "home"
        ) {
            composable(Screen.MainScreen.route) {
                MainScreen()
            }
            composable(Screen.Profile.route) {

            }
        }*/

    }
}

