package ar.edu.unlam.mobile.scaffold.ui.components.hero

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.ui.components.CustomClickeableCard
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTextLabelMedium
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTextLabelSmall

@Composable
fun adversaryCardColor(): CardColors {
    return CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary
    )
}

@Composable
fun playerCardColor(): CardColors {
    return CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.tertiary,
        contentColor = MaterialTheme.colorScheme.onTertiary
    )
}

@Preview(showBackground = true)
@Composable
fun HeroCard(
    modifier: Modifier = Modifier,
    hero: HeroModel = HeroModel(),
    cardColors: CardColors = playerCardColor(),
    content: @Composable (ColumnScope.() -> Unit) = {}
) {
    ElevatedCard(
        modifier = modifier
            .border(width = 1.dp, color = Color.Black, shape = RectangleShape),
        colors = cardColors,
        shape = RectangleShape
    ) {
        // row { infiniteTransition (jojo) HeroImage infiniteTransition (jojo) }
        // AnimatedContent para intercambiar UIs, CrossFade es una manera más simple que AnimatedContent
        HeroImage(
            modifier = Modifier
                .padding(8.dp)
                .size(190.dp)
                .border(width = 1.dp, color = Color.Black)
                .align(Alignment.CenterHorizontally),
            url = hero.image.url,
            contentScale = ContentScale.Crop
        )
        CustomTextLabelMedium(
            modifier = Modifier
                .padding(1.dp)
                .align(Alignment.CenterHorizontally),
            text = { hero.name }
        )
        content()
    }
}

@Composable
fun HeroGallery(
    modifier: Modifier = Modifier,
    heroList: List<HeroModel>,
    columnCount: Int = 3,
    onItemClick: (Int) -> Unit
) {
    val listSize = remember(calculation = { heroList.size })
    LazyVerticalGrid(
        modifier = modifier.testTag("lazy grid"),
        columns = GridCells.Fixed(columnCount),
        contentPadding = PaddingValues(all = 8.dp),
        content = {
            items(listSize) { i ->
                GalleryItem(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .testTag("gallery item $i"),
                    hero = heroList[i],
                    onItemClick = { onItemClick(i + 1) }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GalleryItem(
    modifier: Modifier = Modifier,
    hero: HeroModel = HeroModel(),
    onItemClick: () -> Unit = {},
) {
    CustomClickeableCard(
        modifier = modifier,
        onClick = onItemClick
    ) {
        HeroImage(
            modifier = Modifier
                .padding(4.dp)
                .aspectRatio(ratio = 0.8f)
                .testTag("profile image"),
            url = hero.image.url,
            contentScale = ContentScale.Crop
        )
        CustomTextLabelSmall(
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
                .align(alignment = Alignment.CenterHorizontally)
                .testTag("id name text"),
            text = { "${hero.id} ${hero.name}" }
        )
    }
}


