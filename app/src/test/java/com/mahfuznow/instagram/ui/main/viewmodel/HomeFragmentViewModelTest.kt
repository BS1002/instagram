package com.mahfuznow.instagram.ui.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mahfuznow.instagram.data.repository.DummyRepository
import com.mahfuznow.instagram.util.getOrAwaitValue
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runners.MethodSorters
import java.util.*

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    fun `a) load posts data`() = runBlocking {
        println("Started at ${Calendar.getInstance().timeInMillis}")
        var posts = homeFragmentViewModel.posts.getOrAwaitValue()
        assertThat(posts.data).isNull() //Loading State

        delay(1)

        posts = homeFragmentViewModel.posts.getOrAwaitValue()
        assertThat(posts.data?.total).isEqualTo(873) //Success State
        println("Ended at ${Calendar.getInstance().timeInMillis}")
    }

    @Test
    fun `b) load users data`() = runBlocking {
        println("Started at ${Calendar.getInstance().timeInMillis}")
        var users = homeFragmentViewModel.users.getOrAwaitValue()
        assertThat(users.data).isNull() //Loading State

        delay(1)

        users = homeFragmentViewModel.users.getOrAwaitValue()
        assertThat(users.data?.total).isEqualTo(99) //Success State
        println("Ended at ${Calendar.getInstance().timeInMillis}")
    }


    @Test
    fun `c) demo coroutine 1`() {
        runBlocking {
            launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
                println("Started at ${Calendar.getInstance().timeInMillis}")
                delay(1)
                assertThat(true).isTrue()
                println("Ended at ${Calendar.getInstance().timeInMillis}")
            }
        }
    }

    @Test
    fun `d) demo coroutine 2`() = runTest {
        println("Started at ${Calendar.getInstance().timeInMillis}")
        delay(1)
        assertThat(true).isTrue()
        println("Ended at ${Calendar.getInstance().timeInMillis}")
    }
}