package com.example.libraryapp.data

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val image: Int,
    val description: String,
    var status: String = ""
)
