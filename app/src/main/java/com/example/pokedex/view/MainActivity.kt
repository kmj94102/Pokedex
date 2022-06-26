package com.example.pokedex.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex.netwrok.data.getPokemonList
import com.example.pokedex.view.MainActivity.Companion.IMAGE_TYPE_BOTTOM
import com.example.pokedex.view.MainActivity.Companion.IMAGE_TYPE_TOP
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.ui.theme.SubColor
import com.example.pokedex.util.Constants.getDotImage
import com.example.pokedex.util.Constants.makeToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                NavigationGraph()
            }
        }
    }

    companion object {
        const val IMAGE_TYPE_TOP = "image_type_top"
        const val IMAGE_TYPE_BOTTOM = "image_type_bottom"
    }
}

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    val routeAction = remember(navController) {
        RouteAction(navController)
    }

    NavHost(navController = navController, startDestination = RouteAction.HOME ) {
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

@Composable
fun MainContainer(routeAction: RouteAction) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            DexImage(IMAGE_TYPE_TOP)
            LazyColumn(
                modifier = Modifier
                    .background(SubColor)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                itemsIndexed(getPokemonList()) { index, item ->
                    PokemonItem(index+1, item, routeAction)
                }
            }
            DexImage(IMAGE_TYPE_BOTTOM)
        }
    }
}

@Composable
fun DexImage(type: String, modifier: Modifier = Modifier) {

    val imageRes =
        if (type == IMAGE_TYPE_TOP)
            com.example.pokedex.R.drawable.ic_dex_top
        else
            com.example.pokedex.R.drawable.ic_dex_bottom

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "dex image",
        contentScale = ContentScale.FillWidth,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun PokemonItem(index: Int, name: String, routeAction: RouteAction, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(25.dp)
            .clickable { routeAction.navToDetail(index, name) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        PokemonImage(
            url = getDotImage(index),
            modifier = modifier.size(70.dp)
        )

        Spacer(modifier = Modifier.size(20.dp))

        Text(
            text = "No.${index.toString().padStart(3, '0')}",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = name,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokedexTheme {
        NavigationGraph()
    }
}