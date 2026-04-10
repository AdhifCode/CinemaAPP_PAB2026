package com.example.cinemaapp.data.model

import androidx.annotation.DrawableRes

data class Coupon(
    val id: Int,
    val brand: String,            // e.g. "McDonald's"
    val description: String,      // e.g. "Any burger purchase"
    val discountPercent: Int,      // e.g. 10  → displayed as "-10%"
    val expiryDate: String,        // e.g. "Dec 31, 2025"
    @DrawableRes val logoRes: Int, // Drawable resource for brand logo
    val isUsed: Boolean = false
)