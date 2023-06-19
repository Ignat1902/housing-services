package com.example.housing_and_communal_services.screens.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.housing_and_communal_services.R
import com.example.housing_and_communal_services.bottom_navigation.BottomBarScreen
import com.example.housing_and_communal_services.navigation.Screen
import com.example.housing_and_communal_services.screens.bottom_bar_screen.HomeScreen
import com.example.housing_and_communal_services.screens.bottom_bar_screen.MeteringDevicesScreen
import com.example.housing_and_communal_services.screens.bottom_bar_screen.NewsScreen
import com.example.housing_and_communal_services.screens.bottom_bar_screen.RequestScreen
import com.example.housing_and_communal_services.screens.bottom_bar_screen.ServicesScreen
import com.example.housing_and_communal_services.screens.detail_screen.MeterReadingList
import com.example.housing_and_communal_services.showBars
import com.example.housing_and_communal_services.view_models.NewsViewModel
import com.example.housing_and_communal_services.view_models.Service
import com.example.housing_and_communal_services.view_models.ServicesViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    servicesViewModel: ServicesViewModel,
    newsViewModel: NewsViewModel
) {
    showBars(flag = true)
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val listItems = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.DevicesMetering,
        BottomBarScreen.Services,
        BottomBarScreen.RequestServices,
        BottomBarScreen.News
    )
    val title = remember {
        mutableStateOf(BottomBarScreen.Home.title)
    }

    val text = remember { mutableStateOf("") }
    val isEditing = remember { mutableStateOf(false) }
    val filteredServices= remember { mutableStateOf(emptyList<Service>()) }
    Scaffold(
        topBar = {
            val currentRoute = backStackEntry?.destination?.route
            val showProfileIcon =
                currentRoute != Screen.Profile.route && currentRoute != "detailScreen/{serialNumber}/{name}" && currentRoute != BottomBarScreen.Services.route
            CenterAlignedTopAppBar(
                title = {
                    if (isEditing.value && currentRoute == BottomBarScreen.Services.route) {
                        OutlinedTextField(
                            value = text.value,
                            onValueChange = { newText ->
                                text.value = newText
                                filteredServices.value = servicesViewModel.services.filter { it.title == newText }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent),
                            colors = OutlinedTextFieldDefaults.colors(
                                cursorColor = MaterialTheme.colorScheme.onBackground,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = { /* Perform search action */ }),
                        )
                    } else {
                        //isEditing.value = false
                        Text(
                            title.value,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    if (currentRoute == Screen.Profile.route || currentRoute == "detailScreen/{serialNumber}/{name}") {
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
                    if (currentRoute == BottomBarScreen.Services.route) {
                        IconButton(onClick = {
                            isEditing.value = !isEditing.value
                        }) {
                            Icon(Icons.Filled.Search, contentDescription = "Search")
                        }
                    }
                }
            )
        },
        bottomBar = {
            val showBottomBar = backStackEntry
                ?.destination
                ?.route != Screen.Profile.route
            val showBottomBarDetail = backStackEntry
                ?.destination
                ?.route != "detailScreen/{serialNumber}/{name}"
            if (showBottomBar && showBottomBarDetail) {
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
                                Text(text = item.title, style = MaterialTheme.typography.labelSmall)
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
                NavHost(navController = navController, startDestination = BottomBarScreen.Home.route) {

                    composable(BottomBarScreen.Home.route) {
                        title.value = BottomBarScreen.Home.title
                        HomeScreen()
                    }
                    composable(Screen.Profile.route) {
                        title.value = Screen.Profile.title
                        ProfileInfoScreen()
                    }
                    composable(Screen.SplashScreen.route) {
                        StartScreen(navController)
                    }

                    composable("detailScreen/{serialNumber}/{name}") { backStackEntry ->
                        // Получение serialNumber и name из аргументов маршрута
                        val serialNumber = backStackEntry.arguments?.getString("serialNumber")
                        val name = backStackEntry.arguments?.getString("name")
                        if (serialNumber != null && name != null) {
                            title.value = "История показаний"
                            MeterReadingList(serialNumber, name)
                        }
                    }

                    composable(BottomBarScreen.DevicesMetering.route) {
                        title.value = BottomBarScreen.DevicesMetering.title
                        MeteringDevicesScreen(navController)
                    }

                    composable(BottomBarScreen.Services.route) {
                        title.value = BottomBarScreen.Services.title
                        ServicesScreen(filteredServices.value,servicesViewModel)


                    }

                    composable(BottomBarScreen.RequestServices.route) {
                        title.value = BottomBarScreen.RequestServices.title
                        RequestScreen(servicesViewModel)
                    }

                    composable(BottomBarScreen.News.route) {
                        title.value = BottomBarScreen.News.title
                        NewsScreen(newsViewModel)
                    }
                }
            }
        })
}