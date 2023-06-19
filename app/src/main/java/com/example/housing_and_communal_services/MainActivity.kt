package com.example.housing_and_communal_services

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.compose.Housing_and_communal_servicesTheme
import com.example.housing_and_communal_services.navigation.SetupNavGraph
import com.example.housing_and_communal_services.view_models.LoginViewModel


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
            Housing_and_communal_servicesTheme {
                val context = LocalContext.current
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connectivityManager.activeNetworkInfo
                val isConnected = networkInfo != null && networkInfo.isConnected
                if (!isConnected) {
                    AlertDialog(
                        onDismissRequest = {},
                        title = { Text(text = "Нет подключения к интернету") },
                        text = { Text("Пожалуйста, включите интернет на вашем устройстве.") },
                        confirmButton = {
                            Button(onClick = {}) {
                                Text("ОК")
                            }
                        }
                    )
                }else{
                    Surface(color = MaterialTheme.colorScheme.background) {

                        val navController = rememberNavController()

                        SetupNavGraph(navHostController = navController, loginViewModel = loginViewModel)
                    }
                }


            }
        }
    }
}