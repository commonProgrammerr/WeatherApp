package com.example.findinglogs.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.findinglogs.model.model.Weather
import com.example.findinglogs.model.model.WeatherInfo
import com.example.findinglogs.model.repo.IRepository
import com.example.findinglogs.model.repo.remote.api.WeatherCallback
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
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

    private fun createWeather(name: String): Weather {
        val info = WeatherInfo(300f, 298f, 295f, 305f, 1013f, 70f)
        val weather = Weather()
        weather.name = name
        weather.main = info
        return weather
    }

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

    @Test
    fun `refresh durante fetch pendente nao inicia nova requisicao`() {
        val mockRepo = mockk<IRepository>(relaxed = true)
        every { mockRepo.getLocalizations() } returns mapOf("1" to "-8.0,-34.9")

        // Capture callback to simulate slow network (don't respond yet)
        val callbackSlot = slot<WeatherCallback>()
        every { mockRepo.retrieveForecast(any(), capture(callbackSlot)) } returns Unit

        val viewModel = MainViewModel(
            RuntimeEnvironment.getApplication(),
            mockRepo
        )

        // First fetch is in progress (callback not invoked)
        // Call refresh while fetch is pending — should be debounced
        viewModel.refresh()

        // Complete the pending callback
        callbackSlot.captured.onSuccess(createWeather("Recife"))

        // retrieveForecast should have been called exactly once
        // (the refresh call should not trigger a second request)
        verify(exactly = 1) { mockRepo.retrieveForecast(any(), any()) }
    }
}
