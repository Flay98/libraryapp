package com.example.libraryapp.ui.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraryapp.data.Book
import com.example.libraryapp.data.FirebaseBookRepository

class CatalogViewModel : ViewModel() {

    private var allBooks: List<Book> = listOf()

    private val _filteredBooks = MutableLiveData<List<Book>>()
    val filteredBooks: LiveData<List<Book>> get() = _filteredBooks

    init {
        loadBooks()
    }

    private fun loadBooks() {
        FirebaseBookRepository.getBooks { books ->
            allBooks = books
            _filteredBooks.value = books
        }
    }

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