package com.umutcansahin.artbooktesting2.repo

import androidx.lifecycle.LiveData
import com.umutcansahin.artbooktesting2.model.ImageResponce
import com.umutcansahin.artbooktesting2.roomdb.Art
import com.umutcansahin.artbooktesting2.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun delete(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponce>
}