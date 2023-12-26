package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun GameScore(
    modifier: Modifier = Modifier,
    playerScore: Int = 0,
    adversaryScore: Int = 0
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IndividualScore(
            modifier = Modifier
                .border(1.dp, color = Color.Black),
            score = playerScore,
            text = "Jugador"
        )
        IndividualScore(
            modifier = Modifier
                .border(1.dp, color = Color.Black),
            score = adversaryScore,
            text = "Adversario",
            backgroundColor = MaterialTheme.colorScheme.secondary,
            textColor = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IndividualScore(
    modifier: Modifier = Modifier,
    score: Int = 0,
    text: String = "Jugador o adversario:",
    backgroundColor: Color = MaterialTheme.colorScheme.tertiary,
    textColor: Color = MaterialTheme.colorScheme.onTertiary
) {
    Text(
        modifier = modifier
            .background(color = backgroundColor)
            .padding(5.dp),
        text = "$text: $score",
        color = textColor
    )
}
