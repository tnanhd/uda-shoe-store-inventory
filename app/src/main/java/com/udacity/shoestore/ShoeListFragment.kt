package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.databinding.ShoeItemBinding
import com.udacity.shoestore.viewmodel.ShoeListViewModel

class ShoeListFragment : Fragment() {

    private val viewModel: ShoeListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentShoeListBinding.inflate(inflater, container, false)

        viewModel.shoes.observe(viewLifecycleOwner) { shoes ->
            shoes.forEach { shoe ->
                val shoeItemBinding = ShoeItemBinding.inflate(inflater, container, false)
                shoeItemBinding.shoeName.text = shoe.name
                shoeItemBinding.shoeCompany.text = shoe.company
                shoeItemBinding.shoeSize.text = shoe.size.toString()
                shoeItemBinding.shoeDescription.text = shoe.description
                if (shoe.images.isNotEmpty()) {
                    shoeItemBinding.shoesImage.setImageResource(shoe.images[0].toInt())
                }
                binding.productsContainer.addView(shoeItemBinding.root)
            }
        }

        binding.fab.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_shoeListFragment_to_shoeDetailFragment)
        )

        return binding.root
    }

}