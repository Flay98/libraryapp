package com.example.libraryapp.ui.bookDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraryapp.data.Book
import com.example.libraryapp.data.BookRepository

class BookDetailsView : ViewModel() {

    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book> get() = _book

    fun loadBook(bookId: Int) {
        val result = BookRepository.getBookById(bookId)
        result?.let {
            _book.value = it
        }
    }

    fun updateBookStatus(status: String) {
        _book.value?.let {
            BookRepository.updateBookStatus(it.id, status)
            _book.value = it.copy(status = status) // триггерим обновление UI
        }
    }
}