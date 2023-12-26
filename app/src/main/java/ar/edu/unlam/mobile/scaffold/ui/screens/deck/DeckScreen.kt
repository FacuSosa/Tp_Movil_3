package ar.edu.unlam.mobile.scaffold.ui.screens.deck

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.domain.model.DeckModel
import ar.edu.unlam.mobile.scaffold.ui.components.CustomButton
import ar.edu.unlam.mobile.scaffold.ui.components.ListaMazosPersonalizados
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroImage
import ar.edu.unlam.mobile.scaffold.ui.components.hero.playerCardColor

@Composable
fun DeckScreen(
    modifier: Modifier = Modifier,
    viewModel: DeckViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val listDeck by viewModel.listDeck.collectAsStateWithLifecycle()
    val randomDeck by viewModel.randomDeck.collectAsStateWithLifecycle()
    val pantallaActual by viewModel.pantallaActual.collectAsStateWithLifecycle()
    val irACrearMazos = viewModel::irAGenerarMazos
    val generarMazo = viewModel::generarMazoRandom
    val regresarAlListado = viewModel::irAListaDeMazos
    val guardarMazo = viewModel::guardarMazoRandom

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        when (pantallaActual) {
            DeckUI.LISTA_DE_MAZOS -> DeckListUI(
                modifier = modifier,
                listaDeMazos = listDeck,
                irACrearMazos = irACrearMazos
            )

            DeckUI.GENERAR_MAZOS -> GenerarDeckUI(
                modifier = modifier,
                deck = randomDeck,
                generarMazo = generarMazo,
                regresarAlListado = regresarAlListado,
                guardarMazo = guardarMazo
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeckListUI(
    modifier: Modifier = Modifier,
    listaDeMazos: List<DeckModel> = emptyList(),
    irACrearMazos: () -> Unit = {}
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.fondo_mazo_azul),
            contentDescription = "deck background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = 5.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomButton(
                modifier = Modifier.padding(8.dp),
                label = { "Crear Nuevo Mazo" },
                onClick = irACrearMazos
            )
            ListaMazosPersonalizados(
                modifier = Modifier.fillMaxWidth(),
                deckList = listaDeMazos
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GenerarDeckUI(
    modifier: Modifier = Modifier,
    deck: DeckModel = DeckModel(id = 0),
    generarMazo: () -> Unit = {},
    regresarAlListado: () -> Unit = {},
    guardarMazo: () -> Unit = {}
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo_mazo_azul),
            contentDescription = "deck background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CustomButton(
                    modifier = Modifier.weight(1f),
                    label = { "Mazo Aleatoreo" },
                    onClick = generarMazo
                )
                CustomButton(
                    modifier = Modifier.weight(1f),
                    label = { "Guardar Mazo" },
                    onClick = guardarMazo
                )
            }
            CuadriculaImagen(deck = deck)
            CustomButton(
                modifier = Modifier.padding(8.dp),
                label = { "Regresar al Listado" },
                onClick = regresarAlListado
            )
        }
    }
}

@Composable
fun CuadriculaImagen(
    modifier: Modifier = Modifier,
    deck: DeckModel = DeckModel(id = 0),
    cardColors: CardColors = playerCardColor()
) {
    val imagemMod = Modifier.size(170.dp)
        .padding(8.dp)
    Column(modifier = modifier.padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = modifier
                    .padding(8.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RectangleShape),
                colors = cardColors,
                shape = RectangleShape
            ) {
                HeroImage(modifier = imagemMod, url = deck.carta1.image.url)
            }
            ElevatedCard(
                modifier = modifier
                    .padding(8.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RectangleShape),
                colors = cardColors,
                shape = RectangleShape
            ) {
                HeroImage(modifier = imagemMod, url = deck.carta2.image.url)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = modifier
                    .padding(8.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RectangleShape),
                colors = cardColors,
                shape = RectangleShape
            ) {
                HeroImage(modifier = imagemMod, url = deck.carta3.image.url)
            }
            ElevatedCard(
                modifier = modifier
                    .padding(8.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RectangleShape),
                colors = cardColors,
                shape = RectangleShape
            ) {
                HeroImage(modifier = imagemMod, url = deck.carta4.image.url)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = modifier
                    .padding(8.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RectangleShape),
                colors = cardColors,
                shape = RectangleShape
            ) {
                HeroImage(modifier = imagemMod, url = deck.carta5.image.url)
            }
            ElevatedCard(
                modifier = modifier
                    .padding(8.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RectangleShape),
                colors = cardColors,
                shape = RectangleShape
            ) {
                HeroImage(modifier = imagemMod, url = deck.carta6.image.url)
            }
        }
    }
}
