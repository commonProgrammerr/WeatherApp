package com.example.findinglogs.model.repo

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class RepositoryTest {

    @Test
    fun `getLocalizations nao contem coordenadas duplicadas`() {
        val repository = Repository(RuntimeEnvironment.getApplication())
        val localizations = repository.localizations

        val values = localizations.values.toList()
        val uniqueValues = values.toSet()

        assertThat(values).hasSize(uniqueValues.size)
    }
}
