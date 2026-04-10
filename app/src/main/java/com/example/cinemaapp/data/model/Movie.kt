package com.example.cinemaapp.data.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class Movie(
    val id: Int,
    val title: String,
    val rating: Float,             // e.g. 4.5f
    val genre: String,             // e.g. "Action"
    val duration: String,          // e.g. "2h 15m"
    @DrawableRes val posterRes: Int
)

data class MovieCategory(
    val id: Int,
    val emoji: String,             // e.g. "😂"
    val title: String,             // e.g. "Comedy"
    val movieCount: Int,           // e.g. 12
    val chipColor: Color,
    val isSelected: Boolean = false
)