package com.example.libraryapp.ui.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraryapp.R
import com.example.libraryapp.data.Book

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
        val books = listOf(
            Book("Тихий Дэн", "Андрей Таренков", R.drawable.den),
            Book("Заводной Апельсин", "Энтони Бёрджесс", R.drawable.apelsin),
            Book("Пацанские цитаты", "Джейсон Стэтхэм", R.drawable.statham),
            Book("Так говорил Жириновский", "Владимир Жириновский", R.drawable.zhirik),
            Book("Это я, Эдичка", "Эдуард Лимонов", R.drawable.eto_ya),
            Book("Муму", "Иван Тургенев", R.drawable.mymy),
            Book("Братья Карамазовы", "Федор Достоевский", R.drawable.karamozovy),
            Book("Анна Каренина", "Лев Толстой", R.drawable.karenina),
            Book("Мы", "Евгений Замятин", R.drawable.mbl)
        )
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