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
    fun testThatTextCanBeTypedIntoTheTextField() {
        // The app has been launched but we need to wait a bit for the app's UI composables to be displayed.
        // This is because some android devices are slow and when they launch an app (especially for the first time),
        // they will show a default splash screen (or a blank white screen) briefly before showing the UI of the app.

        // This splash/blank screen will cause problems for your UI tests because UIAutomator begins testing
        // immediately the first UI of the app appears. Now, since it is the splash/blank screen that appears first,
        // UIAutomator will run all your tests on that splash/blank screen.

        // This will cause all your tests will fail before the actual UI composables have a chance to be drawn on the screen.

        // So, the first thing to do is wait until we are sure that the composable we want to test for is visible:
        device.wait(Until.findObject(By.res("searchTextField")), 5_000)

        // Now, we are free to find the text field:
        val textField = device.findObject(By.res("feed"))
        // and type on it:
        textField.text = "Jeff Emuveyan"
    }
}