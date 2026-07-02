package com.example.findinglogs.model.repo

import com.example.findinglogs.model.repo.local.SharedPrefManager
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
        val localizations = repository.getLocalizations()

        val values = localizations.values.toList()
        val uniqueValues = values.toSet()

        assertThat(values).hasSize(uniqueValues.size)
    }

    @Test
    fun `getLocalizations carrega dados do SharedPreferences apos seed`() {
        val app = RuntimeEnvironment.getApplication()

        // First instance seeds the data into SharedPreferences
        Repository(app)

        // Manually persist a new city via SharedPrefManager
        val prefs = SharedPrefManager.getInstance(app)
        prefs.writeString("loc_6", "-15.7934,-47.8822") // Brasília
        prefs.writeString("loc_count", "6")

        // Second instance reads from SharedPreferences instead of hardcoding
        val repo2 = Repository(app)
        val localizations = repo2.getLocalizations()

        assertThat(localizations).hasSize(6)
        assertThat(localizations).containsEntry("6", "-15.7934,-47.8822")
    }

    @Test
    fun `getLocalizations filtra coordenadas duplicadas`() {
        val app = RuntimeEnvironment.getApplication()

        // Seed data
        Repository(app)

        // Add a city with coordinates that already exist
        val prefs = SharedPrefManager.getInstance(app)
        prefs.writeString("loc_6", "-8.05428,-34.8813") // same as Recife (loc_1)
        prefs.writeString("loc_count", "6")

        val repo = Repository(app)
        val localizations = repo.getLocalizations()

        // Should have deduplicated the repeated coordinate
        assertThat(localizations).hasSize(5)
    }

    @Test
    fun `addCity persiste nova cidade no SharedPreferences`() {
        val app = RuntimeEnvironment.getApplication()
        val repository = Repository(app)

        repository.addCity("-15.7934,-47.8822") // Brasília
        val localizations = repository.getLocalizations()

        assertThat(localizations).hasSize(6)
        assertThat(localizations.values).contains("-15.7934,-47.8822")
    }
}
