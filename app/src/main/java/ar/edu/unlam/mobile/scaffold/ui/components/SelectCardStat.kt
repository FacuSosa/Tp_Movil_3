package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.unlam.mobile.scaffold.domain.cardgame.Stat
import ar.edu.unlam.mobile.scaffold.domain.model.StatModel

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCardStat(
    modifier: Modifier = Modifier,
    heroStats: StatModel = StatModel(),
    onClick: (Stat) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val statList by remember { mutableStateOf(getStatList(heroStats)) }
    var selectedStat by remember { mutableStateOf(statList[0]) }

    Box(
        modifier = modifier
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = "${selectedStat.first.statName}: ${selectedStat.second}",
                onValueChange = { onClick(selectedStat.first) },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                statList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = "${item.first.statName}: ${item.second}") },
                        onClick = {
                            selectedStat = item
                            onClick(selectedStat.first)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

private fun getStatList(stat: StatModel): List<Pair<Stat, Int>> {
    val statPairList = mutableListOf<Pair<Stat, Int>>()
    statPairList.add(Pair(Stat.POWER, stat.power))
    statPairList.add(Pair(Stat.DURABILITY, stat.durability))
    statPairList.add(Pair(Stat.STRENGTH, stat.strength))
    statPairList.add(Pair(Stat.SPEED, stat.speed))
    statPairList.add(Pair(Stat.COMBAT, stat.combat))
    statPairList.add(Pair(Stat.INTELLIGENCE, stat.intelligence))
    statPairList.sortByDescending { it.second }
    return statPairList
}
