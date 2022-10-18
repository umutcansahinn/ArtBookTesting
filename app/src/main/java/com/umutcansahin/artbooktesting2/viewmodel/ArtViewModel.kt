package com.umutcansahin.artbooktesting2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutcansahin.artbooktesting2.model.ImageResponce
import com.umutcansahin.artbooktesting2.repo.ArtRepositoryInterface
import com.umutcansahin.artbooktesting2.roomdb.Art
import com.umutcansahin.artbooktesting2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface
): ViewModel() {

    //Art Fragment

    val artList = repository.getArt()

    //Image API fragment

    private val images = MutableLiveData<Resource<ImageResponce>>()
    val imageList: LiveData<Resource<ImageResponce>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl : LiveData<String>
        get() = selectedImage

    //Art Detail Fragment

    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val inserArtMessage : LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetInsertArtMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url: String) {
        selectedImage.value = url
    }

    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.delete(art)
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(name: String, artistName: String, year: String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            insertArtMsg.value = Resource.error("Enter name, artist, year",null)
            return
        }
        val yearInt = try {
            year.toInt()
        }catch (e :Exception) {
            insertArtMsg.value = Resource.error("Year should be number", null)
            return
        }

        val art = Art(name, artistName,yearInt,selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.value = Resource.success(art)
    }

    fun searchForImage(searchString: String) {
        if (searchString.isEmpty()) {
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val responce = repository.searchImage(searchString)
            images.value = responce
        }
    }
}