package com.example.bookshelf.data

import com.example.bookshelf.model.BookList
import com.example.bookshelf.network.BookshelfApiService

// 1. This interface directs the functions over a repository
interface BookListRepository {
    suspend fun getBookList(): BookList
}

// 2. This overrides the interface to perform the retrofit commands instead
class NetworkBookListRepository(
private val bookshelfApiService: BookshelfApiService
): BookListRepository {
    override suspend fun getBookList(): BookList {
        return bookshelfApiService.getList()
    }
}