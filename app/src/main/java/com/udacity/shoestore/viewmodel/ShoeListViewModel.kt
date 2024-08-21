package com.udacity.shoestore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class ShoeListViewModel : ViewModel() {
    private val _shoes = MutableLiveData<List<Shoe>>()
    val shoes: LiveData<List<Shoe>>
        get() = _shoes

    fun addShoe(shoe: Shoe) {
        val currentShoes = _shoes.value ?: emptyList()
        _shoes.value = currentShoes + shoe
    }

    init {
        _shoes.value = listOf(
            Shoe("Nice Shoes", 40.0, "Nake", "This is a very nice pair of shoes"),
            Shoe("Ugly Shoes", 44.0, "Addidos", "This is a very ugly pair of shoes"),
            Shoe("Expensive Shoes", 42.0, "Biti's", "This is a very expensive pair of shoes"),
        )
    }
}