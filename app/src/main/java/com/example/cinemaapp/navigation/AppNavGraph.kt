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
import com.example.cinemaapp.ui.screens.signup.SignupScreen // <-- JANGAN LUPA IMPORT INI

// ── Route constants ────────────────────────────────────────────────────────────
object Routes {
    const val LOGIN   = "login"
    const val SIGNUP  = "signup"
    const val HOME    = "home"
    const val MOVIES  = "movies"
    const val SEATS   = "seats"
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
        // ── Login ─────────────────────────────────────────────────────────────
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

        // ── Signup ────────────────────────────────────────────────────────────
        // INI ADALAH BAGIAN YANG KURANG SEBELUMNYA
        composable(Routes.SIGNUP) {
            SignupScreen(
                onSignupSuccess = {
                    // Jika sukses daftar, arahkan ke Home dan hapus tumpukan layar Login & Signup
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    // Jika batal dan ingin kembali ke Login
                    navController.popBackStack()
                }
            )
        }

        // ── Home ──────────────────────────────────────────────────────────────
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToMovies = {
                    navController.navigate(Routes.MOVIES)
                }
            )
        }

        // ── Movies ────────────────────────────────────────────────────────────
        composable(Routes.MOVIES) {
            MoviesScreen(
                onMovieClick = { _ ->
                    navController.navigate(Routes.SEATS)
                }
            )
        }

        // ── Seat Selection ────────────────────────────────────────────────────
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

        // ── Payment ───────────────────────────────────────────────────────────
        composable(
            route = Routes.PAYMENT,
            arguments = listOf(
                navArgument("seatCount")  { type = NavType.IntType },
                navArgument("totalPrice") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val seatCount  = backStackEntry.arguments?.getInt("seatCount") ?: 0
            val totalPrice = backStackEntry.arguments?.getString("totalPrice") ?: "$0.00"

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

        // ── Profile ───────────────────────────────────────────────────────────
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