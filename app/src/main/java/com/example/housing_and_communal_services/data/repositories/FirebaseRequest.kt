package com.example.housing_and_communal_services.data.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseRequest {
    val db = Firebase.firestore

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