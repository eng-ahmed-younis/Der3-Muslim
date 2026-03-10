package com.der3.shared.data.source

import com.der3.shared.data.dto.MasbahaAzkarDto

class MasbahaRemoteDataSource(
    private val firestore: FirebaseFirestore
) {

    fun getAzkars(onResult: (List<MasbahaAzkarDto>) -> Unit) {

        firestore.collection("azkars")
            .get()
            .addOnSuccessListener { snapshot ->

                val list = snapshot.documents.mapNotNull {
                    it.toObject(MasbahaAzkarDto::class.java)
                }

                onResult(list)
            }
    }
}