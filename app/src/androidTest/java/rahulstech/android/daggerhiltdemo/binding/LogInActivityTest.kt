package rahulstech.android.daggerhiltdemo.binding

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import rahulstech.android.daggerhiltdemo.R

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LogInActivityTest {

    // @Rule runs something before or after test to initialize or release
    // here HiltAndroidRule are initialized as we need to load different component graph for test.
    // ActivityScenarioRule is used to initialize activity for test
    // NOTE: the hilt rule must be initialized before any other therefore order = 0 is used

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityRule = ActivityScenarioRule(LogInActivity::class.java)

    @Test
    fun testFakeAuthenticatorIsUsed() {
        // Type username and password
        onView(withId(R.id.input_username)).perform(typeText("rahul"), closeSoftKeyboard())
        onView(withId(R.id.input_password)).perform(typeText("1234"), closeSoftKeyboard())

        // Click login
        onView(withId(R.id.btn_login)).perform(click())

        // Verify fake token is shown
        onView(withId(R.id.output)).check(matches(withText("FAKE_TOKEN")))
    }
}
