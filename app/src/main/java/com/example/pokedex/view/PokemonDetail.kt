package com.example.pokedex.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedex.R
import com.example.pokedex.netwrok.data.PokemonInfoResult
import com.example.pokedex.netwrok.data.getStatusColor
import com.example.pokedex.ui.theme.SubColor
import com.example.pokedex.util.Constants.getDetailImage
import com.example.pokedex.util.Constants.getShinyImage
import com.skydoves.landscapist.glide.GlideImage

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
        Box {
            Column(
                modifier = Modifier
                    .background(SubColor)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(0.dp, 137.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                PokemonInfoImage(index, name)
                PokemonStatus(pokemonInfo)
                PokemonShinyImage(index)
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
fun PokemonInfoImage(index: Int, name: String) {

    val infiniteTransition = rememberInfiniteTransition()
    val rotationAngle = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 4000,
                delayMillis = 0,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
    )
    val circleSize by infiniteTransition.animateFloat(
        initialValue = 100.0f,
        targetValue = 300.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                delayMillis = 0,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color(0x805282F7))
                .size(circleSize.dp)
                .align(Alignment.Center)
        )
        Image(
            painter = painterResource(id = R.drawable.img_character_effect),
            contentDescription = "circle",
            modifier = Modifier
                .size(360.dp)
                .rotate(rotationAngle.value)
        )

        GlideImage(
            imageModel = getDetailImage(index),
            loading = {
                CircularProgressIndicator()
            },
            failure = {
                Text(text = "이미지 로드에 실패하였습니다.")
            },
            modifier = Modifier.padding(55.dp)
        )

        Text(
            modifier = Modifier.padding(20.dp, 25.dp)
                .align(Alignment.TopStart),
            text = name,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
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

        for ((key, status) in pokemonInfo.status) {
            StatusBar(key, status.toFloat(), getStatusColor(key))
        }
    }
}

@Composable
fun PokemonShinyImage(index: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 20.dp, 20.dp, 30.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.shiny),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )
        GlideImage(
            imageModel = getShinyImage(index),
            loading = { CircularProgressIndicator() },
            failure = {
                Text(text = "이미지 로드에 실패하였습니다.")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun StatusBar(name: String, status: Float, color: Color, modifier: Modifier = Modifier) {
    Row(modifier = Modifier.fillMaxWidth()) {
        var isRotated by rememberSaveable { mutableStateOf(false) }
        val rotationAngle by animateFloatAsState(
            targetValue = if (isRotated) status / 150 else 0.0f,
            animationSpec = tween(durationMillis = 2500)
        )
        val onClickAction = remember(Unit) {
            {
                isRotated = !isRotated
            }
        }

        LaunchedEffect(Unit) {
            onClickAction()
        }
        Text(
            text = name,
            modifier = Modifier.weight(3f)
        )
        LinearProgressIndicator(
            modifier = modifier
                .height(20.dp)
                .weight(10f)
                .clip(RoundedCornerShape(20.dp)),
            progress = rotationAngle,
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