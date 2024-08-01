package com.example.sumsmart.ui

import androidx.lifecycle.ViewModel
import com.example.sumsmart.data.NumberData
import com.example.sumsmart.data.NumberProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class GameScreen4ViewModel : ViewModel() {
    private val _numbersGrid = MutableStateFlow(generateRandomNumbersGrid())
    val numbersGrid: StateFlow<List<NumberData>> = _numbersGrid

    private val _targetSum = MutableStateFlow(generateTargetSum())
    val targetSum: StateFlow<Int> = _targetSum

    private val _selectedNumbers = MutableStateFlow(listOf<Int>())
    val selectedNumbers: StateFlow<List<Int>> = _selectedNumbers

    private val _resultMessage = MutableStateFlow<String?>(null)
    val resultMessage: StateFlow<String?> = _resultMessage

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> get() = _score

    fun selectNumber(index: Int) {
        _selectedNumbers.update { selectedNumbers ->
            if (selectedNumbers.contains(index)) {
                selectedNumbers - index
            } else {
                selectedNumbers + index
            }
        }

        checkSum()
    }

    private fun checkSum() {
        if (_selectedNumbers.value.size == 2) {
            val sum = _selectedNumbers.value.sumOf { _numbersGrid.value[it].value }
            if (sum == _targetSum.value && canConnect(_selectedNumbers.value[0], _selectedNumbers.value[1])) {
                _resultMessage.value = "Đúng rồi! Tổng là $sum"
                _score.value += 10
                removeNumbers()
                _selectedNumbers.value = listOf()
                _targetSum.value = generateTargetSum()
            } else {
                _resultMessage.value = "Chưa chính xác, hãy chọn lại!"
                _selectedNumbers.value = listOf()
            }
        }
    }

    private fun removeNumbers() {
        val updatedGrid = _numbersGrid.value.toMutableList()
        _selectedNumbers.value.forEach { index ->
            updatedGrid[index] = NumberData(0, 0) // Or some value representing an empty slot
        }
        _numbersGrid.value = updatedGrid
    }

    private fun canConnect(index1: Int, index2: Int): Boolean {
        val grid = _numbersGrid.value
        val row1 = index1 / 6
        val col1 = index1 % 6
        val row2 = index2 / 6
        val col2 = index2 % 6

        // Check horizontal path
        if (row1 == row2) {
            val start = minOf(col1, col2)
            val end = maxOf(col1, col2)
            for (col in start + 1 until end) {
                if (grid[row1 * 6 + col].drawableId != 0) {
                    return false
                }
            }
            return true
        }

        // Check vertical path
        if (col1 == col2) {
            val start = minOf(row1, row2)
            val end = maxOf(row1, row2)
            for (row in start + 1 until end) {
                if (grid[row * 6 + col1].drawableId != 0) {
                    return false
                }
            }
            return true
        }

        // Path not valid
        return false
    }

    fun resetGame() {
        _numbersGrid.value = generateRandomNumbersGrid()
        _selectedNumbers.value = listOf()
        _resultMessage.value = null
    }

    private fun generateRandomNumbersGrid(): List<NumberData> {
        return List(36) { NumberProvider.numberDataList.random() }
    }

    private fun generateTargetSum(): Int {
        return Random.nextInt(3, 18)
    }
}
