package com.example.libraryapp.ui.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CatalogViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "123"
    }
    val text: LiveData<String> = _text
}