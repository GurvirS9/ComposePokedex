package com.compose.pookiedex.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.compose.pookiedex.data.PokemonResponse

@Composable
fun PokeScreen(
    modifier: Modifier = Modifier,
    viewModel: PokeViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Enter Pokemon name or ID") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.searchPokemon(searchQuery) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            viewModel.isLoading.value -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            viewModel.error.value != null -> {
                Text(
                    text = viewModel.error.value ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            viewModel.pokemon.value != null -> {
                PokemonDetails(viewModel.pokemon.value!!)
            }
        }
    }
}

@Composable
fun PokemonDetails(pokemon: PokemonResponse) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            AsyncImage(
                model = pokemon.sprites.front_default,
                contentDescription = pokemon.name,
                modifier = Modifier.size(250.dp)
            )

            Text(
                text = "#${pokemon.id} - ${pokemon.name.uppercase()}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                pokemon.types.forEach { typeSlot ->
                    Card(
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Text(
                            text = typeSlot.type.name.uppercase(),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Height: ${pokemon.height/10.0}m")
                    Text("Weight: ${pokemon.weight/10.0}kg")

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Base Stats:", fontWeight = FontWeight.Bold)
                    pokemon.stats.take(3).forEach { stat ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(stat.stat.name.uppercase())
                            Text(stat.base_stat.toString())
                        }
                    }
                }
            }
        }
    }
}