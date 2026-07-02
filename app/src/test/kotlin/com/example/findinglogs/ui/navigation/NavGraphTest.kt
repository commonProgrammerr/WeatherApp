package com.example.findinglogs.ui.navigation

import com.google.common.truth.Truth.assertThat
import org.junit.Test

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
