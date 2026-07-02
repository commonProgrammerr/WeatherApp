package com.example.findinglogs.ui.navigation

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class NavGraphTest {

    @Test
    fun `detail route recebe nome da cidade como argumento`() {
        val route = Screen.Detail.createRoute("Recife")
        assertThat(route).isEqualTo("detail/Recife")
    }

    @Test
    fun `home route nao tem parametros`() {
        val route = Screen.Home.route
        assertThat(route).isEqualTo("home")
    }
}
