package com.example.sumsmart.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.sumsmart.data.NumberProvider
import kotlin.random.Random

class GameScreen2ViewModel : ViewModel() {

    private val numberMap = NumberProvider.numberDataList.associate { it.drawableId to it.value }

    private val _numbersTop = MutableStateFlow<List<Int>>(emptyList())
    val numbersTop: StateFlow<List<Int>> = _numbersTop

    private val _numbersBottom = MutableStateFlow<List<Int>>(emptyList())
    val numbersBottom: StateFlow<List<Int>> = _numbersBottom

    private val _targetSum = MutableStateFlow(10)
    val targetSum: StateFlow<Int> get() = _targetSum

    private val _selectedNumberTop = MutableStateFlow<List<Int>>(emptyList())
    val selectedNumberTop: StateFlow<List<Int>> get() = _selectedNumberTop

    private val _selectedNumberBottom = MutableStateFlow<List<Int>>(emptyList())
    val selectedNumberBottom: StateFlow<List<Int>> get() = _selectedNumberBottom

    private val _resultMessage = MutableStateFlow<String?>(null)
    val resultMessage: StateFlow<String?> get() = _resultMessage

    private val _selectedNumbersTopDisplay = MutableStateFlow<List<Int>>(emptyList())
    val selectedNumbersTopDisplay: StateFlow<List<Int>> get() = _selectedNumbersTopDisplay

    private val _selectedNumbersBottomDisplay = MutableStateFlow<List<Int>>(emptyList())
    val selectedNumbersBottomDisplay: StateFlow<List<Int>> get() = _selectedNumbersBottomDisplay
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> get() = _score

    init {
        generateNumbers()
        setRandomTargetSum()
    }

    private fun generateNumbers() {
        val allNumbers = numberMap.keys.toList()
        val shuffledNumbers = allNumbers.shuffled(Random(System.currentTimeMillis()))
        _numbersTop.value = shuffledNumbers
        _numbersBottom.value = shuffledNumbers.shuffled(Random(System.currentTimeMillis())) // Trộn lại để có danh sách khác nhau
    }

    private fun setRandomTargetSum() {
        val randomTarget = Random.nextInt(2, 18) // Điều chỉnh phạm vi nếu cần
        _targetSum.value = randomTarget
    }

    fun selectNumberTop(number: Int) {
        Log.d("GameScreen2ViewModel", "Số được chọn từ dãy trên: $number")
        if (_selectedNumberTop.value.size < 2) {
            val actualNumberTop = numberMap[number]
            if (actualNumberTop != null) {
                _selectedNumberTop.value = _selectedNumberTop.value + actualNumberTop
                _selectedNumbersTopDisplay.value = _selectedNumbersTopDisplay.value + number
            } else {
                Log.e("GameScreen2ViewModel", "Invalid resource ID: $number")
            }
        }
    }

    fun selectNumberBottom(number: Int) {
        Log.d("GameScreen2ViewModel", "Số được chọn từ dãy dưới: $number")
        if (_selectedNumberBottom.value.size < 2) {
            val actualNumberBottom = numberMap[number]
            if (actualNumberBottom != null) {
                _selectedNumberBottom.value = _selectedNumberBottom.value + actualNumberBottom
                _selectedNumbersBottomDisplay.value = _selectedNumbersBottomDisplay.value + number
            } else {
                Log.e("GameScreen2ViewModel", "Invalid resource ID: $number")
            }
        }
    }

    fun resetSelection() {
        _selectedNumberTop.value = emptyList()
        _selectedNumberBottom.value = emptyList()
        _selectedNumbersBottomDisplay.value = emptyList()
        _selectedNumbersTopDisplay.value = emptyList()

    }

    fun checkSum() {
        val selectedTop = _selectedNumberTop.value
        val selectedBottom = _selectedNumberBottom.value
        val targetSum = _targetSum.value
        Log.d("GameScreen2ViewModel", "Số được chọn từ dãy trên: $selectedTop")
        Log.d("GameScreen2ViewModel", "Số được chọn từ dãy dưới: $selectedBottom")
        Log.d("GameScreen2ViewModel", "Tổng mục tiêu: $targetSum")

        val totalSum = (selectedTop + selectedBottom).sum()

        if (totalSum == targetSum) {
            _resultMessage.value = "Đúng rồi! Tổng là $targetSum."
            _score.value += 10
            setRandomTargetSum()
            generateNumbers() // Tạo số mới sau khi trả lời đúng
        } else {
            _resultMessage.value = "Sai rồi! Vui lòng thử lại."
        }
        resetSelection()
    }
}