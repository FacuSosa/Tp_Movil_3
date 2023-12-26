package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun CustomTitle(
    modifier: Modifier = Modifier,
    text: () -> String = { "Title" }
) {
    Text(
        modifier = modifier.testTag("text"),
        text = text(),
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun CustomTextLabelMedium(
    modifier: Modifier = Modifier,
    text: () -> String = { "CustomText" }
) {
    Text(
        modifier = modifier.testTag("text"),
        text = text(),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}

@Composable
fun CustomTextLabelSmall(
    modifier: Modifier = Modifier,
    text: () -> String = { "CustomText" }
) {
    Text(
        modifier = modifier.testTag("text"),
        text = text(),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}

@Composable
fun CustomTextBodyLarge(
    modifier: Modifier = Modifier,
    text: () -> String = { "Text body large" }
) {
    Text(
        modifier = modifier.testTag("text"),
        text = text(),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}
