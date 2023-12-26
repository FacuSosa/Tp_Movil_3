package ar.edu.unlam.mobile.scaffold.ui.screens.qrscanner

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.ui.components.CustomButton
import ar.edu.unlam.mobile.scaffold.ui.components.CustomCard
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTitle
import ar.edu.unlam.mobile.scaffold.ui.components.LoadingComposable
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroCard
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroStats

@Composable
fun QrScannerScreen(
    modifier: Modifier = Modifier,
    viewModel: QrScannerViewModel = hiltViewModel()
) {
    val startScan = viewModel::startScan
    val uiState by viewModel.qrScannerUIState.collectAsStateWithLifecycle()
    val heroQr by viewModel.heroQr.collectAsStateWithLifecycle()

    when (uiState) {
        QrScannerUiState.Cancelled -> {
            QrScannerUi(modifier = modifier, startScan = startScan)
            Toast.makeText(LocalContext.current, "Se canceló la lectura de QR", Toast.LENGTH_LONG).show()
        }
        is QrScannerUiState.Error -> QrFailureUi(
            modifier = modifier,
            startScan = startScan,
            failureMessage = (uiState as QrScannerUiState.Error).message
        )
        QrScannerUiState.Loading -> LoadingComposable(modifier = modifier)
        QrScannerUiState.QrSuccess -> QrSuccessUi(modifier = modifier, hero = heroQr)
        QrScannerUiState.Success -> QrScannerUi(modifier = modifier, startScan = startScan)
    }
}

@Preview(showBackground = true)
@Composable
fun QrScannerUi(
    modifier: Modifier = Modifier,
    startScan: () -> Unit = { }
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.fondo_coleccion),
            contentDescription = "QrScannerUi background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = 25.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTitle(
                text = { "Escaneo heroico QR" }
            )
            CustomCard(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "Aqui podras conseguir nuevos personajes, " +
                        "al escanear codigos QR generados por otros jugadores.",
                    textAlign = TextAlign.Center
                )
            }
            CustomButton(
                onClick = startScan,
                label = { "Comenzar" }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QrSuccessUi(
    modifier: Modifier = Modifier,
    hero: HeroModel = HeroModel()
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.fondo_coleccion),
            contentDescription = "QrSuccessUi background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = 25.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTitle(
                text = { "Nuevo personaje adquirido!!!!" }
            )
            HeroCard(
                hero = hero
            ) {
                HeroStats(
                    modifier = Modifier.padding(3.dp),
                    stats = { hero.stats }
                )
            }
            CustomTitle(
                modifier = Modifier.padding(5.dp),
                text = { "Cantidad actual: ${hero.quantity}" }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QrFailureUi(
    modifier: Modifier = Modifier,
    startScan: () -> Unit = { },
    failureMessage: String = ""
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.fondo_coleccion),
            contentDescription = "QrFailureUi background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = 25.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTitle(text = { "Algo salio mal" })
            CustomCard(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "Razón: $failureMessage",
                    textAlign = TextAlign.Center
                )
            }
            CustomButton(
                onClick = startScan,
                label = { "volver a intentarlo?" }
            )
        }
    }
}
