package com.skydoves.pokedex.core.test

import com.skydoves.pokedex.core.model.Pokemon
import com.skydoves.pokedex.core.model.PokemonInfo
import kotlin.random.Random

object MockPokemonInfo {
  private val hp = Random.nextInt(PokemonInfo.MAX_HP)
  private val attack = Random.nextInt(PokemonInfo.MAX_ATTACK)
  private val defense = Random.nextInt(PokemonInfo.MAX_DEFENSE)
  private val speed = Random.nextInt(PokemonInfo.MAX_SPEED)
  private val exp = Random.nextInt(PokemonInfo.MAX_EXP)

  val mockPokemon = listOf(
    Pokemon(
      page = 0,
      name = "valera",
      url = "https://pokeapi.co/api/v2/pokemon/1/",
    ), Pokemon(
      page = 0,
      name = "lusya",
      url = "https://pokeapi.co/api/v2/pokemon/2/",
    )
  )

//  fun mockPokemonList() = mockPokemon()

//  fun mockPokemonInfo() = listOf(mockPokemonEntityInfo())

  val mockPokemonEntityInfo = listOf(
    PokemonInfo(
      id = 77,
      name = "Pikachu",
      height = 40,
      weight = 60,
      experience = 112,
      types = listOf(
        PokemonInfo.TypeResponse(
          slot = 1,
          type = PokemonInfo.Type(
            name = "Electric"
          )
        )
      ),
      hp = hp,
      attack = attack,
      defense = defense,
      speed = speed,
      exp = exp
    ), PokemonInfo(
      id = 11,
      name = "Makaroshka",
      height = 120,
      weight = 10,
      experience = 123,
      types = listOf(
        PokemonInfo.TypeResponse(
          slot = 2,
          type = PokemonInfo.Type(
            name = "Eda"
          )
        )
      ),
      hp = hp,
      attack = attack,
      defense = defense,
      speed = speed,
      exp = exp
    )
  )
}
