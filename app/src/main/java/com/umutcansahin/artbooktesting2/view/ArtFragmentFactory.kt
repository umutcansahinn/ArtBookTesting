package com.umutcansahin.artbooktesting2.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.umutcansahin.artbooktesting2.adapter.ArtRecyclerAdapter
import com.umutcansahin.artbooktesting2.adapter.ImageRecyclerAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val imageRecyclerAdapter: ImageRecyclerAdapter,
    private val glide: RequestManager
) : FragmentFactory(){

    @OptIn(ExperimentalMultiplatform::class)
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className) {
            ArtFragment::class.java.name -> ArtFragment(artRecyclerAdapter)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            else -> super.instantiate(classLoader, className)
        }

    }
}