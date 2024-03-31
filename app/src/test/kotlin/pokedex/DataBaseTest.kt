package pokedex

import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.skydoves.pokedex.core.database.PokedexDatabase
import com.skydoves.pokedex.core.database.PokemonDao
import com.skydoves.pokedex.core.database.PokemonInfoDao
import com.skydoves.pokedex.core.database.entitiy.PokemonEntity
import com.skydoves.pokedex.core.database.entitiy.PokemonInfoEntity
import com.skydoves.pokedex.core.model.Pokemon
import com.skydoves.pokedex.core.model.PokemonInfo
import com.skydoves.pokedex.core.model.PokemonInfo.Companion.MAX_ATTACK
import com.skydoves.pokedex.core.model.PokemonInfo.Companion.MAX_DEFENSE
import com.skydoves.pokedex.core.model.PokemonInfo.Companion.MAX_EXP
import com.skydoves.pokedex.core.model.PokemonInfo.Companion.MAX_HP
import com.skydoves.pokedex.core.model.PokemonInfo.Companion.MAX_SPEED
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class DataBaseTest {

  private lateinit var database: PokedexDatabase

  val hp = Random.nextInt(MAX_HP)
  val attack = Random.nextInt(MAX_ATTACK)
  val defense = Random.nextInt(MAX_DEFENSE)
  val speed = Random.nextInt(MAX_SPEED)
  val exp = Random.nextInt(MAX_EXP)

  @Before
  fun setup() {
    database = Room.inMemoryDatabaseBuilder(
      ApplicationProvider.getApplicationContext(),
      PokedexDatabase::class.java
    ).build()
  }

  @After
  fun tearDown() {
    database.close()
  }

  @Test
  fun testInsertPokemonInfo() = runBlocking {
    // Создаем тестовый объект PokemonInfoEntity
    val pokemonInfo = PokemonInfoEntity(
      id = 25, // Пример значения для поля id
      name = "Pikachu", // Пример значения для поля name
      height = 40, // Пример значения для поля height
      weight = 60, // Пример значения для поля weight
      experience = 112, // Пример значения для поля experience
      types = listOf( // Пример значений для списка types
        PokemonInfo.TypeResponse(
          slot = 1, // Пример значения для поля slot
          type = PokemonInfo.Type(
            name = "Electric" // Пример значения для поля name во вложенном классе Type
          )
        )
      ),
      hp = hp, // Задаем значение для параметра hp
      attack = attack, // Задаем значение для параметра attack
      defense = defense, // Задаем значение для параметра defense
      speed = speed, // Задаем значение для параметра speed
      exp = exp // Задаем значение для параметра exp
    )


    // Создаем мок для PokemonInfoDao
    val pokemonInfoDao = mockk<PokemonInfoDao>()

    // Устанавливаем ожидаемое поведение мока при вызове insertPokemonInfo
    coEvery { pokemonInfoDao.insertPokemonInfo(any()) } returns Unit

    // Создаем экземпляр PokedexDatabase с использованием мока для PokemonInfoDao
    val database = PokedexDatabase.createForTesting(mockk(), pokemonInfoDao)

    // Вызываем метод, который мы тестируем
    database.pokemonInfoDao().insertPokemonInfo(pokemonInfo)

    // Проверяем, что метод был вызван с правильными аргументами
    coVerify { pokemonInfoDao.insertPokemonInfo(pokemonInfo) }

    // Если метод возвращает что-то, вы также можете проверить результат, например:
    // assertEquals(expectedResult, actualResult)
  }
  @get:Rule
  var rule = MockKRule(true)

  private val pokemonDAO: PokemonDao = mockk()
  //private val pokemonInfoDao: PokemonInfoDao = mockk()
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

    assertEquals(expectedMessage.page, result[0].page)

  }


  /*@get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var repository: pokemonInfoDaoProvider
  private val pokemonDao: PokemonDao = mockk()

  private val pokemon = Pokemon(id = "001", name = "Bulbasaur")

  @Before
  fun setup() {
    repository = PokemonRepository(pokemonDao)
  }*/
  /*
    @Test
    fun insertPokemon_Success() {
      // Given
      every { runBlocking { pokemonDao.insertPokemon(pokemon) } } returns Unit

      // When
      runBlocking { repository.insertPokemon(pokemon) }

      // Then
      verify { runBlocking { pokemonDao.insertPokemon(pokemon) } }
    }

    @Test
    fun insertPokemon_ReturnsCorrectId() {
      // Given
      val expectedId = 1L
      every { runBlocking { pokemonDao.insertPokemon(pokemon) } } returns expectedId

      // When
      val result = runBlocking { repository.insertPokemon(pokemon) }

      // Then
      assertEquals(expectedId, result)
    }

    @Test
    fun insertPokemon_Error() {
      // Given
      val error = Throwable("Failed to insert Pokemon")
      every { runBlocking { pokemonDao.insertPokemon(pokemon) } } throws error

      // When
      var exception: Throwable? = null
      try {
        runBlocking { repository.insertPokemon(pokemon) }
      } catch (e: Throwable) {
        exception = e
      }

      // Then
      assertEquals(error, exception)
    }


    private lateinit var database: PokedexDatabase
    private lateinit var pokemonDao: PokemonDao
    private lateinit var pokemonInfoDao: PokemonInfoDao

    @Before
    fun setup() {
      // Создание моков для DAO
      pokemonDao = mockk()
      pokemonInfoDao = mockk()

      // Создание экземпляра базы данных с использованием моков для DAO
      database = PokedexDatabase.createForTesting(pokemonDao, pokemonInfoDao)
    }

    @Test
    fun testInsertPokemonInfo() {
      // Ваш тест для метода insertPokemonInfo
    }

    @Test
    fun testGetPokemonInfo() {
      // Ваш тест для метода getPokemonInfo
    }
  }*/
}

private infix fun Any.returns(mutableLiveData: MutableLiveData<Pokemon>) {

}
