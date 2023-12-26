package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.domain.model.StatModel
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroCard
import ar.edu.unlam.mobile.scaffold.ui.components.hero.playerCardColor

@Composable
fun AnimatedHeroCard(
    modifier: Modifier = Modifier,
    hero: HeroModel = HeroModel(),
    cardColors: CardColors = playerCardColor()
) {
    val powerLevel = calculatePowerLevel(hero.stats)
    val infiniteTransition = rememberInfiniteTransition(label = "border animation")
    val borderColor by infiniteTransition.animateColor(
        initialValue = Color.Cyan,
        targetValue = Color.Magenta,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "animated border"
    )

    when {
        powerLevel < 200 -> {
            HeroCard(
                modifier = modifier,
                hero = hero,
                cardColors = cardColors
            )
        }
        powerLevel in 200..399 -> {
            HeroCard(
                modifier = modifier.border(width = 1.dp, color = borderColor),
                hero = hero,
                cardColors = cardColors
            )
        }
        else -> {
            MenancingAnimation {
                HeroCard(
                    modifier = modifier.border(width = 1.dp, color = borderColor),
                    hero = hero,
                    cardColors = cardColors
                )
            }
        }
    }
}

private fun calculatePowerLevel(stat: StatModel): Int {
    return stat.power + stat.combat + stat.speed + stat.intelligence + stat.strength + stat.durability
}
