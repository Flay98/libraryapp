package com.example.libraryapp.data

import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore


object FirebaseBookRepository {
    @SuppressLint("StaticFieldLeak")
    private val db = FirebaseFirestore.getInstance()

    fun getBooks(callback: (List<Book>) -> Unit) {
        db.collection("books").get()
            .addOnSuccessListener { result ->
                val books = result.mapNotNull { it.toObject(Book::class.java) }
                callback(books)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }
}
