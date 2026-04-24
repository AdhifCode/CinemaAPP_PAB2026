package com.example.cinemaapp.data.model

data class UserProfile(
    val id: Int = 1,
    val name: String = "Nadhifal Azharuddiya",
    val email: String = "nadhifalazharuddiya@gmail.com",
    val phone: String = "+62 812-3456-7890",
    val city: String = "Jakarta",
    val membershipTier: String = "Gold",  // Silver, Gold, Platinum
    val moviesWatched: Int = 0,
    val ticketsBought: Int = 0,
    val points: Int = 0,
    val avatarUrl: String = ""
)