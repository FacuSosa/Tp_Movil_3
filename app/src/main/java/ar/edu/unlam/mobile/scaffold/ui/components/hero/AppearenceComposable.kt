package ar.edu.unlam.mobile.scaffold.ui.components.hero

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.domain.model.AppearanceModel
import ar.edu.unlam.mobile.scaffold.ui.components.CustomCard
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTextBodyLarge

@Preview(showBackground = true)
@Composable
fun HeroAppearance(
    modifier: Modifier = Modifier,
    heroAppearance: () -> AppearanceModel = { AppearanceModel() }
) {
    CustomCard(
        modifier = modifier
    ) {
        CustomTextBodyLarge(
            modifier = Modifier.padding(8.dp).testTag("appearance text"),
            text = {
                "Genero: ${heroAppearance().gender}\n" +
                    "Raza: ${heroAppearance().race}\n" +
                    "Altura: ${heroAppearance().height}\n" +
                    "Peso: ${heroAppearance().weight}\n" +
                    "Color de ojos: ${heroAppearance().eyeColor}\n" +
                    "Color de cabello: ${heroAppearance().hairColor}"
            }
        )
    }
}
