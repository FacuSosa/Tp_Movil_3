package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffold.ui.theme.shaka_pow

@Composable
fun HeroText(
    modifier: Modifier = Modifier,
    text: String = "",
    textAlign: TextAlign = TextAlign.Center,
    color: Color = Color.White,
    fontSize: TextUnit = 20.sp
) {
    Text(
        text = text,
        fontSize = fontSize,
        color = color,
        fontFamily = shaka_pow,
        textAlign = textAlign,
        modifier = modifier
    )
}