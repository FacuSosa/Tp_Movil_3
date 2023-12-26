package ar.edu.unlam.mobile.scaffold.ui.components.hero

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.domain.model.StatModel
import ar.edu.unlam.mobile.scaffold.ui.components.CustomCard
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTextBodyLarge

@Preview(showBackground = true)
@Composable
fun HeroStats(
    modifier: Modifier = Modifier,
    stats: () -> StatModel = { StatModel() },
) {
    CustomCard(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomTextBodyLarge(
                modifier = Modifier.padding(8.dp).testTag("Int Speed Durability text"),
                text = {
                    "Inteligencia: ${stats().intelligence}\n" +
                        "Velocidad: ${stats().speed}\n" +
                        "Durabilidad: ${stats().durability}"
                }
            )
            CustomTextBodyLarge(
                modifier = Modifier.padding(8.dp).testTag("Str Power Combat text"),
                text = {
                    "Fuerza: ${stats().strength}\n" +
                        "Poder: ${stats().power}\n" +
                        "Combate: ${stats().combat}"
                }
            )
        }
    }
}
