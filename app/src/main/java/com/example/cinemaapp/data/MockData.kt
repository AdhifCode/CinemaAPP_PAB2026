package com.example.cinemaapp.data

import androidx.compose.ui.graphics.Color
import com.example.cinemaapp.R
import com.example.cinemaapp.data.model.*
import com.example.cinemaapp.ui.theme.*

object MockData {

    // ─── Real Indonesian Cinema Coupons ───────────────────────────────────────
    val coupons = listOf(
        Coupon(
            id              = 1,
            brand           = "McDonald's",
            description     = "McSpicy Burger atau Big Mac combo",
            discountPercent = 10,
            expiryDate      = "30 Jun 2026",
            logoRes         = R.drawable.ic_mcdonalds
        ),
        Coupon(
            id              = 2,
            brand           = "Starbucks",
            description     = "Grande size ke atas, semua varian",
            discountPercent = 15,
            expiryDate      = "31 Mei 2026",
            logoRes         = R.drawable.ic_starbucks
        ),
        Coupon(
            id              = 3,
            brand           = "KFC",
            description     = "Bucket Combo Hemat 6 pcs + minuman",
            discountPercent = 20,
            expiryDate      = "15 Jul 2026",
            logoRes         = R.drawable.ic_kfc
        ),
        Coupon(
            id              = 4,
            brand           = "Pizza Hut",
            description     = "Family size pizza pilihan (diameter 33 cm)",
            discountPercent = 25,
            expiryDate      = "28 Feb 2026",
            logoRes         = R.drawable.ic_pizzahut,
            isUsed          = true
        ),
        Coupon(
            id              = 5,
            brand           = "Subway",
            description     = "Footlong sandwich any variant",
            discountPercent = 12,
            expiryDate      = "10 Mar 2026",
            logoRes         = R.drawable.ic_subway
        ),
    )

    // ─── Movie Categories ─────────────────────────────────────────────────────
    val movieCategories = listOf(
        MovieCategory(1, "😂", "Comedy",  14, ChipComedy,  isSelected = true),
        MovieCategory(2, "⚽", "Sports",   8,  ChipSports),
        MovieCategory(3, "💥", "Action",  21, ChipAction),
        MovieCategory(4, "🎭", "Drama",   17, ChipDrama),
        MovieCategory(5, "👻", "Horror",   9, Color(0xFF6D28D9)),
    )

    // ─── Popular Movies (with full detail) ────────────────────────────────────
    val popularMovies = listOf(
        Movie(
            id          = 1,
            title       = "Dune: Part Two",
            rating      = 4.8f,
            genre       = "Sci-Fi",
            duration    = "2j 46m",
            posterRes   = R.drawable.poster_dune,
            year        = 2024,
            director    = "Denis Villeneuve",
            cast        = "Timothée Chalamet, Zendaya, Rebecca Ferguson, Austin Butler",
            description = "Paul Atreides bersatu dengan suku Fremen dalam perjalanan balas dendam " +
                    "terhadap persekongkolan yang menghancurkan keluarganya. Dihadapkan pada " +
                    "pilihan antara cinta hidupnya dan takdir alam semesta, Paul harus mencegah " +
                    "masa depan kelam yang hanya ia sendiri yang bisa memprediksinya.",
            trailerUrl  = "https://www.youtube.com/embed/Way9Dexny3w"
        ),
        Movie(
            id          = 2,
            title       = "Deadpool & Wolverine",
            rating      = 4.6f,
            genre       = "Action",
            duration    = "2j 8m",
            posterRes   = R.drawable.poster_deadpool,
            year        = 2024,
            director    = "Shawn Levy",
            cast        = "Ryan Reynolds, Hugh Jackman, Emma Corrin, Matthew Macfadyen",
            description = "Deadpool direkrut oleh TVA dan harus bekerja sama dengan Wolverine " +
                    "yang skeptis untuk menyelamatkan multiverse dari ancaman baru. Dua karakter " +
                    "ikonik Marvel akhirnya bertemu dalam petualangan penuh aksi, humor, dan " +
                    "emosi yang tak terduga.",
            trailerUrl  = "https://www.youtube.com/embed/73_1biulkYk"
        ),
        Movie(
            id          = 3,
            title       = "Inside Out 2",
            rating      = 4.7f,
            genre       = "Comedy",
            duration    = "1j 40m",
            posterRes   = R.drawable.poster_insideout,
            year        = 2024,
            director    = "Kelsey Mann",
            cast        = "Amy Poehler, Maya Hawke, Kensington Tallman, Liza Lapira",
            description = "Riley memasuki masa remaja dan emosi-emosi baru mulai bermunculan di " +
                    "Markas Besar. Anxiety yang ambisius mengambil alih kendali, memaksa Joy " +
                    "dan teman-temannya berjuang untuk mendapatkan kembali tempat mereka dalam " +
                    "kehidupan Riley yang terus berkembang.",
            trailerUrl  = "https://www.youtube.com/embed/LEjhY15eCx0"
        ),
        Movie(
            id          = 4,
            title       = "Alien: Romulus",
            rating      = 4.3f,
            genre       = "Horror",
            duration    = "1j 59m",
            posterRes   = R.drawable.poster_alien,
            year        = 2024,
            director    = "Fede Álvarez",
            cast        = "Cailee Spaeny, David Jonsson, Archie Renaux, Isabela Merced",
            description = "Sekelompok penjelajah luar angkasa muda menemukan stasiun ruang " +
                    "angkasa yang terbengkalai. Saat menjelajahi kegelapannya, mereka bertemu " +
                    "dengan bentuk kehidupan paling menakutkan di alam semesta — Alien Xenomorph " +
                    "dalam kondisi yang lebih brutal dari sebelumnya.",
            trailerUrl  = "https://www.youtube.com/embed/ZNIXoMq-NNk"
        ),
        Movie(
            id          = 5,
            title       = "Twisters",
            rating      = 4.1f,
            genre       = "Action",
            duration    = "2j 2m",
            posterRes   = R.drawable.poster_twisters,
            year        = 2024,
            director    = "Lee Isaac Chung",
            cast        = "Daisy Edgar-Jones, Glen Powell, Anthony Ramos, Brandon Perea",
            description = "Kate Cooper, mantan ahli badai yang trauma, kembali ke Oklahoma untuk " +
                    "menguji sistem deteksi tornado revolusioner. Di lapangan ia bertemu Tyler " +
                    "Owens, pemburu badai karismatik. Bersama mereka menghadapi musim tornado " +
                    "paling mematikan dalam sejarah.",
            trailerUrl  = "https://www.youtube.com/embed/AKjqkKS8UF8"
        ),
    )

    // ─── Now Showing (subset berbeda, tetap menggunakan Movie yang sama) ──────
    val nowShowingMovies = popularMovies.reversed()

    // ─── Seat Layout ──────────────────────────────────────────────────────────
    val seats: List<Seat> = buildList {
        val predefined = mapOf(
            Pair(0, 0) to SeatStatus.CLOSED,
            Pair(0, 6) to SeatStatus.CLOSED,
            Pair(1, 1) to SeatStatus.RESERVED,
            Pair(1, 2) to SeatStatus.RESERVED,
            Pair(2, 3) to SeatStatus.RESERVED,
            Pair(2, 4) to SeatStatus.RESERVED,
            Pair(3, 0) to SeatStatus.SELECTED,
            Pair(3, 1) to SeatStatus.SELECTED,
            Pair(3, 2) to SeatStatus.SELECTED,
            Pair(4, 5) to SeatStatus.RESERVED,
            Pair(5, 6) to SeatStatus.CLOSED,
            Pair(6, 0) to SeatStatus.RESERVED,
            Pair(6, 1) to SeatStatus.RESERVED,
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
        ShowDate(1, "SEN", 21),
        ShowDate(2, "SEL", 22, isSelected = true),
        ShowDate(3, "RAB", 23),
        ShowDate(4, "KAM", 24),
        ShowDate(5, "JUM", 25),
        ShowDate(6, "SAB", 26),
        ShowDate(7, "MIN", 27),
    )

    val showTimes = listOf(
        ShowTime(1, "10.30"),
        ShowTime(2, "13.15", isSelected = true),
        ShowTime(3, "16.00"),
        ShowTime(4, "19.30"),
        ShowTime(5, "22.00"),
    )

    val initialTicketSummary = TicketSummary(
        selectedSeatCount = 3,
        pricePerSeat      = 50_000.0
    )

    val currentUser = com.example.cinemaapp.data.model.UserProfile(
        id             = 1,
        name           = "Budi Santoso",
        email          = "demo@cinemaapp.com",
        phone          = "+62 812-3456-7890",
        city           = "Jakarta",
        membershipTier = "Gold",
        moviesWatched  = 47,
        ticketsBought  = 83,
        points         = 1240
    )
}