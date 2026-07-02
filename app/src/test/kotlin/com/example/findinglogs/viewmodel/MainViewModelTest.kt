package com.example.findinglogs.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.findinglogs.model.model.Weather
import com.example.findinglogs.model.model.WeatherInfo
import com.example.findinglogs.model.repo.IRepository
import com.example.findinglogs.model.repo.remote.api.WeatherCallback
import com.google.common.truth.Truth.assertThat
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
    fun `refresh forca nova requisicao mesmo durante fetch pendente`() {
        val mockRepo = mockk<IRepository>(relaxed = true)
        every { mockRepo.getLocalizations() } returns mapOf("1" to "-8.0,-34.9")

        val callbackSlots = mutableListOf<WeatherCallback>()
        every { mockRepo.retrieveForecast(any(), capture(callbackSlots)) } returns Unit

        val viewModel = MainViewModel(
            RuntimeEnvironment.getApplication(),
            mockRepo
        )

        // refresh() deve forcar uma nova fetch mesmo se uma estiver pendente
        viewModel.refresh()

        verify(exactly = 2) { mockRepo.retrieveForecast(any(), any()) }
    }

    @Test
    fun `estado inicial do UiState eh Loading`() {
        val mockRepo = mockk<IRepository>(relaxed = true)
        every { mockRepo.getLocalizations() } returns mapOf("1" to "-8.0,-34.9")

        val callbackSlot = slot<WeatherCallback>()
        every { mockRepo.retrieveForecast(any(), capture(callbackSlot)) } returns Unit

        val viewModel = MainViewModel(
            RuntimeEnvironment.getApplication(),
            mockRepo
        )

        val state = viewModel.getUiState().value
        assertThat(state).isInstanceOf(UiState.Loading::class.java)
    }

    @Test
    fun `UiState transita para Success apos dados carregados`() {
        val mockRepo = mockk<IRepository>(relaxed = true)
        every { mockRepo.getLocalizations() } returns mapOf("1" to "-8.0,-34.9")

        val callbackSlot = slot<WeatherCallback>()
        every { mockRepo.retrieveForecast(any(), capture(callbackSlot)) } returns Unit

        val viewModel = MainViewModel(
            RuntimeEnvironment.getApplication(),
            mockRepo
        )

        callbackSlot.captured.onSuccess(createWeather("Recife"))

        val state = viewModel.getUiState().value
        assertThat(state).isInstanceOf(UiState.Success::class.java)
        assertThat((state as UiState.Success).data).hasSize(1)
    }

    @Test
    fun `UiState transita para Error quando fetch falha`() {
        val mockRepo = mockk<IRepository>(relaxed = true)
        every { mockRepo.getLocalizations() } returns mapOf("1" to "-8.0,-34.9")

        val callbackSlot = slot<WeatherCallback>()
        every { mockRepo.retrieveForecast(any(), capture(callbackSlot)) } returns Unit

        val viewModel = MainViewModel(
            RuntimeEnvironment.getApplication(),
            mockRepo
        )

        callbackSlot.captured.onFailure("Network error")

        val state = viewModel.getUiState().value
        assertThat(state).isInstanceOf(UiState.Error::class.java)
    }
}
