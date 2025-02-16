package com.compose.pookiedex.data

data class PokemonResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlot>,
    val sprites: Sprites,
    val stats: List<Stat>
)

data class TypeSlot(
    val slot: Int,
    val type: Type
)

data class Type(
    val name: String
)

data class Sprites(
    val front_default: String
)

data class Stat(
    val base_stat: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String
)
