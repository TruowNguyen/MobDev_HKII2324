package com.example.bookshelf

import com.example.bookshelf.model.BookList
import com.example.bookshelf.data.BookListRepository

class FakeNetworkBookshelfRepository: BookListRepository {
    override suspend fun getBookList(): BookList {
        return FakeDataSource.fakeList
    }
}