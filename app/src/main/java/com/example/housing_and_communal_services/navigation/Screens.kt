package com.example.housing_and_communal_services.navigation

/**
 * This class described screen navigation objects
 */
sealed class Screen(val route: String, val title: String) {
    object screenSplash : Screen("screenSplash","")
    object Login : Screen("login","Авторизация")
    object Registration : Screen("registration","Регистрация")
    object RegistrationTel: Screen("registration_tel","Регистрация")
    object Profile:Screen("profile","Профиль")
    object AccountNumberEntryPage:Screen("account_number_entry_page", "")
    object AddressConfirmationPage:Screen("address_confirmation_page", "")
    object PersonalDataEntryPage:Screen("personal_data_entry_page", "")
}