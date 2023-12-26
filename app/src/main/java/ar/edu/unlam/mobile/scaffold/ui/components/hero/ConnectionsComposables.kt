package ar.edu.unlam.mobile.scaffold.ui.components.hero

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.domain.model.ConnectionsModel
import ar.edu.unlam.mobile.scaffold.ui.components.CustomCard
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTextBodyLarge

@Preview(showBackground = true)
@Composable
fun HeroConnections(
    modifier: Modifier = Modifier,
    heroConnections: () -> ConnectionsModel = { ConnectionsModel() }
) {
    CustomCard(modifier = modifier) {
        CustomTextBodyLarge(
            modifier = Modifier.padding(8.dp).testTag("connections text"),
            text = {
                "Afiliacion grupal: ${heroConnections().groupAffiliation}\n" +
                    "Personas mas cercanas: ${heroConnections().relatives}"
            }
        )
    }
}
