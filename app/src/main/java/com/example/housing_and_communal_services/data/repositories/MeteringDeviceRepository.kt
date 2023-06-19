package com.example.housing_and_communal_services.data.repositories

import android.util.Log
import com.example.housing_and_communal_services.data.models.MeterReading
import com.example.housing_and_communal_services.data.models.MeteringDevice
import com.example.housing_and_communal_services.data.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MeteringDeviceRepository {
    private val firestore = Firebase.firestore
    private val collectionMeterDevice = firestore.collection("Metering device")
    private val collectionMeterReading = firestore.collection("Meter readings")
    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    suspend fun fetchUserById(): User? {
        val userDocument = firestore.collection("User").document(getUserId()).get().await()
        return if (userDocument.exists()) {
            userDocument.toObject(User::class.java)
        } else {
            null
        }
    }


    //Фунция для получения всех существующих счетчиков
    suspend fun fetchMeteringDevices(address: String): List<MeteringDevice> {
        val documents = collectionMeterDevice
            .whereEqualTo("address", address)
            .get()
            .await()

        return documents.map { document ->
            MeteringDevice(
                name = document.getString("name") ?: "",
                serial_number = document.getString("serial_number") ?: "",
                verification_date = document.getString("verification_date") ?: "",
                address = document.getString("address") ?: ""
            )
        }

    }


    //Функция для получения последнего показания по счетчику
    suspend fun getLastMeterReading(serialNumber: String): MeterReading? {
        return try {
            val querySnapshot = collectionMeterReading
                .whereEqualTo("serial_number", serialNumber)
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()

            if (querySnapshot.documents.isNotEmpty()) {
                //val meterReading = querySnapshot.documents[0].toObject(MeterReading::class.java)
                val document = querySnapshot.documents[0]
                val meterReading =
                    document.getDate("date")?.let {
                        MeterReading(
                            value = document.getDouble("value") ?: 0.0,
                            date = it,
                            serial_number = document.getString("serialNumber") ?: ""
                        )
                    }
                Log.d("MeterRepository", "getLastMeterReading: $meterReading")
                meterReading
            } else {
                Log.d("MeterRepository", "getLastMeterReading: No documents found")
                null
            }
        } catch (e: Exception) {
            Log.e("MeterRepository", "getLastMeterReading: Error", e)
            null
        }
    }

    //Функция для отправки показания
    suspend fun addMeterReading(meterReading: MeterReading) {
        /* val currentDate = Date()
         val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
         val formattedDate = dateFormat.format(currentDate)*/
        collectionMeterReading
            .add(meterReading).addOnSuccessListener {
                Log.d("Meter Reading", "Показания успешно переданы")
            }
            .addOnFailureListener {
                Log.d("Meter Reading", "Произошла ошибка")
            }
            .await()
    }



    //Функция получения последнего показания по серийному номеру
    suspend fun getLastDateMeterReadingBySerialNumber(serialNumber: String): MeterReading? {
        val querySnapshot = collectionMeterReading
            .whereEqualTo("serial_number", serialNumber)
            .orderBy("date", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()

        return if (querySnapshot.documents.isNotEmpty()) {
            val document = querySnapshot.documents[0]
            MeterReading(
                value = document.getDouble("value") ?: 0.0,
                date = document.getDate("date")!!,
                serial_number = document.getString("serial_number") ?: ""
            )
        } else {
            null
        }
    }

    //Функция получения списка показаний по серийному номеру
    fun getMeterReadings(serialNumber: String): Flow<List<MeterReading>> = callbackFlow {
        val listenerRegistration = firestore.collection("Meter readings")
            .whereEqualTo("serial_number", serialNumber)
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val readings = value?.documents?.mapNotNull { document ->
                    MeterReading(
                        value = document.getDouble("value") ?: 0.0,
                        date = document.getDate("date")!!,
                        serial_number = document.getString("serial_number") ?: ""
                    )
                }

                readings?.let { this.trySend(it).isSuccess }
            }

        awaitClose { listenerRegistration.remove() }
    }


}