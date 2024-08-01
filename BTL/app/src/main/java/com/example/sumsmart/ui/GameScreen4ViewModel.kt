import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sumsmart.data.NumberData
import com.example.sumsmart.data.NumberProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random
import java.util.ArrayDeque

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

        viewModelScope.launch {
            checkSum()
        }
    }

    private fun checkSum() {
        if (_selectedNumbers.value.size == 2) {
            val sum = _selectedNumbers.value.sumOf { _numbersGrid.value[it].value }
            val canConnect = with(Dispatchers.Default) { canConnect(_selectedNumbers.value[0], _selectedNumbers.value[1]) }
            if (sum == _targetSum.value && canConnect) {
                _resultMessage.value = "Đúng rồi! Tổng là $sum"
                _score.update { it + 10 }
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
        val nRows = 6
        val nCols = 6
        val grid = _numbersGrid.value.map { it.drawableId != 0 }
        val start = Pair(index1 / nCols, index1 % nCols)
        val end = Pair(index2 / nCols, index2 % nCols)

        // Dùng BFS để tìm đường nối giữa hai ô
        val directions = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))
        val queue = ArrayDeque<Pair<Int, Int>>()
        val trace = Array(nRows) { Array(nCols) { Pair(-1, -1) } }

        queue.add(start)
        trace[start.first][start.second] = Pair(-2, -2)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()

            if (current == end) break

            for (dir in directions) {
                var x = current.first + dir.first
                var y = current.second + dir.second

                while (x in 0 until nRows && y in 0 until nCols && grid[x * nCols + y]) {
                    if (trace[x][y].first == -1) {
                        trace[x][y] = current
                        queue.add(Pair(x, y))
                    }
                    x += dir.first
                    y += dir.second
                }
            }
        }

        // Duyệt ngược lại từ điểm kết thúc để kiểm tra đường đi
        var pathValid = false
        var steps = 0
        var point = end

        while (trace[point.first][point.second] != Pair(-2, -2)) {
            steps++
            point = trace[point.first][point.second]
        }

        if (point == start && steps <= 4) {
            pathValid = true
        }

        return pathValid
    }

    fun resetGame() {
        _numbersGrid.value = generateRandomNumbersGrid()
        _selectedNumbers.value = listOf()
        _resultMessage.value = null
        _score.value = 0
    }

    private fun generateRandomNumbersGrid(): List<NumberData> {
        return List(36) { NumberProvider.numberDataList.random() }
    }

    private fun generateTargetSum(): Int {
        return Random.nextInt(3, 18)
    }
}
