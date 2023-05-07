package com.example.housing_and_communal_services

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.compose.Housing_and_communal_servicesTheme
import com.example.housing_and_communal_services.navigation.SetupNavGraph


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Housing_and_communal_servicesTheme {
                // A surface container using the 'background' color from the theme

                val navController = rememberNavController()
                SetupNavGraph(navHostController = navController)

            }
        }
    }
}

