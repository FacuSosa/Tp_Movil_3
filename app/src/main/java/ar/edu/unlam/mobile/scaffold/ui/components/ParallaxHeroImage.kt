package ar.edu.unlam.mobile.scaffold.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroImage

// For changing offset values, it is always preferred to use .offset { } instead of .offset()
// as offset {..} is implemented to avoid recomposition during the offset changes

@SuppressLint("UnrememberedMutableState")
@Composable
fun ParallaxHeroImage(
    modifier: Modifier = Modifier,
    depthMultiplier: Int = 20,
    imageUrl: String = "https://loremflickr.com/400/400/cat?lock=1",
    data: () -> SensorData = { SensorData() },
    alpha: Float = 0.8f
) {
    val sensorData = data()
    val roll by derivedStateOf { sensorData.roll * depthMultiplier }
    val pitch by derivedStateOf { sensorData.pitch * depthMultiplier }

    Box(modifier = modifier) {
        // Glow Shadow
        // Has quicker offset change and in opposite direction to the Image Card
        HeroImage(
            url = imageUrl,
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = -(roll * 1.5).dp.roundToPx(),
                        y = (pitch * 2).dp.roundToPx()
                    )
                }
                .width(311.dp)
                .height(311.dp)
                .align(Alignment.Center)
                .blur(radius = 24.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded),
            contentScale = ContentScale.Crop,
            alpha = alpha
        )

        // Edge (used to give depth to card when tilted)
        // Has slightly slower offset change than Image Card
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = (roll * 0.9).dp.roundToPx(),
                        y = -(pitch * 0.9).dp.roundToPx()
                    )
                }
                .width(350.dp)
                .height(350.dp)
                .align(Alignment.Center)
                .background(
                    color = Color.White.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(16.dp)
                )
        )

        // Image Card
        // The image inside has a parallax shift in the opposite direction
        HeroImage(
            url = imageUrl,
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = roll.dp.roundToPx(),
                        y = -pitch.dp.roundToPx()
                    )
                }
                .width(350.dp)
                .height(350.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(16.dp)),
            alignment = BiasAlignment(
                horizontalBias = (roll * 0.005).toFloat(),
                verticalBias = 0f
            ),
            contentScale = ContentScale.Crop
        )
    }
}
