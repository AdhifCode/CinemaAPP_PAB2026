package com.example.cinemaapp.data.model

data class UserProfile(
    val id: Int = 1,
    val name: String = "Budi Santoso",
    val email: String = "demo@cinemaapp.com",
    val phone: String = "+62 812-3456-7890",
    val city: String = "Jakarta",
    val membershipTier: String = "Gold",  // Silver, Gold, Platinum
    val moviesWatched: Int = 47,
    val ticketsBought: Int = 83,
    val points: Int = 1240,
    val avatarUrl: String = ""
)