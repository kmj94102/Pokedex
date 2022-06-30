package com.example.pokedex.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.R
import com.example.pokedex.netwrok.data.getPokemonList
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.ui.theme.SubColor
import com.example.pokedex.util.Constants.getDotImage
import com.skydoves.landscapist.glide.GlideImage
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
}

@Composable
fun MainContainer(routeAction: RouteAction) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box {
            LazyColumn(
                modifier = Modifier
                    .background(SubColor)
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 137.dp)
            ) {
                itemsIndexed(getPokemonList()) { index, item ->
                    PokemonItem(index + 1, item, routeAction)
                }
            }
            DexImage(R.drawable.ic_dex_top)
            DexImage(
                res = R.drawable.ic_dex_bottom,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun DexImage(@DrawableRes res: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = res),
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
            .padding(25.dp, 5.dp)
            .clickable { routeAction.navToDetail(index, name) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            imageModel = getDotImage(index),
            loading = {
                Image(
                    painter = painterResource(id = R.drawable.ic_ball),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp).align(Alignment.Center)
                )
            },
            failure = {
            },
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