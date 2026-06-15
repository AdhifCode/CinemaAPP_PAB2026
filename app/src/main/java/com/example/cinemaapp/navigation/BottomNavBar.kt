package com.example.cinemaapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalActivity
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Redeem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cinemaapp.ui.theme.*

private data class BottomNavItem(
    val label:         String,
    val route:         String,
    val selectedIcon:  ImageVector,
    val unselectedIcon: ImageVector
)

private val navItems = listOf(
    BottomNavItem("Home",    Routes.HOME,    Icons.Filled.Home,          Icons.Outlined.Home),
    BottomNavItem("Movies",  Routes.MOVIES,  Icons.Filled.LocalActivity,  Icons.Outlined.LocalActivity),
    BottomNavItem("Coupons", Routes.HOME,    Icons.Filled.Redeem,         Icons.Outlined.Redeem),
    BottomNavItem("Profile", Routes.PROFILE, Icons.Filled.Person,         Icons.Outlined.Person),
)

// Route-route yang menyembunyikan bottom nav
private val routesWithoutBottomNav = setOf(Routes.SEATS, Routes.LOGIN, Routes.SIGNUP)

@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute      = navBackStackEntry?.destination?.route

    // Sembunyikan bottom nav di halaman tertentu
    if (currentRoute in routesWithoutBottomNav) return
    // Sembunyikan juga di payment (route-nya dinamis)
    if (currentRoute?.startsWith("payment/") == true) return

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BottomBarBg)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            navItems.forEach { item ->
                val isSelected = when (item.label) {
                    "Home"    -> currentRoute == Routes.HOME
                    "Movies"  -> currentRoute == Routes.MOVIES
                    "Coupons" -> false  // belum ada route dedicated
                    "Profile" -> currentRoute == Routes.PROFILE
                    else      -> false
                }

                BottomNavItemView(
                    item       = item,
                    isSelected = isSelected,
                    onClick    = {
                        // Coupons belum punya screen sendiri — arahkan ke HOME
                        val targetRoute = if (item.label == "Coupons") Routes.HOME else item.route
                        if (currentRoute != targetRoute) {
                            navController.navigate(targetRoute) {
                                popUpTo(Routes.HOME) { saveState = true }
                                launchSingleTop = true
                                restoreState    = true
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomNavItemView(
    item:       BottomNavItem,
    isSelected: Boolean,
    onClick:    () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier            = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(
                    if (isSelected) NeonGreen.copy(alpha = 0.15f)
                    else            androidx.compose.ui.graphics.Color.Transparent
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = if (isSelected) item.selectedIcon else item.unselectedIcon,
                contentDescription = item.label,
                tint               = if (isSelected) NeonGreen else TextSecondary,
                modifier           = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text     = item.label,
            color    = if (isSelected) NeonGreen else TextSecondary,
            fontSize = 10.sp
        )
    }
}