package com.example.pokedex.view

import androidx.navigation.NavHostController

//// 네비게이션 라우트 액션
//class RouteAction(navHostController: NavHostController){
//
//    // 특정 라우트로 이동
//    val navTo: (NAV_ROUTE) -> Unit = { route ->
//        navHostController.navigate(route.routeName)
//    }
//
//    // 뒤로가기 이동
//    val gotoBack: () -> Unit = {
//        navHostController.navigateUp()
//    }
//}

class RouteAction(navHostController: NavHostController) {

    val navToDetail : (Int, String) -> Unit = { index, name ->
        navHostController.navigate("$DETAIL/$index/$name")
    }

    companion object {
        const val HOME = "home"
        const val DETAIL = "detail"
    }

}