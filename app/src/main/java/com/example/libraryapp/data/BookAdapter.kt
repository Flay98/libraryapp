package com.example.libraryapp.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.libraryapp.R

class BookAdapter(private val books: List<Book>,
                  private val onBookClick: (Book) -> Unit) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.bookTitle)
        val author = view.findViewById<TextView>(R.id.bookAuthor)
        val cover = view.findViewById<ImageView>(R.id.bookCover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.title.text = book.title
        holder.author.text = book.author
        //holder.cover.setImageResource(book.image)
        Glide.with(holder.cover.context)
            .load(book.imageURL)
            // необязательно: картинка, если ошибка
            .into(holder.cover)
        holder.itemView.setOnClickListener {
            onBookClick(book)
        }
    }

    override fun getItemCount(): Int = books.size
}