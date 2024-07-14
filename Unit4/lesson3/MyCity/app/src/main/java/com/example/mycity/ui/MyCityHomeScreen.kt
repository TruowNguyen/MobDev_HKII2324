package com.example.mycity.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.example.mycity.datasource.dataSource
import com.example.mycity.model.Category
import com.example.mycity.model.MyCityUiState
import com.example.mycity.model.Recommendation
import com.example.mycity.utils.MyCityContentType


@Composable
fun MyCityHomeScreen(
    onCategoryClicked: (category: Category) -> Unit,
    onRecommendationClicked: (recommendation: Recommendation) -> Unit,
    contentType: MyCityContentType,
    myCityUiState: MyCityUiState
) {

    val cityCategories = dataSource.cityCatories
    Row(modifier = Modifier.padding(8.dp)) {

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
            items(cityCategories) {
                MyCityCategoryItem(category = it, onCategoryClicked = onCategoryClicked)
            }
        }

        if(contentType == MyCityContentType.triple){

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
                myCityUiState.selectedCategory?.let {
                    items(it.recommendations) {
                        RecommendationItem(
                            recommendation = it,
                            onRecommendationClicked = onRecommendationClicked
                        )
                    }
                }
            }

            //MyCityCategoryRecommendationsScreen(myCityUiState = myCityUiState, onRecommendationClicked = onRecommendationClicked)
            myCityUiState.selectedReccomendation?.let { MyCityCategoryRecommendationDetailsScreen(recommendation = it, modifier = Modifier.weight(1f)) }
        }
    }
}

@Composable
fun MyCityCategoryItem(category: Category, onCategoryClicked: (category: Category) -> Unit) {

    Card(elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(size = 8.dp),
        modifier = Modifier.clickable {
            onCategoryClicked(category)
        }) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = category.imageResourceId),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = category.name),
                fontFamily = FontFamily.SansSerif,
                fontSize = 24.sp
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MyCityHomeScreenPreview() {
//    MyCityHomeScreen(onCategoryClicked = { })
//}
