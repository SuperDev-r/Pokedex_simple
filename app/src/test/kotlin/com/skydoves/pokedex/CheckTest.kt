package com.skydoves.pokedex

import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.skydoves.pokedex.core.database.PokemonDao
import com.skydoves.pokedex.core.model.Pokemon
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckTest {

  @get:Rule
  var rule = MockKRule(true)

  private val pokemonDAO: PokemonDao = mockk()

  @Test
  fun getById()= runBlockingTest {

    /*val expectedMessage = Pokemon(
      page = 0,
      name = "111",
      url = "\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/\" +\n" +
        "      \"pokemon/other/official-artwork/1.png\""
    )

    coEvery { pokemonDAO.getPokemonList(0 ) } returns List<PokemonEntity>(init = 1, size = 1)

    val result = pokemonDAO.getById(expectedMessage.id)*/

//    assertEquals(expectedMessage.id, result.value?.id)

    val expectedMessage = Pokemon(
      page = 0,
      name = "111",
      url = "https://pokeapi.co/api/v2/pokemon/1/"
    )

    coEvery { pokemonDAO.getPokemonList(0) } returns MutableLiveData(expectedMessage)

    val result = pokemonDAO.getPokemonList(expectedMessage.page)

    Assert.assertEquals(expectedMessage.page, result[0].page)

  }
}

private infix fun Any.returns(mutableLiveData: MutableLiveData<Pokemon>) {

}