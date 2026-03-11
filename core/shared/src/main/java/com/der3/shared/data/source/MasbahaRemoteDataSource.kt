package com.der3.shared.data.source

import android.util.Log
import com.der3.shared.data.dto.MasbahaAzkarDto
import com.der3.shared.data.dto.MasbahaConfigDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MasbahaRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun getVersion(): Int {
        return try {
            val snapshot = firestore.collection("masbaha_azkars")
                .document("config")
                .get()
                .await()
            val config = snapshot.toObject(MasbahaConfigDto::class.java)
            config?.version ?: 0
        } catch (e: Exception) {
            0
        }
    }

    suspend fun getAzkars(): List<MasbahaAzkarDto> {
        return try {
            val snapshot = firestore.collection("masbaha_azkars")
                .get()
                .await()
            snapshot.documents
                .filter { it.id != "config" } // Filter out the config document
                .mapNotNull { it.toObject(MasbahaAzkarDto::class.java) }
        } catch (e: Exception) {
            Log.i("MasbahaRemoteDataSource",e.message.toString())
            emptyList()
        }
    }
}
