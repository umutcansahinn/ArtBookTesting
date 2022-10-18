package com.umutcansahin.artbooktesting2.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.umutcansahin.artbooktesting2.MainCoroutineRule
import com.umutcansahin.artbooktesting2.getOrAwaitValueTest
import com.umutcansahin.artbooktesting2.repo.FakeArtRepository
import com.umutcansahin.artbooktesting2.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()  //her seyi main thraid'de calıstırmayı sağlar

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup() {
        //test Doubles(sahte ile test)

        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`() {
        viewModel.makeArt("Mona Lisa","Da Vinci","")
        val value = viewModel.inserArtMessage.getOrAwaitValueTest()           //burada 'liveData' normal data döndürüldü bu islemi yaparken interneten alınan hazır kod sayesinde yapıldı.
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without name returns error`() {
        viewModel.makeArt("","Da Vinci","1800")
        val value = viewModel.inserArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName returns error`() {
        viewModel.makeArt("Mona Lisa","","1800")
        val value = viewModel.inserArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}