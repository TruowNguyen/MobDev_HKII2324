package com.example.mycity.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycity.model.MyCityUiState
import com.example.mycity.model.Recommendation

@Composable
fun MyCityCategoryRecommendationsScreen(
    myCityUiState: MyCityUiState,
    onRecommendationClicked: (recommendation: Recommendation) -> Unit,
) {
    Box(modifier = Modifier.padding(8.dp)) {

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            myCityUiState.selectedCategory?.let {
                items(it.recommendations) {
                    RecommendationItem(
                        recommendation = it,
                        onRecommendationClicked = onRecommendationClicked
                    )
                }
            }
        }
    }
}

@Composable
fun RecommendationItem(
    recommendation: Recommendation,
    onRecommendationClicked: (recommendation: Recommendation) -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(size = 8.dp),
        modifier = Modifier.clickable {
            onRecommendationClicked(recommendation)
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = recommendation.imageResourceId),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = recommendation.name),
                fontFamily = FontFamily.SansSerif,
                fontSize = 24.sp
            )
        }
    }
}