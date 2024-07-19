package com.example.bookshelf

import com.example.bookshelf.data.NetworkBookListRepository
import com.example.bookshelf.ui.BookshelfUiState
import com.example.bookshelf.ui.BookshelfViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun networkMarsPhotosRepository_getMarsPhotos_verifyPhotoList() =
        runTest {
            val repository = NetworkBookListRepository(
                bookshelfApiService = FakeBookshelfApiService()
            )
            assertEquals(FakeDataSource.fakeList, repository.getBookList())
        }

    @Test
    fun marsViewModel_getMarsPhotos_verifyMarsUiStateSuccess() =
        runTest{

            val bookshelfViewModel = BookshelfViewModel(
                bookListRepository = FakeNetworkBookshelfRepository()
            )

            assertEquals(
                BookshelfUiState.Success(list = FakeDataSource.fakeList),
                bookshelfViewModel.bookshelfUiState
            )
        }


}
