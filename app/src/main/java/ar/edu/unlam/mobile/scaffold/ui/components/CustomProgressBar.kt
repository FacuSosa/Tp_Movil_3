package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.ui.theme.shaka_pow

@Preview
@Composable
fun CustomProgressBar(
    modifier: Modifier = Modifier,
    progress: Float = 0.6f,
    height: Dp = 40.dp,
    backgroundColor: Color = Color.Cyan,
    foregroundColor: Color = Color.Yellow,
    content: @Composable (BoxScope.() -> Unit) = {}
) {
    var barProgress by remember {
        mutableStateOf(0f)
    }
    val size by animateFloatAsState(
        targetValue = barProgress,
        tween(
            durationMillis = 1000,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        label = "Custom Progress Bar Animation"
    )
    // Progress Bar
    Box(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(30.dp))
    ) {
        // background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(30.dp))
                .background(backgroundColor)
        )
        // foreground
        Box(
            modifier = Modifier
                .fillMaxWidth(size)
                .fillMaxHeight()
                .clip(RoundedCornerShape(30.dp))
                .background(foregroundColor)
                .animateContentSize()
        )
        content()
    }
    LaunchedEffect(key1 = progress) {
        barProgress = progress
    }
}

@Composable
fun CustomProgressBarWithDots(
    modifier: Modifier = Modifier,
    progress: Float = 0f
) {
    CustomProgressBar(modifier = modifier, progress = progress) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val cacheProgress = "%.2f".format(progress * 100f)
            Text(
                modifier = Modifier.padding(top = 9.dp),
                text = "Loading: $cacheProgress%",
                fontFamily = shaka_pow
            )
            LoadingAnimation(
                modifier = Modifier
                    .padding(start = 10.dp, top = 15.dp),
                circleSize = 15.dp,
                spaceBetween = 5.dp,
                travelDistance = 15.dp
            )
        }
    }
}
