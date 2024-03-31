package pokedex

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.pokedex.core.database.PokemonDao
import com.skydoves.pokedex.core.database.entitiy.mapper.asEntity
import com.skydoves.pokedex.core.network.service.PokedexClient
import com.skydoves.pokedex.core.network.service.PokedexService
import com.skydoves.pokedex.core.repository.MainRepository
import com.skydoves.pokedex.core.repository.MainRepositoryImpl
import com.skydoves.pokedex.core.test.MainCoroutinesRule
import com.skydoves.pokedex.core.test.MockPokemonInfo
import com.skydoves.pokedex.ui.main.MainViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewModelTest {
  private lateinit var viewModel: MainViewModel
  private lateinit var mainRepository: MainRepository
  private val pokedexService: PokedexService = mock()
  private val pokdexClient: PokedexClient = PokedexClient(pokedexService)
  private val pokemonDao: PokemonDao = mock()

  @get:Rule
  val coroutinesRule = MainCoroutinesRule()

  @Before
  fun setup() {
    mainRepository = MainRepositoryImpl(pokdexClient, pokemonDao, coroutinesRule.testDispatcher)
    viewModel = MainViewModel(mainRepository)
  }

  @Test
  fun getPokemonTest() = runTest {
    val mockData = MockPokemonInfo.mockPokemon
    whenever(pokemonDao.getPokemonList(page_ = 0)).thenReturn(mockData.asEntity())
    whenever(pokemonDao.getAllPokemonList(page_ = 0)).thenReturn(mockData.asEntity())

    mainRepository.fetchPokemonList(
      page = 0,
      onStart = {},
      onComplete = {},
      onError = {},
    ).test {
      val item = awaitItem()
      assertEquals(item[0].page, 0)
      assertEquals(item[0].name, "valera")
      assertEquals(item, MockPokemonInfo.mockPokemon)
      awaitComplete()
    }

    verify(pokemonDao, atLeastOnce()).getPokemonList(page_ = 0)
  }

  @Test
  fun checkPokemonListTest() = runTest {
    val mockData = MockPokemonInfo.mockPokemon
    whenever(pokemonDao.getPokemonList(page_ = 0)).thenReturn(mockData.asEntity())
    whenever(pokemonDao.getAllPokemonList(page_ = 0)).thenReturn(mockData.asEntity())

    mainRepository.fetchPokemonList(
      page = 0,
      onStart = {},
      onComplete = {},
      onError = {},
    ).test {
      val item = awaitItem()
      assertEquals(item[0].page, 0)
      assertEquals(item[0].page, 0)
      assertEquals(item[0].name, "valera")
      assertEquals(item[1].name, "lusya")
      assertEquals(item[0].url, "https://pokeapi.co/api/v2/pokemon/1/")
      assertEquals(item[1].url, "https://pokeapi.co/api/v2/pokemon/2/")
      assertEquals(item, MockPokemonInfo.mockPokemon)
      awaitComplete()
    }

    verify(pokemonDao, atLeastOnce()).getPokemonList(page_ = 0)
  }

  @Test
  fun checkPokemonListSizeTest() = runTest {
    val mockData = MockPokemonInfo.mockPokemon
    whenever(pokemonDao.getPokemonList(page_ = 0)).thenReturn(mockData.asEntity())
    whenever(pokemonDao.getAllPokemonList(page_ = 0)).thenReturn(mockData.asEntity())

    mainRepository.fetchPokemonList(
      page = 0,
      onStart = {},
      onComplete = {},
      onError = {},
    ).test {
      val item = awaitItem()
      assertEquals(item.size, 2)
      assertEquals(item, MockPokemonInfo.mockPokemon)
      awaitComplete()
    }

    verify(pokemonDao, atLeastOnce()).getPokemonList(page_ = 0)
  }
}