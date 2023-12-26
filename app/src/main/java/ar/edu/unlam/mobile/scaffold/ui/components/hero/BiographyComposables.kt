package ar.edu.unlam.mobile.scaffold.ui.components.hero

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.domain.model.BiographyModel
import ar.edu.unlam.mobile.scaffold.ui.components.CustomCard
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTextBodyLarge

@Preview(showBackground = true)
@Composable
fun HeroBiography(
    modifier: Modifier = Modifier,
    biography: () -> BiographyModel = { BiographyModel() }
) {
    CustomCard(
        modifier = modifier
    ) {
        CustomTextBodyLarge(
            modifier = Modifier.padding(8.dp).testTag("body text"),
            text = {
                "Nombre: ${biography().fullName}\n" +
                    "Alter-Egos: ${biography().alterEgos}\n" +
                    "Apodos: ${biography().aliases}\n" +
                    "Lugar de nacimiento: ${biography().placeOfBirth}\n" +
                    "Primera aparicion: ${biography().firstAppearance}\n" +
                    "Editorial: ${biography().publisher}\n" +
                    "Alineacion: ${biography().alignment}"
            }
        )
    }
}
