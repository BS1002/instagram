package com.mahfuznow.instagram.ui.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mahfuznow.instagram.data.repository.DummyRepository
import com.mahfuznow.instagram.util.getOrAwaitValue
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class HomeFragmentViewModelTest {

    //setting rules to execute each test synchronously
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    //This is required in order to use Coroutine inside Test
    //In our viewModel we have used .asLiveData() which internally uses coroutine
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var homeFragmentViewModel: HomeFragmentViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        homeFragmentViewModel = HomeFragmentViewModel(DummyRepository())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `load posts data`() {
        var posts = homeFragmentViewModel.posts.getOrAwaitValue()
        assertThat(posts.data).isNull() //Loading State
        posts = homeFragmentViewModel.posts.getOrAwaitValue()
        assertThat(posts.data?.total).isEqualTo(873) //Success State
    }

    @Test
    fun `load users data`() {
        var users = homeFragmentViewModel.users.getOrAwaitValue()
        assertThat(users.data).isNull() //Loading State
        users = homeFragmentViewModel.users.getOrAwaitValue()
        assertThat(users.data?.total).isEqualTo(99) //Success State
    }


    @Test
    fun `demo coroutine 1`() {
        runBlocking {
            launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
                delay(2000)
                assertThat(true).isTrue()
            }
        }
    }

    @Test
    fun `demo coroutine 2` () = runTest {
        delay(2000)
        assertThat(true).isTrue()
    }
}