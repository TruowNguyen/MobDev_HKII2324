package com.example.sumsmart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.sumsmart.navigation.NavGraph
import com.example.sumsmart.ui.theme.SumSmartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SumSmartTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SumSmartApp()
                }
            }
        }
    }
}

@Composable
fun SumSmartApp() {
    val navController = rememberNavController()
    NavGraph(navController = navController)
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SumSmartApp()
}