package com.example.findinglogs.util

import com.example.findinglogs.model.util.Utils
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class UtilsTest {

    @Test
    fun `converte 300 Kelvin para 26_8 graus Celsius`() {
        val result = Utils.getCelsiusTemperatureFromKevin(300.0f)
        assertThat(result).isEqualTo("26,8ºC")
    }

    @Test
    fun `converte ponto de gelo 273_15 Kelvin para 0 graus`() {
        val result = Utils.getCelsiusTemperatureFromKevin(273.15f)
        assertThat(result).isEqualTo("0ºC")
    }

    @Test
    fun `converte zero absoluto 0 Kelvin para -273_1 graus`() {
        val result = Utils.getCelsiusTemperatureFromKevin(0f)
        assertThat(result).isEqualTo("-273,1ºC")
    }
}
