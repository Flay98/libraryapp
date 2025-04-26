package com.example.libraryapp.ui.bookDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.libraryapp.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.libraryapp.data.model.Book
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BookDetailsFragment : Fragment() {

    private lateinit var titleText: TextView
    private lateinit var authorText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var coverImage: ImageView
    private val viewModel: BookDetailsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_book_details, container, false)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleText = view.findViewById(R.id.titleText)
        authorText = view.findViewById(R.id.bookAuthor)
        descriptionText = view.findViewById(R.id.bookDescription)
        coverImage = view.findViewById(R.id.bookCover)

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val contentContainer = view.findViewById<LinearLayout>(R.id.bookContentContainer)

        progressBar.visibility = View.VISIBLE
        contentContainer.visibility = View.GONE

        val bookId = arguments?.getString("bookId") ?: return
        viewModel.loadBook(bookId)

        viewModel.book.observe(viewLifecycleOwner) { book ->
            titleText.text = "Выбранная книга: ${book.title}"
            authorText.text = book.author
            descriptionText.text = book.description
            Glide.with(requireContext())
                .load(book.imageURL)
                .into(coverImage)
            progressBar.visibility = View.GONE
            contentContainer.visibility = View.VISIBLE
        }

        val btnReading = view.findViewById<Button>(R.id.btnNowReading)

        btnReading.setOnClickListener {
            viewModel.book.value?.let { book ->
                addBookToUserList(book, "читаю")
            }
            Toast.makeText(requireContext(), "Добавлено в \"Сейчас читаю\"", Toast.LENGTH_SHORT).show()

            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("refreshHome", true)
        }

        val btnWishlist = view.findViewById<Button>(R.id.btnWishlist)

        btnWishlist.setOnClickListener {
            viewModel.book.value?.let { book ->
                addBookToUserList(book, "хочу")
            }
            Toast.makeText(requireContext(), "Добавлено в \"Хочу прочитать\"", Toast.LENGTH_SHORT).show()

            val navController = findNavController()
            navController.previousBackStackEntry?.savedStateHandle?.set("refreshHome", true)
        }

        val btnRead = view.findViewById<Button>(R.id.btnRead)

        btnRead.setOnClickListener {
            viewModel.book.value?.let { book ->
                addBookToUserList(book, "прочитано")
            }
            Toast.makeText(requireContext(), "Добавлено в \"Прочитано\"", Toast.LENGTH_SHORT).show()

            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("refreshHome", true)
        }

        val btnBack = view.findViewById<Button>(R.id.btnBackToCatalog)

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun addBookToUserList(book: Book, status: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            Toast.makeText(requireContext(), "Ошибка: пользователь не авторизован", Toast.LENGTH_SHORT).show()
            return
        }

        val userBooksRef = FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .collection("books")

        val bookData = mapOf(
            "id" to book.id,
            "title" to book.title,
            "author" to book.author,
            "imageURL" to book.imageURL,
            "description" to book.description,
            "status" to status
        )

        userBooksRef.document(book.id).set(bookData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Книга добавлена в раздел \"$status\"", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Ошибка добавления книги: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

}

