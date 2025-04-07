package com.example.libraryapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraryapp.R
import com.example.libraryapp.data.Book

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
        val mockBooks = listOf(
            Book("Тихий Дэн", "Андрей Таренков", R.drawable.den),
            Book("Цитаты Стэтхема", "Джейсон Стэтхем", R.drawable.statham),
            Book("Так говорил Жириновский", "Владимир Жириновский", R.drawable.zhirik),
            Book("Заводной Апельсин", "Энтони Бёрджэсс", R.drawable.apelsin)
        )

        val mockBooks1 = listOf(
            Book("Тихий Дэн", "Андрей Таренков", R.drawable.den),
            Book("Цитаты Стэтхема", "Джейсон Стэтхем", R.drawable.statham),
        )

        val mockBooks2 = listOf(
            Book("Заводной Апельсин", "Энтони Бёрджэсс", R.drawable.apelsin)
        )

        // Заглушки, но можно и разные списки
        _readingBooks.value = mockBooks
        _wishlistBooks.value = mockBooks1
        _readBooks.value = mockBooks2
    }
}