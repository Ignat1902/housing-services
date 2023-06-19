package com.example.housing_and_communal_services.data.repositories

import android.util.Log
import com.example.housing_and_communal_services.data.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository {
    val db = Firebase.firestore
    fun hasUser(): Boolean = Firebase.auth.currentUser != null
    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()


    // функция для регистрации пользователя
    suspend fun createUser(
        email: String,
        password: String,
        user: User,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    //onComplete.invoke(true)
                    val reference = db.collection("User").document(getUserId())
                    reference.set(user)
                        .addOnSuccessListener {
                            onComplete.invoke(true)
                        }
                        .addOnFailureListener {
                            onComplete.invoke(false)
                        }
                } else {
                    onComplete.invoke(false)
                }
            }.await()
    }

    //Функция поиска адреса по личному номеру
    suspend fun getAddressByAccountNumber(
        accountNumber: String,
    ): String? = withContext(Dispatchers.IO) {
        val query = db.collection("Personal account").whereEqualTo("account_number", accountNumber.toInt())
        try {
            val querySnapshot = query.get().await()
            if (!querySnapshot.isEmpty) {
                val doc = querySnapshot.documents[0]
                doc.getString("address")
            } else {
                null
            }
        } catch (e: Exception) {
            Log.d("GetAddress", "Error: ", e)
            null
        }
    }

    //функция для авторизации пользователя
    suspend fun login(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }.await()
    }

    //Функция для сброса пароля
    suspend fun sendPasswordResetEmail(email: String): Result<Void?> {
        return try {
            val task = Firebase.auth.sendPasswordResetEmail(email).await()
            Result.success(task)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}