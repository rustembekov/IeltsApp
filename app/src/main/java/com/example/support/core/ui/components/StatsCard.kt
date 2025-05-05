package com.example.support.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.R
import com.example.support.core.ui.AppTheme

@Composable
fun StatsCard(
    ranking: String,
    points: String
) {
    Card(
        modifier = Modifier
            .height(92.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.homeItemPrimary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            AppTheme.colors.backgroundGradientFirst,
                            AppTheme.colors.backgroundGradientSecond.copy(alpha = 0.5f)
                        )
                    ),
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_ranking),
                        contentDescription = "Ranking",
                        modifier = Modifier.size(54.dp),)
                    Column(modifier = Modifier) {
                        Text(
                            text = stringResource(R.string.ranking),
                            color = AppTheme.colors.homeTextPrimary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = ranking,
                            color = Color(0xffF5BA46),
                            style = MaterialTheme.typography.displayMedium
                        )
                    }
                }

                VerticalDivider(
                    modifier = Modifier
                        .height(51.5.dp)
                        .width(2.dp),
                    color = Color.LightGray
                )

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_points),
                        contentDescription = "Points",
                        modifier = Modifier.size(54.dp)
                    )
                    Column {
                        Text(
                            text = stringResource(R.string.points),
                            color = AppTheme.colors.homeTextPrimary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = points,
                            color = Color(0xffF5BA46),
                            style = MaterialTheme.typography.displayMedium
                        )
                    }
                }
            }
        }

    }
}

@Preview(
    showBackground = false
)
@Composable
private fun StatsCardPreview() {
    AppTheme {
        StatsCard(
            ranking = "560",
            points = "1635"
        )
    }
}