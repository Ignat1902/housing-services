package com.example.housing_and_communal_services.view_models

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.housing_and_communal_services.data.repositories.ServicesRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date

class ServicesViewModel(private val repository: ServicesRepository) : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    //Изменяемый лист, который хранит в себе все существующие услуги в БД
    val services = mutableStateListOf<Service>()
    //Изменяемый лист, который хранит в себе все существующие заявки данного пользователя в БД
    val getRequest = mutableStateListOf<getRequest>()

    fun getAllServices() {
        viewModelScope.launch {
            try {
                val querySnapshot = withContext(Dispatchers.IO) {
                    firestore.collection("Services").get().await()
                }
                val servicesList = mutableListOf<Service>()
                for (document in querySnapshot.documents) {
                    val description = document.getString("description") ?: ""
                    val title = document.getString("title") ?: ""
                    val price = document.getString("price") ?: ""
                    val service = Service(description, title, price)
                    servicesList.add(service)
                }
                services.clear()
                services.addAll(servicesList)
            } catch (exception: Exception) {
                // Обработка ошибки
            }
        }
    }

    //Функция для получения списка заявок
    fun getRequests() {
        viewModelScope.launch {
            val requests = repository.getRequestsByAddress()
            getRequest.clear()
            getRequest.addAll(requests)
        }
    }

    private val _checkRequest = MutableLiveData<Boolean>()
    val checkRequest: LiveData<Boolean> get() = _checkRequest


    fun checkRequest(title: String) = viewModelScope.launch {
        val checkRequests = repository.checkRequest(title)
        _checkRequest.value = checkRequests
        //Log.d("IGNAT","checkRequest = ${checkRequest.value}")
    }

    fun createRequest(title: String,context: Context) = viewModelScope.launch {
        repository.createRequest(title, context)
    }

}

data class Service(
    val description: String,
    val title: String,
    val price: String
)
data class Request(
    val date: Date,
    val title: String,
    val address: String,
    val status: String
)
data class getRequest(
    val date: Date,
    val title: String,
    val status: String
)
