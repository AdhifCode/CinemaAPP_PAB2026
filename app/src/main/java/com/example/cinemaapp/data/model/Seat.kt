package com.example.cinemaapp.data.model

/**
 * Represents a single cinema seat.
 * The grid is built from a List<Seat> ordered row-by-row.
 */
data class Seat(
    val id: Int,
    val row: Int,          // 0-indexed row number
    val col: Int,          // 0-indexed column number
    val status: SeatStatus
)

enum class SeatStatus {
    AVAILABLE,   // Outlined — user can select
    RESERVED,    // Filled gray — taken by someone else
    SELECTED,    // Filled neon green — chosen by current user
    CLOSED       // Shows ✕ icon — not in service
}

data class ShowTime(
    val id: Int,
    val time: String,       // e.g. "10:30 AM"
    val isSelected: Boolean = false
)

data class ShowDate(
    val id: Int,
    val dayOfWeek: String,  // e.g. "MON"
    val dayOfMonth: Int,    // e.g. 14
    val isSelected: Boolean = false
)

data class TicketSummary(
    val selectedSeatCount: Int,
    val pricePerSeat: Double,   // e.g. 15.05
    val currency: String = "$"
) {
    val totalPrice: Double get() = selectedSeatCount * pricePerSeat
    val formattedTotal: String get() = "$currency${"%.2f".format(totalPrice)}"
}