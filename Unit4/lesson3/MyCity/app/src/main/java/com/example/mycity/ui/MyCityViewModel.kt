package com.example.mycity.ui

import androidx.lifecycle.ViewModel
import com.example.mycity.model.Category
import com.example.mycity.model.MyCityUiState
import com.example.mycity.model.Recommendation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyCityViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(MyCityUiState())
    val uiState: StateFlow<MyCityUiState> = _uiState.asStateFlow()

    fun updateSelectedCategory(category : Category) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCategory = category
            )
        }
    }

    fun updateSelectedReccomendation(reccomendation: Recommendation){
        _uiState.update { currentState ->
            currentState.copy(
                selectedReccomendation = reccomendation
            )
        }
    }
}