package com.example.housing_and_communal_services

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.compose.Housing_and_communal_servicesTheme
import com.example.housing_and_communal_services.navigation.SetupNavGraph
import com.example.housing_and_communal_services.screens.authorization.LoginViewModel


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
            Housing_and_communal_servicesTheme {
                /*// A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                SetupNavGraph(navHostController = navController, loginViewModel = loginViewModel)*/

                Surface(color = MaterialTheme.colorScheme.background) {

                    val navController = rememberNavController()

                    SetupNavGraph(navHostController = navController, loginViewModel = loginViewModel)
                }

            }
        }
    }
}