package com.synkroniqsdk.sampleapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.synkroniqsdk.sampleapp.ui.screens.ArticlesScreen
import com.synkroniqsdk.sampleapp.ui.screens.CategoriesScreen
import com.synkroniqsdk.sampleapp.ui.screens.TicketsScreen
import com.synkroniqsdk.sampleapp.ui.theme.*

data class NavTab(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun AppNavigation() {

    val tabs = listOf(
        NavTab(Screen.Categories.route, "Categories", Icons.Default.List),
        NavTab(Screen.Tickets.route,    "Tickets",    Icons.Default.ConfirmationNumber),
        NavTab(Screen.Articles.route,   "Articles",   Icons.Default.Article)
    )

    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            NavigationBar(containerColor = BackgroundGray) {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        selected = currentRoute == tab.route,
                        onClick = {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState    = true
                            }
                        },
                        icon  = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor   = SynkroniqBlue,
                            selectedTextColor   = SynkroniqBlue,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary,
                            indicatorColor      = SynkroniqBlue.copy(alpha = 0.12f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = "categories") {
            composable(Screen.Categories.route,) {
                CategoriesScreen(Modifier.padding(innerPadding))
            }
            composable(Screen.Tickets.route,)    {
                TicketsScreen(Modifier.padding(innerPadding))
            }
            composable(Screen.Articles.route,)   {
                ArticlesScreen(Modifier.padding(innerPadding))
            }
        }
    }
}