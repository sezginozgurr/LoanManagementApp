package com.loanmanagementapp.manager

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreManager @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun saveUser(uid: String, userDto: UserDto) {
        firestore.collection("users").document(uid).set(userDto)
    }

    suspend fun getUser(uid: String): UserDto? {
        val snapshot = firestore.collection("users").document(uid).get().await()
        return snapshot.toObject(UserDto::class.java)
    }
}
