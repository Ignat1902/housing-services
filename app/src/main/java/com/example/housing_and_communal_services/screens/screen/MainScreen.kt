package com.example.housing_and_communal_services.screens.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.housing_and_communal_services.R
import com.example.housing_and_communal_services.bottom_navigation.BottomBarScreen
import com.example.housing_and_communal_services.navigation.Screen
import com.example.housing_and_communal_services.showBars


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    showBars(flag = true)
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val listItems = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.DevicesMetering,
        BottomBarScreen.Services,
        BottomBarScreen.RequestServices,
    )
    val title = remember {
        mutableStateOf(BottomBarScreen.Home.title)
    }

    Scaffold(
        topBar = {
            val currentRoute = backStackEntry?.destination?.route
            val showProfileIcon = currentRoute != Screen.Profile.route
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        title.value,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    if (currentRoute == Screen.Profile.route) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                                contentDescription = "Назад"
                            )
                        }
                    }
                },
                actions = {
                    if (showProfileIcon) {
                        IconButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Профиль"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            val showBottomBar = backStackEntry
                ?.destination
                ?.route != Screen.Profile.route
            if (showBottomBar){
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    val currentRoute = backStackEntry?.destination?.route
                    listItems.forEach { item ->
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
                                    launchSingleTop = true
                                }
                            },

                            )
                    }
                }
            }

        },
        content = { padding ->
            BoxWithConstraints(
                Modifier.padding(padding),
            ) {
                NavHost(navController = navController, startDestination = "home_page") {
                    navigation(
                        startDestination = BottomBarScreen.Home.route,
                        route = "home_page"
                    ) {
                        composable(BottomBarScreen.Home.route) {
                            title.value = BottomBarScreen.Home.title
                            HomeScreen()
                        }
                        composable(Screen.Profile.route) {
                            title.value = Screen.Profile.title
                            ProfileInfoScreen()
                        }
                        composable(Screen.screenSplash.route){
                            StartScreen(navController)
                        }
                    }

                    composable(BottomBarScreen.DevicesMetering.route) {
                        title.value = BottomBarScreen.DevicesMetering.title
                        MeteringDevicesScreen()
                    }

                    composable(BottomBarScreen.Services.route) {
                        title.value = BottomBarScreen.Services.title
                        ServicesScreen()
                    }

                    composable(BottomBarScreen.RequestServices.route) {
                        title.value = BottomBarScreen.RequestServices.title
                        RequestsForServicesScreen()
                    }
                }
            }
        })
}