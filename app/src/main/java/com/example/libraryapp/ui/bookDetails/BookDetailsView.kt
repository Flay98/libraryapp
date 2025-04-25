package com.example.libraryapp.ui.bookDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraryapp.data.Book
import com.example.libraryapp.data.BookRepository
import com.google.firebase.firestore.FirebaseFirestore

class BookDetailsView : ViewModel() {

    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book> get() = _book

    private val db = FirebaseFirestore.getInstance()

    fun loadBook(bookId: String) {
        db.collection("books").document(bookId).get()
            .addOnSuccessListener { document ->
                val book = document.toObject(Book::class.java)
                if (book != null) {
                    _book.value = book.copy(id = bookId)
                }
            }
            .addOnFailureListener {
                Log.e("BookDetailsViewModel", "Ошибка загрузки книги", it)
            }
    }

    fun updateBookStatus(status: String) {
        val book = _book.value ?: return
        val bookId = book.id

        db.collection("books").document(bookId)
            .update("status", status)
            .addOnSuccessListener {
                _book.value = book.copy(status = status)
            }
            .addOnFailureListener {
                Log.e("BookDetailsViewModel", "Ошибка обновления статуса", it)
            }
    }
}