package com.example.libraryapp.data

import com.example.libraryapp.R

object BookRepository {
    private val books = listOf(
        Book(1, "Тихий Дэн", "Андрей Таренков", R.drawable.den, "«Тихий Дэн» — роман, который стирает границы между героизмом и обыденностью, погружая читателя в мир простого человека, оказавшегося на дне. Главный герой, Дэн — обычный житель пригорода Нью-Йорка, чья жизнь рушится после увольнения с работы уборщика. В попытках справиться с несправедливостью, одиночеством и внутренними демонами он оказывается втянутым в водоворот событий, где каждая встреча или конфликт становятся роковыми. Его поступки противоречивы, порой пугающе жестоки, но это — взгляд на мир глазами человека, который не боится довести до уголовки."),
        Book(2, "Цитаты Стэтхема", "Джейсон Стэтхэм", R.drawable.statham, "Описание книги 2"),
        Book(3, "Заводной Апельсин", "Энтони Бёрджесс", R.drawable.apelsin, "Описание книги 3"),
        Book(4,"Так говорил Жириновский", "Владимир Жириновский", R.drawable.zhirik, "Описание книги 4"),
        Book(5,"Это я, Эдичка", "Эдуард Лимонов", R.drawable.eto_ya, "sdadsad"),
        Book(6, "Муму", "Иван Тургенев", R.drawable.mymy, "asdasdasdasd"),
        Book(7, "Братья Карамазовы", "Федор Достоевский", R.drawable.karamozovy, "dfdfdfdfdf"),
        Book(8, "Анна Каренина", "Лев Толстой", R.drawable.karenina, "3ewqewqeweqwe"),
        Book(9,"Мы", "Евгений Замятин", R.drawable.mbl, "asdasdasdasdsad")
    )

    fun getBookById(id: Int): Book? {
        return books.find { it.id == id }
    }

    fun getAllBooks(): List<Book> = books

    fun updateBookStatus(id: Int, status: String) {
        getBookById(id)?.status = status
    }
}