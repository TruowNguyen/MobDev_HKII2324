package com.example.a30daysofsth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a30daysofsth.Data.Information
import com.example.a30daysofsth.Data.infor
import com.example.a30daysofsth.ui.theme._30DaysOfSthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _30DaysOfSthTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    _30DaysOfSthApp()
                }
            }
        }
    }
}

@Composable
fun _30DaysOfSthApp() {

    val layoutDirection = LocalLayoutDirection.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing
                    .asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing
                    .asPaddingValues()
                    .calculateEndPadding(layoutDirection)
            )
    ){
        AppList(inforList = infor)
    }
}

@Composable
fun InformationCard(
    information: Information,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Card(modifier = modifier) {
        Column(modifier = modifier) {
            Text(
                text = "Day ${information.day}",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(8.dp)
            )
            ImageButton(
                expanded = expanded,
                onImageClick = { expanded = !expanded },
                imageResId = information.imageResourceId,
                contentDescription = stringResource(information.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp)
            )
            if (expanded) {
                Text(
                    text = stringResource(information.stringResourceId),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ImageButton(
    expanded: Boolean,
    onImageClick: () -> Unit,
    @DrawableRes imageResId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onImageClick,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
    }
}





@Composable
fun AppList(
    inforList: List<Information>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(inforList) { information ->
            InformationCard(
                information = information,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _30DaysOfSthTheme {
        _30DaysOfSthApp()
    }
}