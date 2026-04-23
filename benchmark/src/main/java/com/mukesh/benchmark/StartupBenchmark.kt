package com.mukesh.benchmark

import android.annotation.SuppressLint
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.PowerMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    /**
     * Measures startup performance including the login flow.
     */
    @SuppressLint("CheckResult")
    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun startupWithLogin() =
        benchmarkRule.measureRepeated(
            packageName = "com.mukesh.rickmortyfan",
            metrics =
                listOf(
                    StartupTimingMetric(),
                    FrameTimingMetric(),
                    PowerMetric(type = PowerMetric.Energy()),
                ),
            iterations = 1,
            startupMode = StartupMode.COLD,
        ) {
            pressHome()
            startActivityAndWait()

            // Wait for the email field to appear using its test tag
            device.wait(Until.hasObject(By.res("email_field")), 5000)
            // Find and populate the email field
            val emailField = device.findObject(By.res("email_field"))
            emailField.text = "test12@gmail.com"
            // Find and populate the password field
            val passwordField = device.findObject(By.res("password_field"))
            passwordField.text = "test12"
            device.waitForIdle()
            // Find and click the login button
            val loginButton = device.findObject(By.res("login_button"))
            loginButton.click()
            device.waitForIdle()
            // Wait for the content of the home screen to load (identified by "Characters")
            device.wait(Until.hasObject(By.text("Characters")), 5000)
        }

    /**
     * Measures startup performance when the user is already logged in.
     * The login is performed in the [setupBlock], which is not measured.
     */
    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun startupDirectToHome() =
        benchmarkRule.measureRepeated(
            packageName = "com.mukesh.rickmortyfan",
            metrics =
                listOf(
                    StartupTimingMetric(),
                    FrameTimingMetric(),
                ),
            iterations = 5,
            startupMode = StartupMode.COLD,
            setupBlock = {
                // Perform login in the setup block (unmeasured)
                pressHome()
                startActivityAndWait()

                val emailField = device.findObject(By.res("email_field"))
                if (emailField != null) {
                    emailField.text = "test12@gmail.com"
                    device.findObject(By.res("password_field")).text = "test12"
                    device.findObject(By.res("login_button")).click()
                    device.wait(Until.hasObject(By.text("Characters")), 5000)
                }
            },
        ) {
            pressHome()
            startActivityAndWait()
            // We expect to land directly on the "Characters" screen
            device.wait(Until.hasObject(By.text("Characters")), 5000)
        }
}
