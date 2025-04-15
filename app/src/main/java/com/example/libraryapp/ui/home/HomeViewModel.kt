package com.example.libraryapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraryapp.R
import com.example.libraryapp.data.Book
import com.example.libraryapp.data.BookRepository

class HomeViewModel : ViewModel() {

    // LiveData для каждого раздела
    private val _readingBooks = MutableLiveData<List<Book>>()
    val readingBooks: LiveData<List<Book>> get() = _readingBooks

    private val _wishlistBooks = MutableLiveData<List<Book>>()
    val wishlistBooks: LiveData<List<Book>> get() = _wishlistBooks

    private val _readBooks = MutableLiveData<List<Book>>()
    val readBooks: LiveData<List<Book>> get() = _readBooks

    init {
        loadMockData()
    }

    private fun loadMockData() {
        val books = BookRepository.getAllBooks()

        _readingBooks.value = books.filter { it.status == "читаю" }
        _wishlistBooks.value = books.filter { it.status == "хочу" }
        _readBooks.value = books.filter { it.status == "прочитано" }

    }

    fun refresh() {
        loadMockData()
    }
}