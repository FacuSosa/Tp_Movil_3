package ar.edu.unlam.mobile.scaffold.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.ui.components.CustomButton
import ar.edu.unlam.mobile.scaffold.ui.components.CustomProgressBarWithDots
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxBackgroundImage

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewmodel = hiltViewModel(),
    navDuel: () -> Unit = { },
    navQuiz: () -> Unit = { },
    navMap: () -> Unit = { },
    navUsuario: () -> Unit = { },
    navCollection: () -> Unit = { },
    navDeck: () -> Unit = { },
    navQrScanner: () -> Unit = { }
) {
    val cacheProgress by viewModel.cachingProgress.collectAsStateWithLifecycle()

    val sensorData by viewModel.sensorData
        .collectAsStateWithLifecycle(initialValue = SensorData(0f, 0f))

    DisposableEffect(Unit) {
        onDispose {
            viewModel.cancelSensorDataFlow()
        }
    }

    HomeUi(
        modifier = modifier.testTag("home ui"),
        sensorData = { sensorData },
        navDuel = navDuel,
        navQuiz = navQuiz,
        navMap = navMap,
        navUsuario = navUsuario,
        navCollection = navCollection,
        navQrScanner = navQrScanner,
        navDeck = navDeck,
        cacheProgress = { cacheProgress }
    )
}

@Preview
@Composable
fun HomeUi(
    modifier: Modifier = Modifier,
    sensorData: () -> SensorData = { SensorData() },
    navDuel: () -> Unit = { },
    navQuiz: () -> Unit = { },
    navMap: () -> Unit = { },
    navUsuario: () -> Unit = { },
    navCollection: () -> Unit = { },
    navQrScanner: () -> Unit = { },
    cacheProgress: () -> Float = { 0f },
    navDeck: () -> Unit = {}
) {
    val navButtonModifier = Modifier
        .wrapContentSize()
        .padding(8.dp)

    Box(modifier = modifier) {
        ParallaxBackgroundImage(
            modifier = Modifier.testTag("background").fillMaxSize(),
            contentDescription = "Pantalla Coleccion",
            painterResourceId = R.drawable.pantalla_principal,
            data = sensorData
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CustomButton(
                    modifier = navButtonModifier.testTag("nav duel button"),
                    onClick = navDuel,
                    label = { "Duelo" }
                )

                CustomButton(
                    modifier = navButtonModifier.testTag("nav quiz button"),
                    onClick = navQuiz,
                    label = { "Quiz" }
                )
                CustomButton(
                    modifier = navButtonModifier.testTag("nav deck button"),
                    onClick = navDeck,
                    label = { "Mazos" }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CustomButton(
                    modifier = navButtonModifier,
                    onClick = navQrScanner,
                    label = { "Obtener heroe QR" }
                )
                CustomButton(
                    modifier = navButtonModifier.testTag("nav map button"),
                    onClick = navMap,
                    label = { "Mapa" }
                )
            }
            CustomButton(
                modifier = navButtonModifier.testTag("nav usuario button"),
                onClick = navUsuario,
                label = { "Usuario" }
            )
            CollectionButton(
                modifier = navButtonModifier,
                cacheProgress = cacheProgress,
                navCollection = navCollection
            )
        }
    }
}

@Composable
private fun CollectionButton(
    modifier: Modifier = Modifier,
    cacheProgress: () -> Float = { 0f },
    navCollection: () -> Unit = { }
) {
    if (cacheProgress() < 1f) {
        CustomProgressBarWithDots(modifier = modifier.testTag("progress bar"), progress = cacheProgress())
    } else {
        CustomButton(
            modifier = modifier.testTag("nav collection button"),
            onClick = navCollection,
            label = { "Coleccion" }
        )
    }
}
