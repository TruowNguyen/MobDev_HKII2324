package com.example.mycity.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category(
    @StringRes val name: Int,
    @DrawableRes val imageResourceId: Int,
    val recommendations: List<Recommendation>
)

