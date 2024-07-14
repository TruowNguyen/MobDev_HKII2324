package com.example.mycity.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Recommendation(
    @StringRes val name: Int,
    @StringRes val details: Int,
    @DrawableRes val imageResourceId: Int,
)
