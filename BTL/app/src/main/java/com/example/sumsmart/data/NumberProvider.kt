package com.example.sumsmart.data

import com.example.sumsmart.R

data class NumberData(
    val drawableId: Int,
    val value: Int
)

object NumberProvider {
    val numberDataList = listOf(
        NumberData(R.drawable.number_1, 1),
        NumberData(R.drawable.number_2, 2),
        NumberData(R.drawable.number_3, 3),
        NumberData(R.drawable.number_4, 4),
        NumberData(R.drawable.number_5, 5),
        NumberData(R.drawable.number_6, 6),
        NumberData(R.drawable.number_7, 7),
        NumberData(R.drawable.number_8, 8),
        NumberData(R.drawable.number_9, 9)
    )
}

