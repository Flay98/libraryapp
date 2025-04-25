package com.example.libraryapp.data

data class Book(
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val imageURL: String = "",
    val description: String = "",
    var status: String = ""
)
