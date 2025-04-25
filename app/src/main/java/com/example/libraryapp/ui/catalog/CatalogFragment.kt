package com.example.libraryapp.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.R
import com.example.libraryapp.data.BookAdapter

class CatalogFragment : Fragment() {

    private val viewModel: CatalogViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var bookAdapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_catalog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.catalogRecyclerView)
        searchView = view.findViewById(R.id.searchView)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        bookAdapter = BookAdapter(emptyList()) { book ->
            val bundle = Bundle()
            bundle.putString("bookId", book.id)
            findNavController().navigate(R.id.action_catalogFragment_to_bookDetailsFragment, bundle)
        } // пока пустой
        recyclerView.adapter = bookAdapter

        // Подписка на данные
        viewModel.filteredBooks.observe(viewLifecycleOwner) { books ->
            bookAdapter = BookAdapter(books) { book ->
                // например, переход на BookDetailsFragment
                val bundle = Bundle()
                bundle.putString("bookId", book.id)
                findNavController().navigate(R.id.action_catalogFragment_to_bookDetailsFragment, bundle)
            }
            recyclerView.adapter = bookAdapter
        }

        // Поиск
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterBooks(newText)
                return true
            }
        })
    }
}