package com.example.cinemaapp.ui.screens.seats

import androidx.lifecycle.ViewModel
import com.example.cinemaapp.data.MockData
import com.example.cinemaapp.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SeatUiState(
    val movieTitle:  String          = "",
    val movieGenre:  String          = "TMDB Movie",
    val seats:       List<Seat>      = emptyList(),
    val showDates:   List<ShowDate>  = emptyList(),
    val showTimes:   List<ShowTime>  = emptyList(),
    val pricePerSeat: Double         = 50000.0
) {
    val selectedSeats: List<Seat>
        get() = seats.filter { it.status == SeatStatus.SELECTED }

    val ticketSummary: TicketSummary
        get() = TicketSummary(
            selectedSeatCount = selectedSeats.size,
            pricePerSeat      = pricePerSeat
        )
}

class SeatViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SeatUiState())
    val uiState: StateFlow<SeatUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        _uiState.value = _uiState.value.copy(
            seats     = MockData.seats,
            showDates = MockData.showDates,
            showTimes = MockData.showTimes
        )
    }

    fun updateMovieTitle(title: String) {
        _uiState.update { it.copy(movieTitle = title) }
    }

    // Toggle AVAILABLE ↔ SELECTED; ignore RESERVED and CLOSED
    fun onSeatClicked(seat: Seat) {
        if (seat.status == SeatStatus.RESERVED ||
            seat.status == SeatStatus.CLOSED) return

        _uiState.update { state ->
            state.copy(
                seats = state.seats.map { s ->
                    if (s.id == seat.id) {
                        s.copy(
                            status = if (s.status == SeatStatus.SELECTED)
                                SeatStatus.AVAILABLE
                            else
                                SeatStatus.SELECTED
                        )
                    } else s
                }
            )
        }
    }

    fun onDateSelected(dateId: Int) {
        _uiState.update { state ->
            state.copy(
                showDates = state.showDates.map { d ->
                    d.copy(isSelected = d.id == dateId)
                }
            )
        }
    }

    fun onTimeSelected(timeId: Int) {
        _uiState.update { state ->
            state.copy(
                showTimes = state.showTimes.map { t ->
                    t.copy(isSelected = t.id == timeId)
                }
            )
        }
    }
}