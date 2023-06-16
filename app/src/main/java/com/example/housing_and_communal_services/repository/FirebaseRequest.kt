package com.example.housing_and_communal_services.repository

import android.content.Context
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseRequest {
    val db = Firebase.firestore
    val repository = AuthRepository()

    fun getAccountAddress(accountNumber: Int): Task<QuerySnapshot> {
        val collectionRef = db.collection("Personal account")
        return collectionRef.whereEqualTo("account_number", accountNumber)
            .get()
    }

    fun createUserDocument(user: User, context:Context) {
        val reference = db.collection("User").document(repository.getUserId())
        reference.set(user)
            .addOnSuccessListener {
                Toast.makeText(context, "Вы были успешно зарегистрированы!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Ошибка регистрации: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    fun isDocumentExists(collectionPath: String, documentId: String, onResultReceived: (Boolean) -> Unit) {
        val documentRef = db.collection(collectionPath).document(documentId)

        documentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                onResultReceived(documentSnapshot != null && documentSnapshot.exists())
            }
            .addOnFailureListener {
                onResultReceived(false)
            }
    }
}