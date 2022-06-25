package com.example.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.pokedex.MainActivity.Companion.IMAGE_TYPE_BOTTOM
import com.example.pokedex.MainActivity.Companion.IMAGE_TYPE_TOP
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.ui.theme.SubColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                MainContainer()
            }
        }
    }

    companion object {
        const val IMAGE_TYPE_TOP = "image_type_top"
        const val IMAGE_TYPE_BOTTOM = "image_type_bottom"
    }
}

@Composable
fun MainContainer() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            DexImage(IMAGE_TYPE_TOP)
            Box(
                modifier = Modifier
                    .background(SubColor)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                PokemonItem()
            }
            DexImage(IMAGE_TYPE_BOTTOM)
        }
    }
}

@Composable
fun DexImage(type: String, modifier: Modifier = Modifier) {

    val imageRes =
        if (type == IMAGE_TYPE_TOP) R.drawable.ic_dex_top else R.drawable.ic_dex_bottom

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "dex image",
        contentScale = ContentScale.FillWidth,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun PokemonItem(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(25.dp),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_ball),
            contentDescription = "dot Image",
            modifier = modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.size(20.dp))

        Text(
            text = "No.001",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = "피카츄",
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
        MainContainer()
    }
}