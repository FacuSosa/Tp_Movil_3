package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun horizontalCardColors(): CardColors {
    return CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomHorizontalClickeableCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable (RowScope.() -> Unit) = {}
) {
    ElevatedCard(
        modifier = modifier,
        onClick = onClick,
        shape = RectangleShape,
        colors = horizontalCardColors()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomClickeableCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable (ColumnScope.() -> Unit) = {}
) {
    ElevatedCard(
        // enabled = hero.quantity > 0
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = cardColors()
    ) {
        content()
    }
}

// son libres de cambiar los colores
@Composable
private fun cardColors(): CardColors {
    return CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
    )
}

@Preview(showBackground = true)
@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit) = {}
) {
    ElevatedCard(
        // enabled = hero.quantity > 0
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        colors = cardColors()
    ) {
        content()
    }
}
