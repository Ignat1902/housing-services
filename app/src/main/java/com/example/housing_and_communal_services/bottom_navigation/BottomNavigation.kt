package com.example.housing_and_communal_services.bottom_navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.housing_and_communal_services.navigation.Screen
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

@Composable
fun BottomNav(
    navController: NavController
) {

    val listItems = listOf(
        Screen.Home,
        Screen.DevicesMetering,
        Screen.Services,
        Screen.RequestServices
    )

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        listItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = item.title, style = MaterialTheme.typography.labelMedium)
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
            )
        }
    }

}