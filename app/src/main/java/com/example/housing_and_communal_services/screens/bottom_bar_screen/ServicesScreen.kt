package com.example.housing_and_communal_services.screens.bottom_bar_screen

import ServiceCard
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.housing_and_communal_services.view_models.Service
import com.example.housing_and_communal_services.view_models.ServicesViewModel

@Composable
fun ServicesScreen(filterServices: List<Service>,servicesViewModel: ServicesViewModel) {
    servicesViewModel.getAllServices()
    if (!filterServices.isEmpty()){
        LazyColumn {
            items(filterServices) { service ->
                ServiceCard(
                    title = service.title,
                    description = service.description,
                    coast = service.price,
                    servicesViewModel
                )
            }
        }
    }else{
        LazyColumn {
            items(servicesViewModel.services) { service ->
                ServiceCard(
                    title = service.title,
                    description = service.description,
                    coast = service.price,
                    servicesViewModel
                )
            }
        }
    }
}
