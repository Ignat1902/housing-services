package com.example.housing_and_communal_services.navigation

import com.example.housing_and_communal_services.R

/**
 * This class described screen navigation objects
 * @param screenName - deeplink screen representation
 * @param titleResourceId - resource to name tabbar or appbar navigation title
 */
sealed class Screen(val route: String) {
    object screenSplash: Screen("screenSplash")
    object Login: Screen("login")
    object Registration: Screen("registration")
}