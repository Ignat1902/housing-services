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
import com.example.housing_and_communal_services.SecondActivity
import com.example.housing_and_communal_services.screens.authorization.registration.AccountNumberEntryPage
import com.example.housing_and_communal_services.screens.authorization.login.ForgotPasswordScreen
import com.example.housing_and_communal_services.screens.authorization.login.LoginPage
import com.example.housing_and_communal_services.view_models.LoginViewModel
import com.example.housing_and_communal_services.screens.authorization.registration.RegistrationPage
import com.example.housing_and_communal_services.screens.authorization.RegistrationTelephonePage
import com.example.housing_and_communal_services.screens.authorization.WelcomeScreen
import com.example.housing_and_communal_services.screens.screen.StartScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel
) {
    val context = LocalContext.current
    val intent = remember { Intent(context, SecondActivity::class.java) }
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    NavHost(navController = navHostController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            StartScreen(navController = navHostController)
        }

        composable(Screen.Welcome.route) {
            WelcomeScreen(navController = navHostController, loginViewModel, onNavToHomePage = {
                context.startActivity(intent)
            })
        }

        composable(Screen.RegistrationTel.route) {
            RegistrationTelephonePage(
                onHomePage = {
                    context.startActivity(intent)
                },
                loginViewModel = loginViewModel,
                onRegistrationPage = {
                    navHostController.navigate(Screen.AccountNumberEntryPage.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.AccountNumberEntryPage.route) {
            AccountNumberEntryPage(
                loginViewModel = loginViewModel,
                onNextPage = {
                    navHostController.navigate(Screen.Registration.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screen.Registration.route) {
            RegistrationPage(
                onNavToHomePage = {
                    context.startActivity(intent)
                },
                loginViewModel = loginViewModel
            ) {
                navHostController.navigate(Screen.Login.route) {
                    launchSingleTop = true
                }
            }
        }

        //переход на страницу логина
        composable(Screen.Login.route) {
            LoginPage(
                onNavToHomePage = {
                    context.startActivity(intent)
                },
                loginViewModel = loginViewModel,
                onNavToSignUpTelephone = {
                    navHostController.navigate(Screen.RegistrationTel.route) {
                        launchSingleTop = true
                    }
                },
                onForgotPass = {
                    navHostController.navigate(Screen.ForgotPassword.route) {
                        launchSingleTop = true
                    }
                },
            ) {
                navHostController.navigate(Screen.Registration.route) {
                    launchSingleTop = true
                    popUpTo(Screen.Login.route) {
                        inclusive = true
                    }
                }
            }
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                viewModel = loginViewModel,
                onNavToHomePage = {
                    context.startActivity(intent)

                },
            )
        }
    }
}


