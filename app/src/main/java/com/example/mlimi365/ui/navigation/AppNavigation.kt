package com.example.mlimi365.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mlimi365.model.FirebaseAuthRepository
import com.example.mlimi365.model.FirebaseReminderRepository
import com.example.mlimi365.ui.components.ConnectivityBanner
import com.example.mlimi365.ui.screens.CalendarScreen
import com.example.mlimi365.ui.screens.CropDetailScreen
import com.example.mlimi365.ui.screens.HomeScreen
import com.example.mlimi365.ui.screens.LoginScreen
import com.example.mlimi365.ui.screens.OnboardingScreen
import com.example.mlimi365.ui.screens.RegisterScreen
import com.example.mlimi365.ui.screens.RemindersScreen
import com.example.mlimi365.ui.screens.SearchScreen
import com.example.mlimi365.ui.screens.SplashScreen
import com.example.mlimi365.ui.screens.UserAccountScreen
import com.example.mlimi365.utils.rememberConnectivityState
import com.example.mlimi365.worker.WorkManagerScheduler

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current
    val isOnline by rememberConnectivityState(context)

    Column(modifier = Modifier.fillMaxSize()) {
        ConnectivityBanner(
            isOnline = isOnline,
            onBackOnline = {
                // When internet connection is restored, automatically sync data
                try {
                    FirebaseReminderRepository.syncAllCropsToRemote()
                    FirebaseReminderRepository.syncAllMonthsToRemote()
                    WorkManagerScheduler.scheduleFirebaseSyncWork(context)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        )

        Box(modifier = Modifier.weight(1f)) {
            NavHost(
                navController = navController,
                startDestination = "splash",
            ) {

                // SPLASH SCREEN
                composable("splash") {
                    SplashScreen {
                        val destination = if (FirebaseAuthRepository.isUserLoggedIn(context)) "home" else "onboarding"
                        navController.navigate(destination) {
                            popUpTo("splash") {
                                inclusive = true
                            }
                        }
                    }
                }

                // ONBOARDING SCREEN
                composable("onboarding") {
                    OnboardingScreen(
                        onNext = {
                            val destination = if (FirebaseAuthRepository.isUserLoggedIn(context)) "home" else "login"
                            navController.navigate(destination) {
                                popUpTo("onboarding") {
                                    inclusive = true
                                }
                            }
                        },
                    ) {
                        val destination = if (FirebaseAuthRepository.isUserLoggedIn(context)) "home" else "login"
                        navController.navigate(destination) {
                            popUpTo("onboarding") {
                                inclusive = true
                            }
                        }
                    }
                }

                // LOGIN SCREEN
                composable("login") {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onNavigateToRegister = {
                            navController.navigate("register")
                        }
                    )
                }

                // REGISTER SCREEN
                composable("register") {
                    RegisterScreen(
                        onRegisterSuccess = {
                            navController.navigate("home") {
                                popUpTo("register") { inclusive = true }
                            }
                        },
                        onNavigateToLogin = {
                            navController.popBackStack()
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
                        onAccountClick = { navController.navigate("account") },
                        onCropClick = { cropName ->
                            navController.navigate("crop_detail/$cropName")
                        }
                    )
                }

                // USER ACCOUNT SCREEN
                composable("account") {
                    UserAccountScreen(
                        onBack = { navController.popBackStack() },
                        onLogout = {
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
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
                        },
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
    }
}
