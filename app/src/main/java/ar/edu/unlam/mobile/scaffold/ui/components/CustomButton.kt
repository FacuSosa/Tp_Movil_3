package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    label: () -> String = { "Button" }
) {
    ElevatedButton(
        modifier = modifier.testTag(tag = "elevated button"),
        onClick = onClick,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
    ) {
        CustomTextLabelMedium(
            modifier = Modifier.testTag(tag = "label"),
            text = label,
        )
    }
}

@Composable
fun CustomTextButton(
    modifier: Modifier = Modifier,
    label: () -> String = { "Text button" },
    onClick: () -> Unit = { }
) {
    TextButton(
        modifier = modifier.testTag("text button"),
        onClick = onClick
    ) {
        CustomTextLabelMedium(
            modifier = modifier.testTag("label"),
            text = label
        )
    }
}
