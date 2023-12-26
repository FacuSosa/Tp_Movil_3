package ar.edu.unlam.mobile.scaffold.ui.components.hero

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.domain.model.WorkModel
import ar.edu.unlam.mobile.scaffold.ui.components.CustomCard
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTextBodyLarge

@Preview(showBackground = true)
@Composable
fun HeroWork(
    modifier: Modifier = Modifier,
    heroWork: () -> WorkModel = { WorkModel() }
) {
    CustomCard(
        modifier = modifier
    ) {
        CustomTextBodyLarge(
            modifier = Modifier.padding(8.dp).testTag("work text"),
            text = {
                "Profesion: ${heroWork().occupation}\n" +
                    "Base: ${heroWork().base}"
            }
        )
    }
}
