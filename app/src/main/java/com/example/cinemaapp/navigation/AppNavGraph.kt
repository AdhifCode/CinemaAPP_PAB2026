package com.example.cinemaapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cinemaapp.ui.screens.home.HomeScreen
import com.example.cinemaapp.ui.screens.movies.MoviesScreen
import com.example.cinemaapp.ui.screens.seats.SeatSelectionScreen
import com.example.cinemaapp.ui.screens.payment.PaymentScreen // Nanti kita buat ini

// ── Route constants ────────────────────────────────────────────────────────────
object Routes {
    const val HOME    = "home"
    const val MOVIES  = "movies"
    const val SEATS   = "seats"
    const val PAYMENT = "payment/{seatCount}/{totalPrice}"
}

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController    = navController,
        startDestination = Routes.HOME,
        modifier         = modifier
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToMovies = {
                    navController.navigate(Routes.MOVIES)
                }
            )
        }

        composable(Routes.MOVIES) {
            MoviesScreen(
                onMovieClick = { _ ->
                    navController.navigate(Routes.SEATS)
                }
            )
        }

        composable(Routes.SEATS) {
            SeatSelectionScreen(
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
                navArgument("seatCount") { type = NavType.IntType },
                navArgument("totalPrice") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val seatCount = backStackEntry.arguments?.getInt("seatCount") ?: 0
            val totalPrice = backStackEntry.arguments?.getString("totalPrice") ?: "$0.00"

            PaymentScreen(
                seatCount = seatCount,
                totalPrice = totalPrice,
                onBack = { navController.popBackStack() },
                onPaymentSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }
    }
}