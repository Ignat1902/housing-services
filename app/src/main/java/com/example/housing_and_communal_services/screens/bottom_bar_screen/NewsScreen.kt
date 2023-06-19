package com.example.housing_and_communal_services.screens.bottom_bar_screen

import NewsCard
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.housing_and_communal_services.view_models.NewsViewModel

@Composable
fun NewsScreen(newsViewModel: NewsViewModel) {
    LazyColumn {
        items(newsViewModel.news) { news ->
            NewsCard(title = news.title, description = news.description , date = news.date)
        }
    }
}
