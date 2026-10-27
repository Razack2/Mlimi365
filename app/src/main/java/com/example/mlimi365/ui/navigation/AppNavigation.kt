package com.example.mlimi365.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mlimi365.ui.screens.CalendarScreen
import com.example.mlimi365.ui.screens.CropDetailScreen
import com.example.mlimi365.ui.screens.HomeScreen
import com.example.mlimi365.ui.screens.OnboardingScreen
import com.example.mlimi365.ui.screens.RemindersScreen
import com.example.mlimi365.ui.screens.SearchScreen
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
            HomeScreen(
                onCalendarClick = { navController.navigate("calendar") },
                onSearchClick = { navController.navigate("search") },
                onReminderClick = { navController.navigate("reminders") },
                onCropGuidesClick = { navController.navigate("search") },
                onCropClick = { cropName ->
                    navController.navigate("crop_detail/$cropName")
                }
            )
        }

        // CALENDAR SCREEN (Jan - Dec farming periods)
        composable("calendar") {
            CalendarScreen(
                onHomeClick = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onSearchClick = { navController.navigate("search") },
                onReminderClick = { navController.navigate("reminders") },
                onCropClick = { cropName ->
                    navController.navigate("crop_detail/$cropName")
                }
            )
        }

        // SEARCH SCREEN (Search crops & guides offline)
        composable("search") {
            SearchScreen(
                onBack = { navController.popBackStack() },
                onHomeClick = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onCalendarClick = { navController.navigate("calendar") },
                onReminderClick = { navController.navigate("reminders") },
                onCropClick = { cropName ->
                    navController.navigate("crop_detail/$cropName")
                }
            )
        }

        // REMINDERS SCREEN (Seasonal activities)
        composable("reminders") {
            RemindersScreen(
                onHomeClick = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onCalendarClick = { navController.navigate("calendar") },
                onSearchClick = { navController.navigate("search") }
            )
        }

        // CROP DETAIL SCREEN (Planting, spacing, fertilizer, weeding, harvesting)
        composable(
            route = "crop_detail/{cropName}",
            arguments = listOf(navArgument("cropName") { type = NavType.StringType })
        ) { backStackEntry ->
            val cropName = backStackEntry.arguments?.getString("cropName") ?: "Maize"
            CropDetailScreen(
                cropName = cropName,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
