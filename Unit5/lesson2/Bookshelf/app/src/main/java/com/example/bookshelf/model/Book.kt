package com.example.bookshelf.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
//Base URL
//https://www.googleapis.com/books/v1/volumes?q=
//outputs JSON
//https://www.googleapis.com/books/v1/volumes/<volume_id>
//Specific book
*/


@Serializable
data class BookList(
    val kind: String,
    val totalItems: Int,
    @SerialName(value = "items")
    val list: List<Volume>,
)


@Serializable
data class Volume(
    val id: String,
    val volumeInfo: Book,
    )

@Serializable
data class Book(
    val title: String,
    val imageLinks: Thumbnails,
) {
    val id: String = this.hashCode().toString()
}

@Serializable
data class Thumbnails(
    val smallThumbnail: String,
    val thumbnail: String,
)