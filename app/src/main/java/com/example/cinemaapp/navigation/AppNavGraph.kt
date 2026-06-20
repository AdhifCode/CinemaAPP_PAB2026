package com.example.cinemaapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cinemaapp.ui.screens.home.HomeScreen
import com.example.cinemaapp.ui.screens.login.LoginScreen
import com.example.cinemaapp.ui.screens.movies.MoviesScreen
import com.example.cinemaapp.ui.screens.payment.PaymentScreen
import com.example.cinemaapp.ui.screens.profile.ProfileScreen
import com.example.cinemaapp.ui.screens.seats.SeatSelectionScreen
import com.example.cinemaapp.ui.screens.signup.SignupScreen

object Routes {
    const val LOGIN   = "login"
    const val SIGNUP  = "signup"
    const val HOME    = "home"
    const val MOVIES  = "movies"
    const val SEATS   = "seats/{movieTitle}"
    const val PROFILE = "profile"
    const val PAYMENT = "payment/{seatCount}/{totalPrice}"
}

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController    = navController,
        startDestination = Routes.LOGIN,
        modifier         = modifier
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Routes.SIGNUP)
                }
            )
        }

        composable(Routes.SIGNUP) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToMovies = {
                    navController.navigate(Routes.MOVIES)
                }
            )
        }

        composable(Routes.MOVIES) {
            MoviesScreen(
                onMovieClick = { movieTitle ->
                    navController.navigate("seats/$movieTitle")
                }
            )
        }

        composable(
            route = Routes.SEATS,
            arguments = listOf(navArgument("movieTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieTitle = backStackEntry.arguments?.getString("movieTitle") ?: "Movie"
            SeatSelectionScreen(
                movieTitle = movieTitle,
                onBack = {
                    navController.popBackStack()
                },
                onBuyTicket = { seatCount, totalPrice ->
                    val safePrice = totalPrice.replace("/", "")
                    navController.navigate("payment/$seatCount/$safePrice")
                }
            )
        }

        composable(
            route = Routes.PAYMENT,
            arguments = listOf(
                navArgument("seatCount")  { type = NavType.IntType },
                navArgument("totalPrice") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val seatCount  = backStackEntry.arguments?.getInt("seatCount") ?: 0
            val totalPrice = backStackEntry.arguments?.getString("totalPrice") ?: "Rp 0"

            PaymentScreen(
                seatCount        = seatCount,
                totalPrice       = totalPrice,
                onBack           = { navController.popBackStack() },
                onPaymentSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.PROFILE) {
            ProfileScreen(
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}