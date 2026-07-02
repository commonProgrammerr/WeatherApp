package com.example.findinglogs.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.findinglogs.model.repo.IRepository
import com.example.findinglogs.model.repo.remote.api.WeatherCallback
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class MainViewModelTest {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @Test
    fun `ao chamar refresh busca dados do repositorio`() {
        val mockRepo = mockk<IRepository>(relaxed = true)
        every { mockRepo.getLocalizations() } returns mapOf("1" to "-8.0,-34.9")

        val viewModel = MainViewModel(
            RuntimeEnvironment.getApplication(),
            mockRepo
        )
        viewModel.refresh()

        verify { mockRepo.retrieveForecast(any(), any()) }
    }
}
