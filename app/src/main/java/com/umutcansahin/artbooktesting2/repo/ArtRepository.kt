package com.umutcansahin.artbooktesting2.repo

import androidx.lifecycle.LiveData
import com.umutcansahin.artbooktesting2.api.RetrofitAPI
import com.umutcansahin.artbooktesting2.model.ImageResponce
import com.umutcansahin.artbooktesting2.roomdb.Art
import com.umutcansahin.artbooktesting2.roomdb.ArtDao
import com.umutcansahin.artbooktesting2.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI
) : ArtRepositoryInterface {

    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun delete(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponce> {
        return try {
            val responce = retrofitAPI.imageSearch(imageString)
            if (responce.isSuccessful) {
                responce.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else {
                Resource.error("Error",null)
            }

        }catch (e: Exception) {
            Resource.error("No Data",null)
        }
    }
}