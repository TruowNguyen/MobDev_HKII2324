package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtSpaceApp() {
    val titles = listOf(
        R.string.title_artwork,
        R.string.title_artwork2
    )

    val descriptions = listOf(
        R.string.decripsion_artwork,
        R.string.decripsion_artwork2
    )

    val images = listOf(
        R.drawable.truongpic,
        R.drawable.spaceman_guitar
    )

    val currentArtIndex = remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name), fontWeight = FontWeight.Bold)
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            ArtSpaceImageAndText(
                titleArtWorkId = titles[currentArtIndex.value],
                descriptionArtWorkId = descriptions[currentArtIndex.value],
                imageId = images[currentArtIndex.value],
                onPreviousClick = {
                    if (currentArtIndex.value > 0) {
                        currentArtIndex.value--
                    } else {
                        currentArtIndex.value = titles.size - 1
                    }
                },
                onNextClick = {
                    if (currentArtIndex.value < titles.size - 1) {
                        currentArtIndex.value++
                    } else {
                        currentArtIndex.value = 0
                    }
                }
            )
        }
    }
}

@Composable
fun ArtSpaceImageAndText(
    titleArtWorkId: Int,
    descriptionArtWorkId: Int,
    imageId: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(imageId),
                contentDescription = stringResource(descriptionArtWorkId),
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = stringResource(titleArtWorkId),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = stringResource(descriptionArtWorkId),
                style = MaterialTheme.typography.bodyMedium,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Button(onClick = onPreviousClick) {
                    Text(text = "Previous")
                }
                Spacer(modifier = Modifier.width(32.dp))
                Button(onClick = onNextClick) {
                    Text(text = "Next")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}
