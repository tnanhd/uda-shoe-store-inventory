package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.viewmodel.ShoeDetailViewModel
import com.udacity.shoestore.viewmodel.ShoeListViewModel

class ShoeDetailFragment : Fragment() {

    private val shoeListViewModel: ShoeListViewModel by activityViewModels()
    private lateinit var shoeDetailViewModel: ShoeDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentShoeDetailBinding.inflate(inflater, container, false)

        shoeDetailViewModel = ViewModelProvider(this).get(ShoeDetailViewModel::class.java)
        binding.shoeDetail = shoeDetailViewModel
        binding.lifecycleOwner = this

        binding.cancelButton.setOnClickListener {
            it.findNavController().navigateUp()
        }

        binding.saveButton.setOnClickListener {
            val shoeSizeStr = shoeDetailViewModel.shoeSize.value

            if (!canConvertToDouble(shoeSizeStr)) {
                Toast.makeText(requireContext(), "Shoe size must be number!", Toast.LENGTH_LONG)
                    .show()
                binding.shoeSizeEdittext.requestFocus()
                return@setOnClickListener
            }

            val shoeSize: Double =
                if (canConvertToDouble(shoeSizeStr)) shoeSizeStr!!.toDouble() else 0.0

            val shoe = Shoe(
                shoeDetailViewModel.shoeName.value ?: "",
                shoeSize,
                shoeDetailViewModel.shoeCompany.value ?: "",
                shoeDetailViewModel.shoeDescription.value ?: ""
            )
            shoeListViewModel.addShoe(shoe)
            it.findNavController().navigateUp()
        }

        return binding.root
    }

    private fun canConvertToDouble(value: String?): Boolean {
        return try {
            value?.toDouble()
            true
        } catch (e: Exception) {
            // Any exception then false
            false
        }
    }
}