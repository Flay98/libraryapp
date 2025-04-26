package com.example.libraryapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraryapp.data.model.Book
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel : ViewModel() {

    private val _readingBooks = MutableLiveData<List<Book>>()
    val readingBooks: LiveData<List<Book>> get() = _readingBooks

    private val _wishlistBooks = MutableLiveData<List<Book>>()
    val wishlistBooks: LiveData<List<Book>> get() = _wishlistBooks

    private val _readBooks = MutableLiveData<List<Book>>()
    val readBooks: LiveData<List<Book>> get() = _readBooks

    init {
        loadUserBooks()
    }

    private fun loadUserBooks() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .collection("books")
            .get()
            .addOnSuccessListener { snapshot ->
                val books = snapshot.documents.mapNotNull { it.toObject(Book::class.java) }

                _readingBooks.value = books.filter { it.status == "читаю" }
                _wishlistBooks.value = books.filter { it.status == "хочу" }
                _readBooks.value = books.filter { it.status == "прочитано" }
            }
            .addOnFailureListener {
                Log.e("HomeViewModel", "Ошибка загрузки книг: ${it.message}")
            }
    }

    fun refresh() {
        loadUserBooks()
    }
}