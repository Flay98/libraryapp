package com.example.libraryapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.R
import com.example.libraryapp.data.adapter.BookAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    private lateinit var readingList: RecyclerView
    private lateinit var wishlist: RecyclerView
    private lateinit var readList: RecyclerView

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        readingList = view.findViewById(R.id.readingRecyclerView1)
        wishlist = view.findViewById(R.id.readingRecyclerView2)
        readList = view.findViewById(R.id.readingRecyclerView3)


        readingList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        wishlist.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        readList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewModel.readingBooks.observe(viewLifecycleOwner) { books ->
            readingList.adapter = BookAdapter(books) {}
        }

        viewModel.wishlistBooks.observe(viewLifecycleOwner) { books ->
            wishlist.adapter = BookAdapter(books) {
            }
        }

        viewModel.readBooks.observe(viewLifecycleOwner) { books ->
            readList.adapter = BookAdapter(books) {
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<Boolean>("refreshHome")
            ?.observe(viewLifecycleOwner) { shouldRefresh ->
                if (shouldRefresh) {
                    println("📘 TRIGGERED REFRESH!")
                    viewModel.refresh()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        readingList.adapter = null
        wishlist.adapter = null
        readList.adapter = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

}