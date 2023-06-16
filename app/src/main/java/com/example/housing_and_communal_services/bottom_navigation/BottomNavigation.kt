package com.example.housing_and_communal_services.bottom_navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNav(
    navController: NavController
) {

    val listItems = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.DevicesMetering,
        BottomBarScreen.Services,
        BottomBarScreen.RequestServices,
    )

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        listItems.forEachIndexed { _, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = item.title, style = MaterialTheme.typography.labelMedium)
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Удаляем предыдущую страницу маршрута из стека и не сохраняем ее состояние
                        popUpTo(navController.graph.startDestinationId)
                        //popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },

            )
        }
    }
}