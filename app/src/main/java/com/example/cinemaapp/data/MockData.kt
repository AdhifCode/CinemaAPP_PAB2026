package com.example.cinemaapp.data

import androidx.compose.ui.graphics.Color
import com.example.cinemaapp.R
import com.example.cinemaapp.data.model.*
import com.example.cinemaapp.ui.theme.*

object MockData {

    val coupons = listOf(
        Coupon(1, "McDonald's",  "Any burger purchase",       10, "Dec 31, 2025", R.drawable.ic_mcdonalds),
        Coupon(2, "Starbucks",   "Grande size & above",       15, "Nov 30, 2025", R.drawable.ic_starbucks),
        Coupon(3, "KFC",         "Bucket meal combo",          20, "Jan 15, 2026", R.drawable.ic_kfc),
        Coupon(4, "Pizza Hut",   "Family size pizza",          25, "Feb 28, 2026", R.drawable.ic_pizzahut),
        Coupon(5, "Subway",      "Footlong sandwich",          12, "Mar 10, 2026", R.drawable.ic_subway),
    )

    val movieCategories = listOf(
        MovieCategory(1, "😂", "Comedy",  14, ChipComedy,  isSelected = true),
        MovieCategory(2, "⚽", "Sports",  8,  ChipSports),
        MovieCategory(3, "💥", "Action",  21, ChipAction),
        MovieCategory(4, "🎭", "Drama",   17, ChipDrama),
        MovieCategory(5, "👻", "Horror",  9,  Color(0xFF6D28D9)),
    )

    val popularMovies = listOf(
        Movie(1, "Dune: Part Two",     4.8f, "Sci-Fi",  "2h 46m", R.drawable.poster_dune),
        Movie(2, "Deadpool & Wolverine", 4.6f, "Action", "2h 8m",  R.drawable.poster_deadpool),
        Movie(3, "Inside Out 2",       4.7f, "Comedy",  "1h 40m", R.drawable.poster_insideout),
        Movie(4, "Alien: Romulus",     4.3f, "Horror",  "1h 59m", R.drawable.poster_alien),
        Movie(5, "Twisters",           4.1f, "Action",  "2h 2m",  R.drawable.poster_twisters),
    )

    val seats: List<Seat> = buildList {
        val predefined = mapOf(
            // (row, col) → SeatStatus overrides; rest default to AVAILABLE
            Pair(0,0) to SeatStatus.CLOSED,
            Pair(0,6) to SeatStatus.CLOSED,
            Pair(1,1) to SeatStatus.RESERVED,
            Pair(1,2) to SeatStatus.RESERVED,
            Pair(2,3) to SeatStatus.RESERVED,
            Pair(2,4) to SeatStatus.RESERVED,
            Pair(3,0) to SeatStatus.SELECTED,
            Pair(3,1) to SeatStatus.SELECTED,
            Pair(3,2) to SeatStatus.SELECTED,
            Pair(4,5) to SeatStatus.RESERVED,
            Pair(5,6) to SeatStatus.CLOSED,
            Pair(6,0) to SeatStatus.RESERVED,
            Pair(6,1) to SeatStatus.RESERVED,
        )
        var id = 0
        for (row in 0 until 8) {
            for (col in 0 until 7) {
                val status = predefined[Pair(row, col)] ?: SeatStatus.AVAILABLE
                add(Seat(id++, row, col, status))
            }
        }
    }

    val showDates = listOf(
        ShowDate(1, "MON", 12),
        ShowDate(2, "TUE", 13, isSelected = true),
        ShowDate(3, "WED", 14),
        ShowDate(4, "THU", 15),
        ShowDate(5, "FRI", 16),
        ShowDate(6, "SAT", 17),
    )

    val showTimes = listOf(
        ShowTime(1, "10:30 AM"),
        ShowTime(2, "01:15 PM", isSelected = true),
        ShowTime(3, "04:00 PM"),
        ShowTime(4, "07:30 PM"),
        ShowTime(5, "10:00 PM"),
    )

    val initialTicketSummary = TicketSummary(
        selectedSeatCount = 3,
        pricePerSeat = 15.017  // 3 × 15.017 ≈ $45.05
    )
}