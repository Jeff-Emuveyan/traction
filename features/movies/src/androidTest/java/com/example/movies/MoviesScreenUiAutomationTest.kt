package com.example.movies

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val TIME_OUT = 5000L

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
/***
 * NOTE: In order to run this ui automation test, ensure that the app is installed on the device and the
 * logo of the app is visible on the screen.
 */
class MoviesScreenUiAutomationTest {

    private lateinit var device: UiDevice

    @Before
    fun startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        val traction: UiObject2 = device.findObject(By.text("traction"))
        // Perform a click and wait until the app is opened.
        val opened: Boolean = traction.clickAndWait(Until.newWindow(), TIME_OUT)
    }

    @Test
    fun testThatTheComposableThatShowsMoviesIsVisible() {

        // wait until we can see the composable for displaying list of movies:
        val lazyColumnComposable = device.findObject(By.scrollable(true))
        // verify that it exists:
        Assert.assertNotNull(lazyColumnComposable)
    }
}