package com.example.libraryapp.ui.bookDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.libraryapp.R
import com.example.libraryapp.data.Book
import androidx.fragment.app.Fragment

class BookDetailsFragment : Fragment() {

    private lateinit var titleText: TextView
    private lateinit var authorText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var coverImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_book_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val book = arguments?.getSerializable("book") as? Book ?: return

        // инициализируем текст
        titleText.text = "Выбранная книга: ${book.title}"
        authorText.text = book.author
        //descriptionText.text = book.description
        coverImage.setImageResource(book.image)
    }
}

