package com.umutcansahin.artbooktesting2.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.umutcansahin.artbooktesting2.R
import com.umutcansahin.artbooktesting2.databinding.FragmentArtDetailBinding
import com.umutcansahin.artbooktesting2.util.Status
import com.umutcansahin.artbooktesting2.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailsFragment @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_art_detail) {

    private var fragmentBinding : FragmentArtDetailBinding? = null

    lateinit var viewModel: ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)


        val binding = FragmentArtDetailBinding.bind(view)
        fragmentBinding = binding

        subcribeToObservers()

        binding.detailImageView.setOnClickListener {
            val action = ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment()
            findNavController().navigate(action)
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.detailSaveButton.setOnClickListener {
            viewModel.makeArt(
                binding.detailNameText.text.toString(),
                binding.detailArtistNameText.text.toString(),
                binding.detailYearText.text.toString())
        }
    }

    private fun subcribeToObservers() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            fragmentBinding?.let {
                glide.load(url).into(it.detailImageView)
            }
        })

        viewModel.inserArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error",Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {

                }
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        fragmentBinding = null
    }
}