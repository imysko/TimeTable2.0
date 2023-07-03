package com.istu.schedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.istu.schedule.data.preference.LanguagesPreference
import com.istu.schedule.data.preference.OnBoardingState
import com.istu.schedule.data.preference.SettingsProvider
import com.istu.schedule.ui.components.navigation.NavGraph
import com.istu.schedule.ui.theme.AppTheme
import com.istu.schedule.util.isFirstLaunch
import com.istu.schedule.util.languages
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        LanguagesPreference.fromValue(languages).let {
            if (it == LanguagesPreference.UseDeviceLanguages) return@let
            it.setLocale(this)
        }

        val startDestination = OnBoardingState.getStartDestination(isFirstLaunch)

        setContentView(
            ComposeView(this).apply {
                consumeWindowInsets = false
                setContent {
                    SettingsProvider {
                        AppTheme {
                            Surface {
                                val navController = rememberAnimatedNavController()
                                NavGraph(
                                    navController = navController,
                                    startDestination = startDestination
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}
