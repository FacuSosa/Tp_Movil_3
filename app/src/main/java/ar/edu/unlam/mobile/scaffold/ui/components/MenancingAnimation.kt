package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.R

@Composable
fun MenancingAnimation(
    modifier: Modifier = Modifier,
    content: @Composable (RowScope.() -> Unit) = {}
) {
    Row(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.size(40.dp))
        content()
        MenancingEffect(modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
private fun MenancingEffect(
    modifier: Modifier = Modifier
) {
    val painter = painterResource(id = R.drawable.menancing_image)
    val infiniteTransition = rememberInfiniteTransition("Jojo remember infinite transition")
    val scale by infiniteTransition.animateFloat(
        label = "Jojo animation",
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    Column(
        modifier = modifier.wrapContentSize()
    ) {
        Image(
            painter = painter,
            contentDescription = "Menancing 1",
            modifier = Modifier
                .size(width = 40.dp, height = 50.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
        )
        Image(
            painter = painter,
            contentDescription = "Menancing 2",
            modifier = Modifier
                .size(width = 30.dp, height = 38.dp)
                .graphicsLayer {
                    scaleX = 2.1f - scale
                    scaleY = 2.1f - scale
                }
        )
        Image(
            painter = painter,
            contentDescription = "Menancing 3",
            modifier = Modifier
                .size(width = 20.dp, height = 25.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
        )
    }
}
