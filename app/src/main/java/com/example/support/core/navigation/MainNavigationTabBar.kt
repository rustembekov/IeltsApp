package com.example.support.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.support.R
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors

data class BottomNavigationItem(
    val title: String,
    val navigator: NavigationItem,
    val iconId: Int
)

@Composable
fun MainNavigationTabBar(navController: NavHostController) {
    val bottomNavigationItems = listOf(
        BottomNavigationItem("Rating", NavigationItem.Rating, R.drawable.ic_rating),
        BottomNavigationItem("Home", NavigationItem.Home, R.drawable.ic_home),
        BottomNavigationItem("Profile", NavigationItem.Profile, R.drawable.ic_profile),
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background( brush = Brush.verticalGradient(
                colors = listOf(
                    colors.backgroundGradientSecond,
                    colors.backgroundGradientFirst
                )
            )),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(72.dp)
                .width(296.dp)
                .padding(bottom = 20.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .background(AppTheme.colors.tabBarSelectedBackground),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(27.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomNavigationItems.forEach { item ->
                    val isSelected = currentRoute == item.navigator.route
                    val iconColor = if (isSelected)
                        AppTheme.colors.tabBarSelectedPrimary
                    else
                        AppTheme.colors.tabBarSelectedSecondary

                    IconButton(
                        onClick = {
                            if (currentRoute != item.navigator.route) {
                                navController.navigate(item.navigator.route) {
                                    popUpTo(NavigationItem.Home.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(item.iconId),
                            contentDescription = item.title,
                            tint = iconColor
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
private fun MainNavigationTabBarPreview() {
    AppTheme(
        darkTheme = false
    ) {
        MainNavigationTabBar(
            navController = rememberNavController()
        )
    }
}