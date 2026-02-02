package com.example.cseconomyassistant.data.auth

import com.google.firebase.auth.FirebaseAuth

object AuthManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun ensureSignedIn(onReady: (String) -> Unit) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            onReady(currentUser.uid)
        } else {
            auth.signInAnonymously()
                .addOnSuccessListener { result ->
                    onReady(result.user!!.uid)
                }
                .addOnFailureListener { e ->
                    throw IllegalStateException("Anonymous sign-in failed", e)
                }
        }
    }

    fun uid(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User not authenticated")
    }
}