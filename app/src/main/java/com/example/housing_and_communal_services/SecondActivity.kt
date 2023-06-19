package com.example.housing_and_communal_services

import com.example.housing_and_communal_services.view_models.ServicesViewModel
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.Housing_and_communal_servicesTheme
import com.example.housing_and_communal_services.data.repositories.ServicesRepository
import com.example.housing_and_communal_services.screens.screen.MainScreen
import com.example.housing_and_communal_services.view_models.NewsViewModel

class SecondActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val repository = ServicesRepository() // создайте репозиторий здесь
            val factory = ServicesViewModelFactory(repository)
            val servicesViewModel = ViewModelProvider(this, factory)[ServicesViewModel::class.java]
            val newsViewModel = viewModel(modelClass = NewsViewModel::class.java)
            newsViewModel.getAllNews()
            Housing_and_communal_servicesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(servicesViewModel, newsViewModel)
                }
            }
        }
    }
}

class ServicesViewModelFactory(private val repository: ServicesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServicesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ServicesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}