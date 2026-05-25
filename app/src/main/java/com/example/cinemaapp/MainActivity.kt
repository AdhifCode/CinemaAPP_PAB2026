package com.example.cinemaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.cinemaapp.navigation.AppNavGraph
import com.example.cinemaapp.navigation.BottomNavBar
import com.example.cinemaapp.ui.theme.CinemaAppTheme
import com.example.cinemaapp.ui.theme.DeepNavy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CinemaAppTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier        = Modifier.fillMaxSize(),
                    containerColor  = DeepNavy,
                    bottomBar       = { BottomNavBar(navController) }
                ) { innerPadding ->
                    AppNavGraph(
                        navController = navController,
                        modifier      = Modifier.padding(innerPadding)  // ← pass to NavHost
                    )
                }
            }
        }
    }
}