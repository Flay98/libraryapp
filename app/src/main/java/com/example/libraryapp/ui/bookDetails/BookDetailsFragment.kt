package com.example.libraryapp.ui.bookDetails

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

class BookDetailsFragment : Fragment() {

    private lateinit var titleText: TextView
    private lateinit var authorText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var coverImage: ImageView
    private val viewModel: BookDetailsView by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_book_details, container, false)

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
            viewModel.updateBookStatus("читаю")
            Toast.makeText(requireContext(), "Добавлено в \"Сейчас читаю\"", Toast.LENGTH_SHORT).show()

            // Сообщаем HomeFragment, чтобы он обновил список
            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("refreshHome", true)
        }

        val btnWishlist = view.findViewById<Button>(R.id.btnWishlist)

        btnWishlist.setOnClickListener {
            viewModel.updateBookStatus("хочу")
            Toast.makeText(requireContext(), "Добавлено в \"Хочу прочитать\"", Toast.LENGTH_SHORT).show()

            val navController = findNavController()
            navController.previousBackStackEntry?.savedStateHandle?.set("refreshHome", true)
        }

        val btnRead = view.findViewById<Button>(R.id.btnRead)

        btnRead.setOnClickListener {
            viewModel.updateBookStatus("прочитано")
            Toast.makeText(requireContext(), "Добавлено в \"Прочитано\"", Toast.LENGTH_SHORT).show()

            // Сообщаем HomeFragment, чтобы он обновил список
            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("refreshHome", true)
        }

        val btnBack = view.findViewById<Button>(R.id.btnBackToCatalog)

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}

