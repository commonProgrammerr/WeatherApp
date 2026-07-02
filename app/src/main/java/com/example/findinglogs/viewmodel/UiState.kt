package com.example.findinglogs.viewmodel

import com.example.findinglogs.model.model.Weather

sealed class UiState {
    data object Loading : UiState()
    data class Success(val data: List<Weather>) : UiState()
    data class Error(val message: String) : UiState()
}
