package com.example.libraryapp.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.libraryapp.data.model.Book
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseBookRepository {
    @SuppressLint("StaticFieldLeak")
    private val db = FirebaseFirestore.getInstance()

    fun getBooks(callback: (List<Book>) -> Unit) {
        db.collection("books").get()
            .addOnSuccessListener { snapshot ->
                val books = snapshot.documents.mapNotNull { doc ->
                    val book = doc.toObject(Book::class.java)
                    book?.copy(id = doc.id)}
                callback(books)
            }
            .addOnFailureListener {
                Log.e("CatalogViewModel", "Ошибка загрузки книг: ${it.message}")
            }
    }
}