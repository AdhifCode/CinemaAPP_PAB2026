package com.example.cinemaapp.ui.screens.seats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cinemaapp.ui.components.*
import com.example.cinemaapp.ui.theme.*

@Composable
fun SeatSelectionScreen(
    onBack: () -> Unit = {},
    onBuyTicket: () -> Unit = {},
    viewModel: SeatViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val summary  = uiState.ticketSummary

    Scaffold(
        containerColor = DeepNavy,
        // ── Sticky bottom bar ─────────────────────────────────────────────────
        bottomBar = {
            BuyTicketBottomBar(
                selectedCount = summary.selectedSeatCount,
                totalPrice    = if (summary.selectedSeatCount == 0)
                    "$0.00"
                else
                    summary.formattedTotal,
                onBuyClick    = onBuyTicket
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ── Top Nav Bar ───────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector        = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint               = TextPrimary,
                        modifier           = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text       = uiState.movieTitle,
                        style      = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary
                    )
                    Text(
                        text  = uiState.movieGenre,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                // Balance spacer so title stays centered
                Spacer(modifier = Modifier.size(48.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ── Cinema screen curve ───────────────────────────────────────────
            CinemaScreenCurve(
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ── Seat grid ─────────────────────────────────────────────────────
            SeatGrid(
                seats      = uiState.seats,
                columns    = 7,
                onSeatClick = viewModel::onSeatClicked,
                modifier   = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Legend ────────────────────────────────────────────────────────
            SeatLegend(
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ── Divider ───────────────────────────────────────────────────────
            HorizontalDivider(
                modifier  = Modifier.padding(horizontal = 20.dp),
                thickness = 1.dp,
                color     = DividerColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Date selection ────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text       = "Select Date",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(uiState.showDates, key = { it.id }) { date ->
                        DateChip(
                            date    = date,
                            onClick = { viewModel.onDateSelected(date.id) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Time selection ────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text       = "Select Time",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(uiState.showTimes, key = { it.id }) { time ->
                        TimeChip(
                            time    = time,
                            onClick = { viewModel.onTimeSelected(time.id) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}