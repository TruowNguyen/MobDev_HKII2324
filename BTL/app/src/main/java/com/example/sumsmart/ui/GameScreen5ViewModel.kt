package com.example.sumsmart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameScreen5ViewModel : ViewModel() {
    private val _player1Input = MutableStateFlow("")
    val player1Input: StateFlow<String> = _player1Input

    private val _player2Input = MutableStateFlow("")
    val player2Input: StateFlow<String> = _player2Input

    private val _equation = MutableStateFlow(generateEquation())
    val equation: StateFlow<String> = _equation

    private val _resultMessage = MutableStateFlow<String?>(null)
    val resultMessage: StateFlow<String?> = _resultMessage

    private var leftNumber: Int = 0
    private var rightNumber: Int = 0

    fun updatePlayer1Input(value: String) {
        _player1Input.value = value
    }

    fun updatePlayer2Input(value: String) {
        _player2Input.value = value
    }

    fun checkSum() {
        val player1Value = _player1Input.value.toIntOrNull()
        val player2Value = _player2Input.value.toIntOrNull()

        if (player1Value != null && player2Value != null) {
            if (player1Value + leftNumber == player2Value + rightNumber) {
                resetGame()
                _resultMessage.value = "Đúng rồi! Tổng 2 vế bằng nhau."
            } else {
                _resultMessage.value = "Chưa chính xác, hãy chọn lại!"
                clearMessageAfterDelay()
            }
        } else {
            _resultMessage.value = "Vui lòng nhập số hợp lệ!"
            clearMessageAfterDelay()
        }
    }

    fun resetGame() {
        _player1Input.value = ""
        _player2Input.value = ""
        _equation.value = generateEquation()
        _resultMessage.value = null
    }

    private fun clearMessageAfterDelay() {
        viewModelScope.launch {
            delay(3000) // Delay for 3 seconds
            _resultMessage.value = null
        }
    }

    private fun resetGameAfterDelay() {
        viewModelScope.launch {
            delay(3000) // Delay for 3 seconds
            resetGame()
        }
    }
    private fun generateEquation(): String {
        leftNumber = Random.nextInt(1, 10)
        rightNumber = Random.nextInt(1, 10)

        // Ensure leftNumber and rightNumber are different
        while (leftNumber == rightNumber) {
            rightNumber = Random.nextInt(1, 10)
        }

        return "x + $leftNumber = y + $rightNumber"
    }

    fun clearMessage() {
        _resultMessage.value = null
    }
}
