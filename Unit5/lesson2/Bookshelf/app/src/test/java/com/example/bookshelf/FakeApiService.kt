package com.example.bookshelf

import com.example.bookshelf.model.BookList
import com.example.bookshelf.network.BookshelfApiService

class FakeBookshelfApiService : BookshelfApiService {
    override suspend fun getList(): BookList {
        return FakeDataSource.fakeList
    }

}