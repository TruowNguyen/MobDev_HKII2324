package com.example.sumsmart.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.sumsmart.data.NumberProvider
import kotlin.random.Random

class GameScreen6ViewModel : ViewModel() {
    private val numberMap = NumberProvider.numberDataList.associate { it.drawableId to it.value }

    private val _numbers = MutableStateFlow<List<Int>>(emptyList())
    val numbers: StateFlow<List<Int>> get() = _numbers

    private val _targetSum = MutableStateFlow(10)
    val targetSum: StateFlow<Int> get() = _targetSum

    private val _selectedNumbers = MutableStateFlow<List<Int>>(emptyList())
    val selectedNumbers: StateFlow<List<Int>> get() = _selectedNumbers

    private val _resultMessage = MutableStateFlow<String?>(null)
    val resultMessage: StateFlow<String?> get() = _resultMessage

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> get() = _score

    init {
        generateNumbers()
    }

    private fun generateNumbers() {
        val pairs = mutableListOf<Int>()
        for (i in 0 until 4) { // Tạo 4 cặp số
            var a: Int
            var b: Int
            do {
                a = Random.nextInt(1, _targetSum.value)
                b = _targetSum.value - a
            } while (a == b || !numberMap.containsValue(a) || !numberMap.containsValue(b))
            pairs.add(a)
            pairs.add(b)
        }
        val numberImages = pairs.mapNotNull { number ->
            numberMap.entries.find { it.value == number }?.key
        }.shuffled(Random(System.currentTimeMillis()))

        _numbers.value = numberImages
    }

    private fun setRandomTargetSum() {
        val randomTarget = Random.nextInt(5, 16) // Adjusted range to accommodate sum of two numbers
        _targetSum.value = randomTarget
    }

    fun selectNumber(number: Int) {
        Log.d("GameScreen6ViewModel", "Resource ID selected: $number")
        if (_selectedNumbers.value.size < 2) {
            val actualNumber = numberMap[number]
            if (actualNumber != null) {
                _selectedNumbers.value = _selectedNumbers.value + actualNumber
                Log.d("GameScreen6ViewModel", "Actual number added: $actualNumber")
            } else {
                Log.e("GameScreen6ViewModel", "Invalid resource ID: $number")
            }
        }

        if (_selectedNumbers.value.size == 2) {
            checkSum()
        }
    }

    private fun checkSum() {
        val selectedNumbers = _selectedNumbers.value
        val targetSum = _targetSum.value
        Log.d("GameScreen6ViewModel", "Selected Numbers: $selectedNumbers")
        Log.d("GameScreen6ViewModel", "Target Sum: $targetSum")

        if (selectedNumbers.sum() == targetSum) {
            _score.value += 10 // Increase score by 10 for a correct answer
            _resultMessage.value = "Đúng rồi! Tổng là $targetSum."
            removeSelectedNumbers()
            checkEndGame()
        } else {
            _resultMessage.value = "Sai rồi! Vui lòng thử lại."
            resetSelection()
        }
    }

    private fun removeSelectedNumbers() {
        val currentNumbers = _numbers.value.toMutableList()
        _selectedNumbers.value.forEach { number ->
            val drawableId = numberMap.entries.find { it.value == number }?.key
            drawableId?.let { currentNumbers.remove(it) }
        }
        _numbers.value = currentNumbers
        resetSelection()
    }

    private fun resetSelection() {
        _selectedNumbers.value = emptyList()
    }

    private fun checkEndGame() {
        if (_numbers.value.isEmpty() || !canFindPairWithSum(_targetSum.value)) {
            _resultMessage.value = "Trò chơi kết thúc!"
        }
    }

    private fun canFindPairWithSum(targetSum: Int): Boolean {
        val numberValues = _numbers.value.mapNotNull { numberMap[it] }
        for (i in numberValues.indices) {
            for (j in i + 1 until numberValues.size) {
                if (numberValues[i] + numberValues[j] == targetSum) {
                    return true
                }
            }
        }
        return false
    }

    fun resetGame() {
        setRandomTargetSum() // Đặt lại targetSum khi bắt đầu trò chơi mới
        generateNumbers()
        _score.value = 0
        _resultMessage.value = null
        resetSelection()
    }

    fun endGame() {
        _resultMessage.value = "Trò chơi kết thúc!"
        // Bạn có thể thực hiện các hành động khác nếu cần khi trò chơi kết thúc
    }
}
