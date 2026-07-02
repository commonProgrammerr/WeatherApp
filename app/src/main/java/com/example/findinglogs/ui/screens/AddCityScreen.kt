package com.example.findinglogs.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.findinglogs.model.model.GeoCity
import com.example.findinglogs.model.repo.IRepository
import com.example.findinglogs.model.repo.remote.api.GeoCallback

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCityScreen(
    repository: IRepository,
    onBack: () -> Unit,
    onCityAdded: () -> Unit = {}
) {
    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf<List<GeoCity>>(emptyList()) }
    var isSearching by remember { mutableStateOf(false) }
    var searched by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Adicionar Cidade") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults
                    .centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { q ->
                    query = q
                    searched = false
                },
                label = { Text("Digite o nome da cidade") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            androidx.compose.material3.Button(
                onClick = {
                    if (query.isBlank()) return@Button
                    isSearching = true
                    searched = true
                    results = emptyList()
                    repository.searchCities(query.trim(), object : GeoCallback {
                        override fun onSuccess(cities: List<GeoCity>?) {
                            results = cities ?: emptyList()
                            isSearching = false
                        }

                        override fun onFailure(msg: String?) {
                            results = emptyList()
                            isSearching = false
                        }
                    })
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = query.isNotBlank() && !isSearching
            ) {
                Text("Pesquisar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isSearching) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            } else if (searched && results.isEmpty()) {
                Text(
                    text = "Nenhuma cidade encontrada",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            } else {
                LazyColumn {
                    items(items = results, key = { it.lat.toString() + it.lon }) { city ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    repository.addCity(
                                        city.name ?: "",
                                        city.coordinates
                                    )
                                    onCityAdded()
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Text(
                                text = city.formattedName ?: "",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
