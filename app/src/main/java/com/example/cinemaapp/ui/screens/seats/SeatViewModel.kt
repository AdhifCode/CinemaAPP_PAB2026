package com.example.cinemaapp.ui.screens.seats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaapp.data.model.*
import com.example.cinemaapp.data.repository.SeatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SeatUiState(
    val movieTitle:  String          = "Dune: Part Two",
    val movieGenre:  String          = "Sci-Fi  •  2h 46m",
    val seats:       List<Seat>      = emptyList(),
    val showDates:   List<ShowDate>  = emptyList(),
    val showTimes:   List<ShowTime>  = emptyList(),
    val pricePerSeat: Double         = 15.017,
    val isLoading:   Boolean         = true
) {
    val selectedSeats: List<Seat>
        get() = seats.filter { it.status == SeatStatus.SELECTED }

    val ticketSummary: TicketSummary
        get() = TicketSummary(
            selectedSeatCount = selectedSeats.size,
            pricePerSeat      = pricePerSeat
        )
}

@HiltViewModel
class SeatViewModel @Inject constructor(
    private val seatRepository: SeatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeatUiState())
    val uiState: StateFlow<SeatUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            combine(
                seatRepository.getSeats(),
                seatRepository.getShowDates(),
                seatRepository.getShowTimes()
            ) { seats, dates, times ->
                SeatUiState(
                    seats = seats,
                    showDates = dates,
                    showTimes = times,
                    isLoading = false
                )
            }.collect { newState ->
                _uiState.update { it.copy(
                    seats = newState.seats,
                    showDates = newState.showDates,
                    showTimes = newState.showTimes,
                    isLoading = newState.isLoading
                )}
            }
        }
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