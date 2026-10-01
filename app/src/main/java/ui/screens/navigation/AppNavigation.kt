package com.example.mlimi365.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mlimi365.ui.screens.HomeScreen
import com.example.mlimi365.ui.screens.OnboardingScreen
import com.example.mlimi365.ui.screens.SplashScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        // SPLASH SCREEN
        composable("splash") {
            SplashScreen(
                onFinished = {
                    navController.navigate("onboarding") {
                        popUpTo("splash") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // ONBOARDING SCREEN
        composable("onboarding") {
            OnboardingScreen(
                onNext = {
                    navController.navigate("home") {
                        popUpTo("onboarding") {
                            inclusive = true
                        }
                    }
                },
                onSkip = {
                    navController.navigate("home") {
                        popUpTo("onboarding") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // HOME SCREEN
        composable("home") {
            HomeScreen()
        }
    }
}