package com.example.housing_and_communal_services.bottom_navigation

import com.example.housing_and_communal_services.R


sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Home: BottomBarScreen(
        route = "home",
        title = "Главная",
        icon =  R.drawable.baseline_home_24
    )

    object DevicesMetering : BottomBarScreen(
        route = "devicesMetering",
        title = "Счетчики",
        icon =  R.drawable.baseline_water_drop_24
    )

    object Services: BottomBarScreen(
        route = "services",
        title = "Услуги",
        icon =  R.drawable.baseline_storefront_24
    )

    object RequestServices: BottomBarScreen(
        route = "requestServices",
        title = "Заявки",
        icon =  R.drawable.baseline_format_list_bulleted_24
    )

}