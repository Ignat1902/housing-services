package com.example.housing_and_communal_services.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository {
    //val currentUser: FirebaseUser? = Firebase.auth.currentUser
    val db = Firebase.firestore
    fun hasUser():Boolean = Firebase.auth.currentUser != null
    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()


    // функция для регистрации пользователя
    suspend fun createUser(
        email: String,
        password: String,
        onComplete:(Boolean)->Unit
    ) = withContext(Dispatchers.IO){
        Firebase.auth
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    onComplete.invoke(true)
                }
                else{
                    onComplete.invoke(false)
                }
            }.await()
    }

    //функция для авторизации пользователя
    suspend fun login(
        email: String,
        password: String,
        onComplete:(Boolean)->Unit
    ) = withContext(Dispatchers.IO){
        Firebase.auth
            .signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    onComplete.invoke(true)
                }
                else{
                    onComplete.invoke(false)
                }
            }.await()
    }

    suspend fun fetchUserById(): User? {
        val userDocument = db.collection("User").document(getUserId()).get().await()
        return if (userDocument.exists()) {
            userDocument.toObject(User::class.java)
        } else {
            null
        }
    }

}