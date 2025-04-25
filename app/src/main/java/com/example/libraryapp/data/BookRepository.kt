package com.example.libraryapp.data

import com.example.libraryapp.R

object BookRepository {
    private val books = listOf(
        //Book(1.toString(), "Тихий Дэн", "Андрей Таренков", R.drawable.den.toString(), "«Тихий Дэн» — роман, который стирает границы между героизмом и обыденностью, погружая читателя в мир простого человека, оказавшегося на дне. Главный герой, Дэн — обычный житель пригорода Нью-Йорка, чья жизнь рушится после увольнения с работы уборщика. В попытках справиться с несправедливостью, одиночеством и внутренними демонами он оказывается втянутым в водоворот событий, где каждая встреча или конфликт становятся роковыми. Его поступки противоречивы, порой пугающе жестоки, но это — взгляд на мир глазами человека, который не боится довести до уголовки."),
        Book(2.toString(), "Цитаты Стэтхема", "Джейсон Стэтхэм", R.drawable.statham.toString(), "Описание книги 2"),
        Book(3.toString(), "Заводной Апельсин", "Энтони Бёрджесс", R.drawable.apelsin.toString(), "Описание книги 3"),
        Book(4.toString(),"Так говорил Жириновский", "Владимир Жириновский", R.drawable.zhirik.toString(), "Описание книги 4"),
        Book(5.toString(),"Это я, Эдичка", "Эдуард Лимонов", R.drawable.eto_ya.toString(), "sdadsad"),
        Book(6.toString(), "Муму", "Иван Тургенев", R.drawable.mymy.toString(), "asdasdasdasd"),
        Book(7.toString(), "Братья Карамазовы", "Федор Достоевский", R.drawable.karamozovy.toString(), "dfdfdfdfdf"),
        Book(8.toString(), "Анна Каренина", "Лев Толстой", R.drawable.karenina.toString(), "3ewqewqeweqwe"),
        Book(9.toString(),"Мы", "Евгений Замятин", R.drawable.mbl.toString(), "asdasdasdasdsad")
    )

    fun getBookById(id: Int): Book? {
        return books.find { it.id.toInt() == id }
    }

    fun getAllBooks(): List<Book> = books

    fun updateBookStatus(id: Int, status: String) {
        getBookById(id)?.status = status
    }
}