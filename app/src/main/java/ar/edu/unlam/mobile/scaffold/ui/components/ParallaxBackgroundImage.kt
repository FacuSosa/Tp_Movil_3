package ar.edu.unlam.mobile.scaffold.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun ParallaxBackgroundImage(
    modifier: Modifier = Modifier,
    contentDescription: String = "Parallax background image",
    painterResourceId: Int = R.drawable.pantalla_principal,
    data: () -> SensorData = { SensorData() },
    strength: Int = 20,
    scale: Float = 1.15f
) {
    val sensorData = data()
    val roll by derivedStateOf { (sensorData.roll) }
    val pitch by derivedStateOf { (sensorData.pitch) }
    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = roll * strength
                    translationY = -pitch * strength
                }
                .align(Alignment.Center),
            painter = painterResource(id = painterResourceId),
            contentDescription = contentDescription,
            contentScale = ContentScale.FillBounds
        )
    }
}
