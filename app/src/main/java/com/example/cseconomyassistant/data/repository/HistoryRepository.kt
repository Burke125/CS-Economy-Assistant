package com.example.cseconomyassistant.data.repository

import com.example.cseconomyassistant.data.model.BuyHistoryEntry
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.auth.FirebaseAuth

class HistoryRepository(private val uid: String) {

    private val db = FirebaseFirestore.getInstance()
    private val collection =
        db.collection("users")
            .document(uid)
            .collection("buy_history")

    fun save(entry: BuyHistoryEntry) {
        collection.add(entry)
    }

    fun observeHistory(onChange: (List<Pair<String, BuyHistoryEntry>>) -> Unit) {
        collection
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                val list = snapshot?.documents
                    ?.mapNotNull { doc ->
                        doc.toObject(BuyHistoryEntry::class.java)
                            ?.let { doc.id to it }
                    } ?: emptyList()

                onChange(list)
            }
    }

    fun delete(id: String) {
        collection.document(id).delete()
    }
}


