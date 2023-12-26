package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.domain.model.DeckModel
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroImage
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroStats
import ar.edu.unlam.mobile.scaffold.ui.components.hero.playerCardColor

@Preview(showBackground = true)
@Composable
fun PlayerDeck(
    modifier: Modifier = Modifier,
    playerDeck: List<HeroModel> = listOf(HeroModel(), HeroModel(), HeroModel()),
    onCardClick: (Int) -> Unit = {}
) {
    val deckSize = remember {
        playerDeck.size
    }
    // lazyColumn porque en un futuro un mazo va a tener más de tres cartas.
    LazyColumn(
        modifier = modifier,
        content = {
            items(deckSize) { i ->
                DeckItem(
                    modifier = Modifier.padding(8.dp),
                    onClick = { onCardClick(i) },
                    hero = { playerDeck[i] }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DeckItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    hero: () -> HeroModel = { HeroModel() }
) {
    CustomHorizontalClickeableCard(
        modifier = modifier.border(width = 2.dp, color = Color.Black, shape = RectangleShape),
        onClick = onClick
    ) {
        HeroImage(
            modifier = Modifier
                .size(100.dp)
                .padding(4.dp)
                .aspectRatio(ratio = 1f),
            contentScale = ContentScale.Crop,
            url = hero().image.url
        )
        HeroStats(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            stats = { hero().stats }
        )
    }
}

@Composable
fun ListaMazosPersonalizados(
    modifier: Modifier = Modifier,
    deckList: List<DeckModel> = emptyList()
) {
    val tamanioLista = deckList.size
    LazyColumn(
        modifier = modifier,
        content = {
            items(tamanioLista) { i ->
                MazoPersonalizado(
                    modifier = Modifier.padding(8.dp),
                    listaURL = obtenerLista(deckList[i])
                )
            }
        }
    )
}

@Composable
fun MazoPersonalizado(
    modifier: Modifier = Modifier,
    listaURL: List<String> = emptyList(),
    cardColors: CardColors = playerCardColor()
) {
    val imagemMod = Modifier.size(100.dp)
    Column(
        modifier = modifier
    ) {
        Card(modifier = modifier) {
            Row {
                ElevatedCard(
                    modifier = modifier
                        .border(width = 1.dp, color = Color.Black, shape = RectangleShape),
                    colors = cardColors,
                    shape = RectangleShape
                ) {
                    HeroImage(modifier = imagemMod, url = listaURL[0])
                }
                ElevatedCard(
                    modifier = modifier
                        .border(width = 1.dp, color = Color.Black, shape = RectangleShape),
                    colors = cardColors,
                    shape = RectangleShape
                ) {
                    HeroImage(modifier = imagemMod, url = listaURL[1])
                }
                ElevatedCard(
                    modifier = modifier
                        .border(width = 1.dp, color = Color.Black, shape = RectangleShape),
                    colors = cardColors,
                    shape = RectangleShape
                ) {
                    HeroImage(modifier = imagemMod, url = listaURL[2])
                }
            }
            Row {
                ElevatedCard(
                    modifier = modifier
                        .border(width = 1.dp, color = Color.Black, shape = RectangleShape),
                    colors = cardColors,
                    shape = RectangleShape
                ) {
                    HeroImage(modifier = imagemMod, url = listaURL[3])
                }
                ElevatedCard(
                    modifier = modifier
                        .border(width = 1.dp, color = Color.Black, shape = RectangleShape),
                    colors = cardColors,
                    shape = RectangleShape
                ) {
                    HeroImage(modifier = imagemMod, url = listaURL[4])
                }
                ElevatedCard(
                    modifier = modifier
                        .border(width = 1.dp, color = Color.Black, shape = RectangleShape),
                    colors = cardColors,
                    shape = RectangleShape
                ) {
                    HeroImage(modifier = imagemMod, url = listaURL[5])
                }
            }
        }
    }
}
private fun obtenerLista(deck: DeckModel): List<String> {
    val lista = mutableListOf<String>()
    lista.add(deck.carta1.image.url)
    lista.add(deck.carta2.image.url)
    lista.add(deck.carta3.image.url)
    lista.add(deck.carta4.image.url)
    lista.add(deck.carta5.image.url)
    lista.add(deck.carta6.image.url)
    return lista
}

