package com.example.bookshelf.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.model.BookList
import com.example.bookshelf.data.BookListRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

//states for uistate
sealed interface BookshelfUiState {
    data class Success(val list: BookList) : BookshelfUiState
    data class Error(val errorMsg: String) : BookshelfUiState
    object Loading : BookshelfUiState
}

/**
 * ViewModel containing the app data and method to retrieve the data
 */
class BookshelfViewModel(
    private val bookListRepository: BookListRepository
) : ViewModel() {

    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    init {
        getBookList()
    }

    fun getBookList() {
        viewModelScope.launch {
            try {
                val listResult = bookListRepository.getBookList()
                bookshelfUiState = BookshelfUiState.Success(listResult)
            } catch (e: IOException) {
                bookshelfUiState = BookshelfUiState.Error("$e")
            } catch (e: HttpException){
                bookshelfUiState = BookshelfUiState.Error("$e")
            }

        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val bookListRepository = application.container.bookListRepository
                BookshelfViewModel(bookListRepository = bookListRepository)
            }
        }
    }



}