package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.viewmodel.ShoeListViewModel

class ShoeDetailFragment : Fragment() {

    private val viewModel: ShoeListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentShoeDetailBinding.inflate(inflater, container, false)

        binding.cancelButton.setOnClickListener {
            it.findNavController().navigateUp()
        }

        binding.saveButton.setOnClickListener {
            val shoeSizeStr = binding.shoeSizeEdittext.text.toString()

            if (!canConvertToDouble(shoeSizeStr)) {
                Toast.makeText(requireContext(), "Shoe size must be number!", Toast.LENGTH_LONG).show()
                binding.shoeSizeEdittext.requestFocus()
                return@setOnClickListener
            }

            val shoeSize: Double =
                if (canConvertToDouble(shoeSizeStr)) shoeSizeStr
                    .toDouble() else 0.0

            val shoe = Shoe(
                binding.shoeNameEdittext.text.toString(),
                shoeSize,
                binding.shoeCompanyEdittext.text.toString(),
                binding.shoeDescriptionEdittext.text.toString()
            )
            viewModel.addShoe(shoe)
            it.findNavController().navigateUp()
        }

        return binding.root
    }

    private fun canConvertToDouble(value: String): Boolean {
        return try {
            value.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}