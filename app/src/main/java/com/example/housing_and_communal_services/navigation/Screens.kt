package com.example.housing_and_communal_services.navigation

/**
 * This class described screen navigation objects
 */
sealed class Screen(val route: String, val title: String) {
    object SplashScreen : Screen("screenSplash","")
    object Welcome : Screen("welcome","Добро пожаловать!")
    object Login : Screen("login","Вход")
    object ForgotPassword: Screen("forgot_pass","Сброс пароля")
    object Registration : Screen("registration","Регистрация")
    object RegistrationTel: Screen("registration_tel","Регистрация")
    object Profile:Screen("profile","Профиль")
    object AccountNumberEntryPage:Screen("account_number_entry_page", "")
}