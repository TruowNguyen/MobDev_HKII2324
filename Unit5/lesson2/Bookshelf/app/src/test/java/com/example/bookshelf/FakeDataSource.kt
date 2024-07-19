package com.example.bookshelf

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BookList
import com.example.bookshelf.model.Thumbnails
import com.example.bookshelf.model.Volume

object FakeDataSource {

    const val kind = "data"
    const val total = 123
    private val thumbnail = Thumbnails(
        smallThumbnail = "linksmall",
        thumbnail = "linkNormal"
    )
    private val book = Book(
        title = "title",
        imageLinks = thumbnail
    )

    private val list =
        listOf<Volume>(
            Volume(volumeInfo = book, id="1"),
            Volume(volumeInfo = book, id = "2")
    )
    val fakeList = BookList(
        kind = kind,
        totalItems = total,
        list = list
    )
}