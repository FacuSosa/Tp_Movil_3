package ar.edu.unlam.mobile.scaffold.ui.screens.collection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxBackgroundImage
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroGallery

@Composable
fun CollectionScreen(
    modifier: Modifier = Modifier,
    navigateToHeroDetail: (Int) -> Unit = {},
    viewModel: CollectionViewModelImp = hiltViewModel()
) {
    val sensorData by viewModel.sensorData
        .collectAsStateWithLifecycle(initialValue = SensorData(0f, 0f))

    DisposableEffect(Unit) {
        onDispose {
            viewModel.cancelSensorDataFlow()
        }
    }

    Box(modifier = modifier) {
        ParallaxBackgroundImage(
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Pantalla Coleccion",
            painterResourceId = R.drawable.fondo_coleccion,
            data = { sensorData }
        )

        val isLoading = viewModel.isLoading.collectAsStateWithLifecycle()

        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            HeroGallery(
                modifier = Modifier.fillMaxSize(),
                heroList = viewModel.heroList.collectAsStateWithLifecycle().value,
                onItemClick = navigateToHeroDetail
            )
        }
    }
}
