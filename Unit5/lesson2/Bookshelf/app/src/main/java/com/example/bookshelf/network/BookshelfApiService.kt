package com.example.bookshelf.network

import com.example.bookshelf.model.BookList
import retrofit2.http.GET



//retrofit instructions to API service
interface BookshelfApiService {
    @GET("?q=jazz+history")
    suspend fun getList(): BookList
}
