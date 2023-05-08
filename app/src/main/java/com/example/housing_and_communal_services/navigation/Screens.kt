package com.example.housing_and_communal_services.navigation

import android.icu.text.CaseMap.Title
import com.example.housing_and_communal_services.R

/**
 * This class described screen navigation objects
 * @param screenName - deeplink screen representation
 * @param titleResourceId - resource to name tabbar or appbar navigation title
 */
sealed class Screen(val route: String,val iconId: Int, val title: String) {
    object screenSplash : Screen("screenSplash",-1,"")
    object Login : Screen("login",-1,"")
    object Registration : Screen("registration",-1,"")
    object Home : Screen("home",R.drawable.baseline_home_24,"Главная")
    object DevicesMetering : Screen("devicesMetering",R.drawable.baseline_water_drop_24,"Счетчики")
    object Services : Screen("services",R.drawable.baseline_storefront_24,"Услуги")
    object RequestServices : Screen("requestServices",R.drawable.baseline_format_list_bulleted_24,"Заявки")
}