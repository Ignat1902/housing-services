package com.example.housing_and_communal_services.view_models

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

data class News(
    val description: String,
    val title: String,
    val date: String
)

class NewsViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    val news = mutableStateListOf<News>()

    fun getAllNews() {
        viewModelScope.launch {
            try {
                val querySnapshot = withContext(Dispatchers.IO) {
                    firestore.collection("News")
                        .orderBy("date", Query.Direction.DESCENDING)
                        .get()
                        .await()
                }
                val newsList = querySnapshot.documents.map { document ->
                    val description = document.getString("description") ?: ""
                    val title = document.getString("title") ?: ""
                    val date = document.getDate("date").toString()
                    News(description, title, date)
                }
                news.apply {
                    clear()
                    addAll(newsList)
                }
            } catch (exception: Exception) {
                // Обработка ошибки
            }
        }
    }
}
