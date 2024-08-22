package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.databinding.ShoeItemBinding
import com.udacity.shoestore.viewmodel.ShoeListViewModel

class ShoeListFragment : Fragment() {

    private val viewModel: ShoeListViewModel by activityViewModels()
    private lateinit var navController: NavController

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

        navController = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_logout, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_logout -> {
                        showConfirmLogoutDialog()
                        true
                    }

                    else -> false
                }
            }

        }
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showConfirmLogoutDialog()
                }
            })
    }

    private fun showConfirmLogoutDialog() {
        MaterialAlertDialogBuilder(requireContext(), R.style.CustomMaterialAlertDialogTheme)
            .setTitle("Signing Out")
            .setMessage("Are you sure you want to log out?")
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Yes") { dialog, _ ->
                logout()
                dialog.dismiss()
            }
            .show()
    }

    private fun logout() {
        navController.navigate(R.id.action_shoeListFragment_to_loginFragment)
    }

}