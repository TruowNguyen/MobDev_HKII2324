package com.example.sumsmart.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.sumsmart.R
import com.example.sumsmart.data.NumberProvider
import kotlin.random.Random

class GameScreen1ViewModel : ViewModel() {

    private val numberMap = NumberProvider.numberDataList.associate { it.drawableId to it.value }

    private val _numbers = MutableStateFlow<List<Int>>(emptyList())
    val numbers: StateFlow<List<Int>> get() = _numbers

    private val _targetSum = MutableStateFlow(10)
    val targetSum: StateFlow<Int> get() = _targetSum

    private val _selectedNumbers = MutableStateFlow<List<Int>>(emptyList())
    val selectedNumbers: StateFlow<List<Int>> get() = _selectedNumbers

    private val _resultMessage = MutableStateFlow<String?>(null)
    val resultMessage: StateFlow<String?> get() = _resultMessage

    private val _selectedNumbersDisplay = MutableStateFlow<List<Int>>(emptyList())
    val selectedNumbersDisplay: StateFlow<List<Int>> get() = _selectedNumbersDisplay

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> get() = _score

    init {
        generateNumbers()
        setRandomTargetSum()
    }

    private fun generateNumbers() {
        val numberImages = numberMap.keys.toList()
        _numbers.value = numberImages.shuffled(Random(System.currentTimeMillis()))
    }

    private fun setRandomTargetSum() {
        val randomTarget = Random.nextInt(3, 20) // Adjusted range to accommodate sum of two numbers
        _targetSum.value = randomTarget
    }

    fun selectNumber(number: Int) {
        Log.d("GameScreen1ViewModel", "Resource ID selected: $number")
        if (_selectedNumbers.value.size < 3) {
            val actualNumber = numberMap[number]
            if (actualNumber != null) {
                _selectedNumbers.value = _selectedNumbers.value + actualNumber
                _selectedNumbersDisplay.value = _selectedNumbersDisplay.value + number
                Log.d("GameScreen1ViewModel", "Actual number added: $actualNumber")
            } else {
                Log.e("GameScreen1ViewModel", "Invalid resource ID: $number")
            }
        }
    }


    fun resetSelection() {
        _selectedNumbers.value = emptyList()
        _selectedNumbersDisplay.value = emptyList()
    }

    fun checkSum() {
        val selectedNumbers = _selectedNumbers.value
        val targetSum = _targetSum.value
        Log.d("GameScreen1ViewModel", "Selected Numbers: $selectedNumbers")
        Log.d("GameScreen1ViewModel", "Target Sum: $targetSum")

        if (selectedNumbers.size >= 2 && selectedNumbers.sum() == targetSum) {
            _score.value += 10 // Increase score by 10 for a correct answer
            _resultMessage.value = "Đúng rồi! Tổng là $targetSum."
            generateNumbers() // Generate new numbers
            setRandomTargetSum() // Generate a new target sum
        } else {
            _resultMessage.value = "Sai rồi! Vui lòng thử lại."
        }
        resetSelection()
    }
}