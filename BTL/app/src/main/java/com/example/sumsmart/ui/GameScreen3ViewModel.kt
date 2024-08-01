package com.example.sumsmart.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameScreen3ViewModel : ViewModel() {
    private val _player1Input = MutableStateFlow("")
    val player1Input: StateFlow<String> = _player1Input

    private val _player2Input = MutableStateFlow("")
    val player2Input: StateFlow<String> = _player2Input

    private val _resultMessage = MutableStateFlow("")
    val resultMessage: StateFlow<String> = _resultMessage

    private val _targetSum = MutableStateFlow(10) // Set a default target sum
    val targetSum: StateFlow<Int> = _targetSum

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> get() = _score

    fun onPlayer1InputChanged(newValue: String) {
        _player1Input.value = newValue
    }

    fun onPlayer2InputChanged(newValue: String) {
        _player2Input.value = newValue
    }

    fun checkSum() {
        val player1Value = _player1Input.value.toIntOrNull()
        val player2Value = _player2Input.value.toIntOrNull()

        if (player1Value != null && player2Value != null) {
            val sum = player1Value + player2Value
            if (sum == _targetSum.value) {
                _score.value += 10
                _resultMessage.value = "Đúng rồi! Tổng là ${_targetSum.value}."
                resetInputs()
                setRandomTargetSum()
            } else {
                _resultMessage.value = "Sai rồi! Tổng là $sum. Hãy thử lại."
            }
        } else {
            _resultMessage.value = "Vui lòng nhập số hợp lệ."
        }
    }

    private fun resetInputs() {
        _player1Input.value = ""
        _player2Input.value = ""
    }

    private fun setRandomTargetSum() {
        _targetSum.value = (2..20).random() // Adjust the range as needed
    }
}
