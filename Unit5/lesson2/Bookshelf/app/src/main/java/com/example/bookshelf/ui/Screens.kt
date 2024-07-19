@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.example.bookshelf.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BookList
import com.example.bookshelf.model.Thumbnails
import com.example.bookshelf.ui.theme.BookshelfTheme

@Composable
fun HomeScreen(
    bookshelfUiState: BookshelfUiState,
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,
) {
    when (bookshelfUiState) {
        is BookshelfUiState.Success ->
            ResultScreen(
                bookshelfUiState.list,
                modifier = modifier.fillMaxWidth()
            )

        is BookshelfUiState.Loading ->
            LoadingScreen(modifier = modifier.fillMaxWidth())

        is BookshelfUiState.Error ->
            ErrorScreen(modifier = modifier.fillMaxWidth(), msg = bookshelfUiState.errorMsg, retryAction = retryAction)
    }
}

@Composable
fun ErrorScreen(modifier: Modifier, msg: String, retryAction: () -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Text(text = msg, modifier = Modifier.padding(16.dp))

    }
}

@Composable
fun LoadingScreen(modifier: Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        ), label = ""
    )
    Image(
        modifier = modifier.size(200.dp)
            .graphicsLayer {
            rotationZ = angle
        },
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ResultScreen(
    bookList: BookList,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        val books = bookList.list
        val newBooks =
            books.map { Book(title = it.volumeInfo.title, imageLinks = it.volumeInfo.imageLinks, ) }
        GridScreen(books = newBooks)
    }
}

@Composable
fun GridScreen(books: List<Book>, modifier: Modifier = Modifier) {
    Column {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(items = books, key = {book -> book.id}
            ) { book ->
                PhotoCard(book = book,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                )
            }

        }
    }
}

@Composable
fun PhotoCard(book: Book, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)

    ) {
        val imgSrc = book.imageLinks.thumbnail.replace("http", "https")
        val title = book.title
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row {

                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(imgSrc)
                        .crossfade(true)
                        .build(),
                    error = painterResource(R.drawable.ic_broken_image),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()

                )
            }
            Row (
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight()
            ){
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                        .fillMaxSize()
                        .wrapContentHeight(),

                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestCard() {
    PhotoCard(
        book = Book(
            title = "The Title",
            imageLinks = Thumbnails(
                smallThumbnail = "http://books.google.com/books/content?id=ubERDAAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
                thumbnail = "http://books.google.com/books/content?id=ubERDAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PhotosGridScreenPreview() {
    BookshelfTheme {
        val mockData = List(10) { Book("$it", Thumbnails("","")) }
        GridScreen(mockData)
    }
}