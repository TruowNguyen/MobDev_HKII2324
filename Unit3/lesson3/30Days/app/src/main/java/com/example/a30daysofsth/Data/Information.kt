package com.example.a30daysofsth.Data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.a30daysofsth.R

data class Information(
    val day: Int,
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)

val infor = listOf(
    Information(1, R.string.affirmation1, R.drawable.image1),
    Information(2, R.string.affirmation2, R.drawable.image2),
    Information(3, R.string.affirmation3, R.drawable.image3),
    Information(4, R.string.affirmation4, R.drawable.image4),
    Information(5, R.string.affirmation5, R.drawable.image5),
    Information(6, R.string.affirmation6, R.drawable.image6),
    Information(7, R.string.affirmation7, R.drawable.image8),
    Information(8, R.string.affirmation9, R.drawable.image9),
    Information(9, R.string.affirmation10, R.drawable.image10)
)