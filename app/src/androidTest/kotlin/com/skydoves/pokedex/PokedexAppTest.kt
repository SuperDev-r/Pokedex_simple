package com.skydoves.pokedex

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.skydoves.pokedex.ui.main.MainActivity
import com.skydoves.pokedex.ui.main.PokemonAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Description
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class PokedexAppTest {

  @get:Rule(order = 1)
  var activityScenario = ActivityScenarioRule(MainActivity::class.java)

  @get:Rule(order = 0)
  var hiltAndroidRule = HiltAndroidRule(this)

  @Test
  fun chooseItemInList() {
    onView(
      withId(R.id.progressbar_main)
    ).check(
      matches(
        isCompletelyDisplayed()
      )
    )
    Thread.sleep(3000)
    onView(
      withText("charmander")
    ).perform(
      click()
    )
    Thread.sleep(5000)
    onView(withId(R.id.header)).check(
      matches(isCompletelyDisplayed())
    )
    onView(withId(R.id.name)).check(
      matches(allOf(isCompletelyDisplayed(), withText("charmander")))
    )
    onView(withId(R.id.weight)).check(
      matches(allOf(isCompletelyDisplayed(), withText("8.5 KG")))
    )
    onView(withId(R.id.height)).check(
      matches(allOf(isCompletelyDisplayed(), withText("0.6 M")))
    )
    onView(withId(R.id.weight_title)).check(
      matches(allOf(isCompletelyDisplayed(), withText("Weight")))
    )
    onView(withId(R.id.height_title)).check(
      matches(allOf(isCompletelyDisplayed(), withText("Height")))
    )
  }

  @Test
  fun scrollItemInList() {
    val pokemonName = "weedle"
    val matcher =
      object :
        BoundedMatcher<ViewHolder, PokemonAdapter.PokemonViewHolder>(PokemonAdapter.PokemonViewHolder::class.java) {

        override fun matchesSafely(item: PokemonAdapter.PokemonViewHolder?): Boolean {
          return item?.binding?.pokemon?.name == pokemonName
        }

        override fun describeTo(description: Description?) {
        }
      }
    onView(
      withId(R.id.progressbar_main)
    ).check(
      matches(
        isCompletelyDisplayed()
      )
    )
    Thread.sleep(3000)
    onView(
      withId(R.id.recyclerView)
    ).perform(
      actionOnHolderItem(matcher, doubleClick()),
    )
    Thread.sleep(5000)
    onView(withId(R.id.header)).check(
      matches(isCompletelyDisplayed())
    )
    onView(withId(R.id.name)).check(
      matches(allOf(isCompletelyDisplayed(), withText(pokemonName)))
    )
    onView(withId(R.id.weight)).check(
      matches(allOf(isCompletelyDisplayed(), withText("3.2 KG")))
    )
    onView(withId(R.id.height)).check(
      matches(allOf(isCompletelyDisplayed(), withText("0.3 M")))
    )
    onView(withId(R.id.weight_title)).check(
      matches(allOf(isCompletelyDisplayed(), withText("Weight")))
    )
    onView(withId(R.id.height_title)).check(
      matches(allOf(isCompletelyDisplayed(), withText("Height")))
    )
  }

  @Test
  fun reChooseItemInList() {
    var pokemonName = "weedle"
    val matcher =
      object :
        BoundedMatcher<ViewHolder, PokemonAdapter.PokemonViewHolder>(PokemonAdapter.PokemonViewHolder::class.java) {

        override fun matchesSafely(item: PokemonAdapter.PokemonViewHolder?): Boolean {
          return item?.binding?.pokemon?.name == pokemonName
        }

        override fun describeTo(description: Description?) {
        }
      }
    onView(
      withId(R.id.progressbar_main)
    ).check(
      matches(
        isCompletelyDisplayed()
      )
    )
    Thread.sleep(3000)
    onView(
      withId(R.id.recyclerView)
    ).perform(
      actionOnHolderItem(matcher, doubleClick()),
    )
    Thread.sleep(5000)
    onView(withId(R.id.header)).check(
      matches(isCompletelyDisplayed())
    )
    onView(withId(R.id.name)).check(
      matches(allOf(isCompletelyDisplayed(), withText(pokemonName)))
    )
    onView(withId(R.id.weight)).check(
      matches(allOf(isCompletelyDisplayed(), withText("3.2 KG")))
    )
    onView(withId(R.id.height)).check(
      matches(allOf(isCompletelyDisplayed(), withText("0.3 M")))
    )
    onView(withId(R.id.weight_title)).check(
      matches(allOf(isCompletelyDisplayed(), withText("Weight")))
    )
    onView(withId(R.id.height_title)).check(
      matches(allOf(isCompletelyDisplayed(), withText("Height")))
    )

    pokemonName = "pidgey"
    onView(
      withId(R.id.arrow)
    ).check(
      matches(
        isCompletelyDisplayed()
      )
    ).perform(
      click()
    )
    Thread.sleep(3000)
    onView(
      withId(R.id.recyclerView)
    ).perform(
      actionOnHolderItem(matcher, doubleClick()),
    )

    onView(withId(R.id.name)).check(
      matches(allOf(isCompletelyDisplayed(), withText(pokemonName)))
    )
    onView(withId(R.id.weight)).check(
      matches(allOf(isCompletelyDisplayed(), withText("1.8 KG")))
    )
    onView(withId(R.id.height)).check(
      matches(allOf(isCompletelyDisplayed(), withText("0.3 M")))
    )
    onView(withId(R.id.weight_title)).check(
      matches(allOf(isCompletelyDisplayed(), withText("Weight")))
    )
    onView(withId(R.id.height_title)).check(
      matches(allOf(isCompletelyDisplayed(), withText("Height")))
    )
  }

  @Test
  fun clickOnToolbarNotOpenCard() {
    onView(
      withId(R.id.progressbar_main)
    ).check(
      matches(
        isCompletelyDisplayed()
      )
    )
    Thread.sleep(3000)
    onView(
      withId(R.id.main_toolbar)
    ).perform(
      click()
    )
    Thread.sleep(5000)
    onView(
      withText("charmander")
    ).check(matches(isDisplayed()))
  }
}
