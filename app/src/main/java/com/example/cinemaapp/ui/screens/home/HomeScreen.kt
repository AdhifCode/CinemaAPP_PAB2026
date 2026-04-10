package com.example.cinemaapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cinemaapp.ui.components.*
import com.example.cinemaapp.ui.theme.*

@Composable
fun HomeScreen(
    onNavigateToMovies: () -> Unit = {},
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Discover card data defined here to avoid
    // putting UI concerns inside the ViewModel
    val discoverCards = listOf(
        DiscoverCardData(
            title            = "Shopping",
            subtitle         = "12 active coupons",
            backgroundColors = listOf(CardShopping, CardShopping.copy(alpha = 0.6f)),
            icon             = Icons.Default.ShoppingBag,
            tag              = "HOT"
        ),
        DiscoverCardData(
            title            = "Tickets",
            subtitle         = "Book your seats",
            backgroundColors = listOf(CardTicket, CardTicket.copy(alpha = 0.6f)),
            icon             = Icons.Default.ConfirmationNumber,
            tag              = "NEW"
        ),
        DiscoverCardData(
            title            = "Dining",
            subtitle         = "5 nearby deals",
            backgroundColors = listOf(CardFood, CardFood.copy(alpha = 0.6f)),
            icon             = Icons.Default.ShoppingBag
        ),
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy),
        contentPadding = PaddingValues(bottom = 100.dp)   // clearance for bottom nav
    ) {
        // ── Top Bar ──────────────────────────────────────────────────────────
        item {
            ProfileTopBar(
                greeting          = uiState.greeting,
                userName          = uiState.userName,
                onSearchClick     = {},
                onNotificationClick = {}
            )
        }

        // ── "Discover" section header ─────────────────────────────────────────
        item {
            Spacer(modifier = Modifier.height(12.dp))
            SectionHeader(title = "Discover", actionLabel = "See all")
        }

        // ── Discover horizontal cards ─────────────────────────────────────────
        item {
            Spacer(modifier = Modifier.height(14.dp))
            LazyRow(
                contentPadding    = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(discoverCards) { card ->
                    DiscoverCard(
                        data    = card,
                        onClick = { if (card.title == "Tickets") onNavigateToMovies() }
                    )
                }
            }
        }

        // ── "My Coupons" section header ───────────────────────────────────────
        item {
            Spacer(modifier = Modifier.height(28.dp))
            SectionHeader(title = "My Coupons", actionLabel = "See all")
        }

        // ── Coupon list ───────────────────────────────────────────────────────
        item { Spacer(modifier = Modifier.height(14.dp)) }

        items(
            items = uiState.coupons,
            key   = { it.id }
        ) { coupon ->
            CouponCard(
                coupon  = coupon,
                onClick = {},
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 10.dp)
            )
        }
    }
}