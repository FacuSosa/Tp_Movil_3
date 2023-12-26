package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.domain.cardgame.Stat
import ar.edu.unlam.mobile.scaffold.domain.model.StatModel

@Preview(showBackground = true)
@Composable
fun ActionMenu(
    modifier: Modifier = Modifier,
    onClickSelectedStat: (Stat) -> Unit = {},
    isMultiplierEnabled: Boolean = true,
    heroStats: StatModel = StatModel(),
    useMultiplier: (Boolean) -> Unit = {},
    onFightClick: () -> Unit = {}
) {
    var checked by rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            SelectCardStat(
                modifier = Modifier.width(200.dp),
                onClick = onClickSelectedStat,
                heroStats = heroStats
            )
            CustomLabeledCheckbox(
                enabled = { isMultiplierEnabled },
                label = { "Multi x2:" },
                checked = {
                    if (isMultiplierEnabled) {
                        checked
                    } else {
                        false
                    }
                },
                onCheckedChange = {
                    checked = it
                    useMultiplier(checked)
                }
            )
        }
        CustomButton(
            label = { "Pelear!" },
            onClick = onFightClick
        )
    }
}
