package pokedex

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.pokedex.core.database.PokemonInfoDao
import com.skydoves.pokedex.core.database.entitiy.mapper.asEntity
import com.skydoves.pokedex.core.network.service.PokedexClient
import com.skydoves.pokedex.core.network.service.PokedexService
import com.skydoves.pokedex.core.repository.DetailRepository
import com.skydoves.pokedex.core.repository.DetailRepositoryImpl
import com.skydoves.pokedex.core.test.MainCoroutinesRule
import com.skydoves.pokedex.core.test.MockPokemonInfo
import com.skydoves.pokedex.ui.details.DetailViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class Test {
  private lateinit var viewModel: DetailViewModel
  private lateinit var detailRepository: DetailRepository
  private val pokedexService: PokedexService = mock()
  private val pokedexClient: PokedexClient = PokedexClient(pokedexService)
  private val pokemonInfoDao: PokemonInfoDao = mock()

  @get:Rule
  val coroutinesRule = MainCoroutinesRule()

  @Before
  fun setUp() {
    detailRepository =
      DetailRepositoryImpl(pokedexClient, pokemonInfoDao, coroutinesRule.testDispatcher)
    viewModel = DetailViewModel(detailRepository, "Pikachu")
  }


  @Test
  fun getPokemonEntityInfo() = runTest {
    val mockData = MockPokemonInfo.mockPokemonEntityInfo.first()
    whenever(pokemonInfoDao.getPokemonInfo(name_ = "Pikachu")).thenReturn(mockData.asEntity())

    detailRepository.fetchPokemonInfo(
      name = "Pikachu",
      onComplete = { },
      onError = { },
    ).test {
      val item = awaitItem()
      assertEquals(item.id, mockData.id)
      assertEquals(item.name, mockData.name)
      assertEquals(item.height, mockData.height)
      assertEquals(item.weight, mockData.weight)
      assertEquals(item.speed, mockData.speed)
      assertEquals(item, mockData)
      awaitComplete()
    }

    verify(pokemonInfoDao, atLeastOnce()).getPokemonInfo(name_ = "Pikachu")
  }

  @Test
  fun getPokemonEntityInfoList() = runTest {
    val mockData_1 = MockPokemonInfo.mockPokemonEntityInfo.first()
    val mockData_2 = MockPokemonInfo.mockPokemonEntityInfo.last()
    whenever(pokemonInfoDao.getPokemonInfo(name_ = "Pikachu")).thenReturn(mockData_1.asEntity())
    whenever(pokemonInfoDao.getPokemonInfo(name_ = "Makaroshka")).thenReturn(mockData_2.asEntity())

    detailRepository.fetchPokemonInfo(
      name = "Pikachu",
      onComplete = { },
      onError = { },
    ).test {
      val item = awaitItem()
      assertEquals(item.id, mockData_1.id)
      assertEquals(item.name, mockData_1.name)
      assertEquals(item.height, mockData_1.height)
      assertEquals(item.weight, mockData_1.weight)
      assertEquals(item.speed, mockData_1.speed)
      assertEquals(item, mockData_1)
      awaitComplete()
    }

    detailRepository.fetchPokemonInfo(
      name = "Makaroshka",
      onComplete = { },
      onError = { },
    ).test {
      val item = awaitItem()
      assertEquals(item.id, mockData_2.id)
      assertEquals(item.name, mockData_2.name)
      assertEquals(item.height, mockData_2.height)
      assertEquals(item.weight, mockData_2.weight)
      assertEquals(item.speed, mockData_2.speed)
      assertEquals(item, mockData_2)
      awaitComplete()
    }

    verify(pokemonInfoDao, atLeastOnce()).getPokemonInfo(name_ = "Makaroshka")
  }
}