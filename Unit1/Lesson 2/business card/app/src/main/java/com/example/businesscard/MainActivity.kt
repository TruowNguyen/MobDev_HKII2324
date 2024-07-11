package com.example.businesscard

import android.icu.text.CaseMap.Title
import android.icu.text.CaseMap.toTitle
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusinessCardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BusinessCard()
                }
            }
        }
    }
}

@Composable
fun BusinessCard() {
    Infomation(
        name = stringResource(R.string.full_name),
        age = 20,
        email = stringResource(R.string.email_address),
        imagePainter = painterResource(R.drawable.truongpic),
        school = stringResource(R.string.university)
    )

}

@Composable
fun Infomation(
    name: String,
    age : Int,
    email: String,
    imagePainter: Painter,
    school: String,
    modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Image(
            painter = imagePainter,
            contentDescription = null)
        Text(
            text = "Full Name: $name",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "Age: $age",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "Email: $email",
            fontSize = 20.sp, modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "School: $school",
            fontSize = 20.sp, modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BusinessCardTheme {
        BusinessCard()
    }
}