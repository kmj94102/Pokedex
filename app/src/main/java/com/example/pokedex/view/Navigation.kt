package com.example.pokedex.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex.util.Constants.makeToast

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    val routeAction = remember(navController) {
        RouteAction(navController)
    }

    NavHost(navController = navController, startDestination = RouteAction.HOME) {
        composable(RouteAction.HOME) {
            MainContainer(routeAction)
        }
        composable(
            route = "${RouteAction.DETAIL}/{index}/{name}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType },
                navArgument("name") { type = NavType.StringType }
            )
        ) { entry ->
            val index = entry.arguments?.getInt("index")
            val name = entry.arguments?.getString("name")

            if (index == null || name == null) {
                LocalContext.current.makeToast("오류가 발생하였습니다.")
                return@composable
            }

            DetailContainer(index, name)
        }
    }
}

class RouteAction(navHostController: NavHostController) {

    val navToDetail : (Int, String) -> Unit = { index, name ->
        navHostController.navigate("$DETAIL/$index/$name")
    }

    companion object {
        const val HOME = "home"
        const val DETAIL = "detail"
    }

}