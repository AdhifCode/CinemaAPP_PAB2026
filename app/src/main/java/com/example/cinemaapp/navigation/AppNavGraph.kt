package com.example.cinemaapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cinemaapp.ui.screens.home.HomeScreen
import com.example.cinemaapp.ui.screens.movies.MoviesScreen
import com.example.cinemaapp.ui.screens.seats.SeatSelectionScreen

// ── Route constants ────────────────────────────────────────────────────────────
object Routes {
    const val HOME   = "home"
    const val MOVIES = "movies"
    const val SEATS  = "seats"
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
                    // Any movie tap navigates to seat selection
                    navController.navigate(Routes.SEATS)
                }
            )
        }

        composable(Routes.SEATS) {
            SeatSelectionScreen(
                onBack = {
                    navController.popBackStack()
                },
                onBuyTicket = {
                    // TODO: navigate to confirmation screen
                }
            )
        }
    }
}