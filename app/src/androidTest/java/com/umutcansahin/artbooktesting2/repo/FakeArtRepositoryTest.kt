package com.umutcansahin.artbooktesting2.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.umutcansahin.artbooktesting2.model.ImageResponce
import com.umutcansahin.artbooktesting2.roomdb.Art
import com.umutcansahin.artbooktesting2.util.Resource

class FakeArtRepositoryTest : ArtRepositoryInterface {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun delete(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponce> {
        return Resource.success(ImageResponce(listOf(),0,0))
    }

    private fun refreshData() {
        artsLiveData.value = arts
    }
}