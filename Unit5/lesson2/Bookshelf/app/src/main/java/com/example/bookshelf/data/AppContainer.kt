package com.example.bookshelf.data

import com.example.bookshelf.network.BookshelfApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val bookListRepository: BookListRepository
}

class DefaultAppContainer: AppContainer {
    private val baseUrl =
        "https://www.googleapis.com/books/v1/volumes/"

    //Create a retrofit object
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json{ignoreUnknownKeys=true}.asConverterFactory("application/json".toMediaType()))//makes it return a string OR JSON
        .baseUrl(baseUrl)
        .build()

    val retrofitService : BookshelfApiService by lazy {
        retrofit.create(BookshelfApiService::class.java)
    }

    override val bookListRepository: BookListRepository by lazy {
        NetworkBookListRepository(retrofitService)
    }

}