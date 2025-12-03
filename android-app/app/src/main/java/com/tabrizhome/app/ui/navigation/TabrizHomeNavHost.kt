package com.tabrizhome.app.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tabrizhome.app.ui.screens.PropertyDetailScreen
import com.tabrizhome.app.ui.screens.PropertyListScreen
import com.tabrizhome.app.ui.viewmodel.PropertyDetailViewModel
import com.tabrizhome.app.ui.viewmodel.PropertyListViewModel

private const val ROUTE_HOME = "home"
private const val ROUTE_DETAIL = "detail/{id}"

@Composable
fun TabrizHomeNavHost(
    padding: PaddingValues,
    layoutDirection: LayoutDirection,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = ROUTE_HOME,
        modifier = Modifier
    ) {
        composable(ROUTE_HOME) {
            val vm: PropertyListViewModel = hiltViewModel()
            PropertyListScreen(
                viewModel = vm,
                padding = padding,
                layoutDirection = layoutDirection,
                onPropertyClick = { id -> navController.navigate("detail/$id") }
            )
        }
        composable(ROUTE_DETAIL) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: return@composable
            val vm: PropertyDetailViewModel = hiltViewModel()
            PropertyDetailScreen(
                id = id,
                viewModel = vm,
                layoutDirection = layoutDirection,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
