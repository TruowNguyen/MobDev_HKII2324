package com.example.mycity.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycity.datasource.dataSource
import com.example.mycity.model.Recommendation
import com.example.mycity.ui.theme.MyCityTheme

@Composable
fun MyCityCategoryRecommendationDetailsScreen(recommendation: Recommendation, modifier: Modifier) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(state = ScrollState(0), enabled = true)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = recommendation.imageResourceId),
            contentDescription = null,
            alignment = Alignment.Center,
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text(stringResource(id = recommendation.details))
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MyCityTheme {
        MyCityCategoryRecommendationDetailsScreen(
            recommendation = dataSource.cityCatories.get(0).recommendations.get(
                0
            ),
            modifier = Modifier
        )
    }
}