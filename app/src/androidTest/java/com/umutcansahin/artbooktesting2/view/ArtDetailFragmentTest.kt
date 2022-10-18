package com.umutcansahin.artbooktesting2.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.umutcansahin.artbooktesting2.getOrAwaitValue
import com.umutcansahin.artbooktesting2.launchFragmentInHiltContainer
import com.umutcansahin.artbooktesting2.repo.FakeArtRepositoryTest
import com.umutcansahin.artbooktesting2.roomdb.Art
import com.umutcansahin.artbooktesting2.viewmodel.ArtViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ArtDetailFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromArtDetailsToImageAPI() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailsFragment>(factory = fragmentFactory) {
            Navigation.setViewNavController(requireView(),navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.artImageView)).perform(click())
        Mockito.verify(navController).navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())

    }

    @Test
    fun testOnBackPressed() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailsFragment>(factory = fragmentFactory) {
            Navigation.setViewNavController(requireView(),navController)
        }
        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun testSave() {
        val testViewModel = ArtViewModel(FakeArtRepositoryTest())
        launchFragmentInHiltContainer<ArtDetailsFragment>(factory = fragmentFactory) {
            viewModel = testViewModel
        }
        Espresso.onView(with(R.id.nameText)).perform(replaceText("Mona Lisa"))
        Espresso.onView(with(R.id.artistName)).perform(replaceText("Da Vinci"))
        Espresso.onView(with(R.id.yearText)).perform(replaceText("1500"))
        Espresso.onView(with(R.id.saveButton)).perform(click())

        assertThat(testViewModel.artList.getOrAwaitValue()).contains(
            Art("Mona Lisa","Da Vinci",1500,""))
    }
}