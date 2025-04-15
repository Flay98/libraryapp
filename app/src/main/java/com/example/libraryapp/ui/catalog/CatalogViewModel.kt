package com.example.libraryapp.ui.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraryapp.R
import com.example.libraryapp.data.Book
import com.example.libraryapp.data.BookRepository

class CatalogViewModel : ViewModel() {

    // Все книги (например, с сервера)
    private val _allBooks = MutableLiveData<List<Book>>()
    val allBooks: LiveData<List<Book>> get() = _allBooks

    // Отфильтрованные книги — то, что показываем на экране
    private val _filteredBooks = MutableLiveData<List<Book>>()
    val filteredBooks: LiveData<List<Book>> get() = _filteredBooks

    init {
        loadMockBooks()
    }

    private fun loadMockBooks() {
        val books = BookRepository.getAllBooks()
        _allBooks.value = books
        _filteredBooks.value = books
    }

    fun filterBooks(query: String?) {
        val currentList = _allBooks.value ?: return

        if (query.isNullOrEmpty()) {
            _filteredBooks.value = currentList
        } else {
            _filteredBooks.value = currentList.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.author.contains(query, ignoreCase = true)
            }
        }
    }
}