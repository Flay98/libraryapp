package com.example.libraryapp.ui.catalog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraryapp.R
import com.example.libraryapp.data.Book
import com.example.libraryapp.data.BookRepository
import com.example.libraryapp.data.FirebaseBookRepository
import com.google.firebase.firestore.FirebaseFirestore

class CatalogViewModel : ViewModel() {

    // Все книги (например, с сервера)
    //private val _allBooks = MutableLiveData<List<Book>>()
    //val allBooks: LiveData<List<Book>> get() = _allBooks
    private var allBooks: List<Book> = listOf()

    // Отфильтрованные книги — то, что показываем на экране
    private val _filteredBooks = MutableLiveData<List<Book>>()
    val filteredBooks: LiveData<List<Book>> get() = _filteredBooks

    init {
        loadMockBooks()
    }

    private fun loadMockBooks() {
        FirebaseFirestore.getInstance()
            .collection("books")
            .get()
            .addOnSuccessListener { snapshot ->
                val books = snapshot.documents.mapNotNull { doc ->
                    val book = doc.toObject(Book::class.java)
                    book?.copy(id = doc.id) // присваиваем documentId в поле id
                }
                allBooks = books // сохраняем список для поиска
                _filteredBooks.value = books // отображаем всё по умолчанию
            }
            .addOnFailureListener {
                Log.e("CatalogViewModel", "Ошибка загрузки книг: ${it.message}")
            }
    }

    /**fun filterBooks(query: String?) {
        val currentList = _allBooks.value ?: return

        if (query.isNullOrEmpty()) {
            _filteredBooks.value = currentList
        } else {
            _filteredBooks.value = currentList.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.author.contains(query, ignoreCase = true)
            }
        }
    }*/

    fun filterBooks(query: String?) {
        _filteredBooks.value = if (query.isNullOrBlank()) {
            allBooks
        } else {
            allBooks.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.author.contains(query, ignoreCase = true)
            }
        }
    }
}