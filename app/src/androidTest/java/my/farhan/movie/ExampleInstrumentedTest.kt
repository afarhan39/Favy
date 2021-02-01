package my.farhan.movie

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import my.farhan.favy.R
import my.farhan.favy.ui.list.MovieListActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("my.farhan.favy", appContext.packageName)
    }

    @get:Rule
    var activityRule = ActivityTestRule(MovieListActivity::class.java)

    @Test
    fun goToDetail() {
        // Click on button to open activity
        Espresso.onView(withId(R.id.rvMovies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2,click()))
    }
}