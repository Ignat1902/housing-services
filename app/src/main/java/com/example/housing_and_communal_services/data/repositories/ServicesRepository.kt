package com.example.housing_and_communal_services.data.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.housing_and_communal_services.view_models.Request
import com.example.housing_and_communal_services.view_models.getRequest
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Date

class ServicesRepository {
    private val firestore = Firebase.firestore
    val meteringDeviceRepository = MeteringDeviceRepository()

    //Функция для проверки была ли оставлена заявка
    suspend fun checkRequest(title: String): Boolean {
        return try {
            val user = meteringDeviceRepository.fetchUserById()
            val snapshot = firestore.collection("Request")
                .whereEqualTo("title", title)
                .whereEqualTo("address", user?.address)
                .whereEqualTo("status", "в обработке")
                .get()
                .await()
            !snapshot.isEmpty
        } catch (e: Exception) {
            Log.d("FFF","Можно ещё разок заявочку оставить дружок")
            false
        }
    }

    //Функция для создания заявки
    suspend fun createRequest(title: String,context: Context) {
        val user = meteringDeviceRepository.fetchUserById()
        val request = Request(
            date = Date(),
            address = user?.address?: "",
            title = title,
            status = "в обработке"
        )
        try {
            firestore.collection("Request")
                .add(request)
                .await()
            Toast.makeText(context, "Заявка успешно оставлена!", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Ошибка $e", Toast.LENGTH_LONG).show()
        }
    }

    // Функция получения списка заявок
    suspend fun getRequestsByAddress(): List<getRequest> {
        val user = meteringDeviceRepository.fetchUserById()
        val requests = mutableListOf<getRequest>()
        val querySnapshot = firestore.collection("Request")
            .whereEqualTo("address", user?.address)
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .await()

        for (document in querySnapshot.documents) {
            val title = document.getString("title") ?: ""
            val date = document.getDate("date") ?: Date()
            val status = document.getString("status") ?: ""
            val request = getRequest( date, title, status)
            requests.add(request)
        }

        return requests
    }
}