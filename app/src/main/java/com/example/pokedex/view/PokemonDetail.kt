package com.example.pokedex.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.pokedex.R
import com.example.pokedex.netwrok.data.PokemonInfoResult
import com.example.pokedex.netwrok.data.getStatusColor
import com.example.pokedex.ui.theme.SubColor
import com.example.pokedex.util.Constants.getDetailImage

// 상세 화면 관련 Compose
@Composable
fun DetailContainer(index: Int, name: String, viewModel: MainViewModel = hiltViewModel()) {
    val pokemonInfo by viewModel.pokomonInfoFlow.collectAsState()
    val scrollState = rememberScrollState()

    viewModel.getPokemonInfo(index)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column {
            DexImage(MainActivity.IMAGE_TYPE_TOP)
            Column(
                modifier = Modifier
                    .background(SubColor)
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PokemonInfoImage(index, name)
                PokemonStatus(pokemonInfo)
            }
            DexImage(MainActivity.IMAGE_TYPE_BOTTOM)
        }
    }
}

@Composable
fun PokemonInfoImage(index: Int, name: String) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.img_character_effect),
            contentDescription = "circle",
            modifier = Modifier.size(360.dp)
        )
        PokemonImage(url = getDetailImage(index), modifier = Modifier.padding(55.dp))
        Text(
            modifier = Modifier.padding(0.dp, 25.dp),
            text = name,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun PokemonImage(url: String, modifier: Modifier = Modifier) {
    val bitmap: MutableState<Bitmap?> = remember {
        mutableStateOf(null)
    }

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(url)
        .placeholder(R.drawable.ic_ball)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })

    bitmap.value?.asImageBitmap()?.let {
        Image(
            bitmap = it,
            contentDescription = "detail image",
            modifier = modifier.size(250.dp),
        )
    } ?: run {
        Image(
            painter = painterResource(id = R.drawable.ic_ball),
            contentDescription = "empty Image",
            modifier = modifier.size(250.dp)
        )
    }

}

@Composable
fun PokemonStatus(pokemonInfo: PokemonInfoResult) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.status),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )

        for((key, status) in pokemonInfo.status) {
            StatusBar(key, status.toFloat(), getStatusColor(key))
        }
    }
}

@Composable
fun StatusBar(name: String, status: Float, color: Color, modifier: Modifier = Modifier) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = name,
            modifier = Modifier.weight(3f)
        )

        LinearProgressIndicator(
            modifier = modifier
                .height(20.dp)
                .weight(10f)
                .clip(RoundedCornerShape(20.dp)),
            progress = status / 150,
            color = color
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = "${status.toInt()}",
            modifier = Modifier
                .weight(2f),
            textAlign = TextAlign.Center
        )
    }
}