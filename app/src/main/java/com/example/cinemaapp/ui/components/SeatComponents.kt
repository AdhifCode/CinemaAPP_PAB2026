package com.example.cinemaapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemaapp.data.model.Seat
import com.example.cinemaapp.data.model.SeatStatus
import com.example.cinemaapp.data.model.ShowDate
import com.example.cinemaapp.data.model.ShowTime
import com.example.cinemaapp.ui.theme.*

// ─── Single Seat Item ─────────────────────────────────────────────────────────

@Composable
fun SeatItem(
    seat: Seat,
    seatSize: Dp = 36.dp,
    onSeatClick: (Seat) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isClickable = seat.status == SeatStatus.AVAILABLE ||
            seat.status == SeatStatus.SELECTED

    Box(
        modifier = modifier
            .size(seatSize)
            .clip(RoundedCornerShape(8.dp))
            .then(seatBackground(seat.status))
            .then(
                if (isClickable) Modifier.clickable { onSeatClick(seat) }
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        when (seat.status) {
            SeatStatus.CLOSED -> {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Closed seat",
                    tint = TextSecondary.copy(alpha = 0.5f),
                    modifier = Modifier.size(16.dp)
                )
            }
            else -> { /* no icon — color alone communicates the state */ }
        }
    }
}

private fun seatBackground(status: SeatStatus): Modifier = when (status) {
    SeatStatus.AVAILABLE -> Modifier.border(
        width = 1.5.dp,
        color = TextSecondary.copy(alpha = 0.4f),
        shape = RoundedCornerShape(8.dp)
    )
    SeatStatus.RESERVED  -> Modifier.background(SeatReserved)
    SeatStatus.SELECTED  -> Modifier.background(NeonGreen)
    SeatStatus.CLOSED    -> Modifier.background(SeatClosed)
}

// ─── Seat Grid ────────────────────────────────────────────────────────────────

@Composable
fun SeatGrid(
    seats: List<Seat>,
    columns: Int = 7,
    onSeatClick: (Seat) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val rows = seats.chunked(columns)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        rows.forEachIndexed { rowIndex, rowSeats ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Row letter label (A, B, C…)
                Text(
                    text = ('A' + rowIndex).toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    fontSize = 11.sp,
                    modifier = Modifier.width(14.dp)
                )

                // Seats — gap in the middle to simulate aisle
                rowSeats.forEachIndexed { colIndex, seat ->
                    SeatItem(
                        seat = seat,
                        onSeatClick = onSeatClick
                    )
                    // Aisle gap after column 3
                    if (colIndex == 2) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
        }
    }
}

// ─── Cinema Screen Curve ──────────────────────────────────────────────────────

@Composable
fun CinemaScreenCurve(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.foundation.Canvas(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(24.dp)
        ) {
            val path = androidx.compose.ui.graphics.Path().apply {
                moveTo(0f, size.height)
                quadraticBezierTo(
                    size.width / 2f, 0f,
                    size.width, size.height
                )
            }
            drawPath(
                path = path,
                color = NeonGreen.copy(alpha = 0.6f),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "SCREEN",
            style = MaterialTheme.typography.labelSmall,
            color = NeonGreen.copy(alpha = 0.6f),
            letterSpacing = 4.sp,
            fontSize = 10.sp
        )
    }
}

// ─── Seat Legend ──────────────────────────────────────────────────────────────

@Composable
fun SeatLegend(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LegendItem(color = null,        label = "Available", isBorder = true)
        LegendItem(color = SeatReserved,label = "Reserved")
        LegendItem(color = NeonGreen,   label = "Selected")
        LegendItem(color = SeatClosed,  label = "Closed",    showX = true)
    }
}

@Composable
private fun LegendItem(
    color: Color?,
    label: String,
    isBorder: Boolean = false,
    showX: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(RoundedCornerShape(4.dp))
                .then(
                    if (isBorder) Modifier.border(
                        1.5.dp, TextSecondary.copy(alpha = 0.5f), RoundedCornerShape(4.dp)
                    )
                    else Modifier.background(color ?: SurfaceVariant)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (showX) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = TextSecondary.copy(alpha = 0.5f),
                    modifier = Modifier.size(10.dp)
                )
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
            fontSize = 11.sp
        )
    }
}

// ─── Date Chip ────────────────────────────────────────────────────────────────

@Composable
fun DateChip(
    date: ShowDate,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val bgColor   = if (date.isSelected) NeonGreen     else SurfaceDark
    val textColor = if (date.isSelected) TextOnAccent  else TextSecondary

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = date.dayOfWeek,
            style = MaterialTheme.typography.bodySmall,
            color = if (date.isSelected) TextOnAccent.copy(alpha = 0.7f) else TextSecondary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

// ─── Time Chip ────────────────────────────────────────────────────────────────

@Composable
fun TimeChip(
    time: ShowTime,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val bgColor   = if (time.isSelected) NeonGreen    else SurfaceDark
    val textColor = if (time.isSelected) TextOnAccent else TextSecondary

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = time.time,
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
            fontSize = 13.sp
        )
    }
}

// ─── Buy Ticket Bottom Bar ────────────────────────────────────────────────────

@Composable
fun BuyTicketBottomBar(
    selectedCount: Int,
    totalPrice: String,
    onBuyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(BottomBarBg)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Seat count + price
        Column {
            Text(
                text = "$selectedCount Seats Selected",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = totalPrice,
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        }

        // CTA button
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(NeonGreen)
                .clickable(onClick = onBuyClick)
                .padding(horizontal = 28.dp, vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Buy Ticket",
                style = MaterialTheme.typography.labelLarge,
                color = TextOnAccent,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}