package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun CustomLabeledCheckbox(
    modifier: Modifier = Modifier,
    label: () -> String = { "Checkbox" },
    enabled: () -> Boolean = { true },
    checked: () -> Boolean = { false },
    onCheckedChange: (Boolean) -> Unit = { }
) {
    /*
    var checked by rememberSaveable {
        mutableStateOf(false)
    }
     */
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CustomTextLabelSmall(
            text = label
        )
        Checkbox(
            modifier = modifier,
            checked = checked(),
            onCheckedChange = {
                onCheckedChange(it)
            },
            enabled = enabled(),
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.secondary,
                uncheckedColor = MaterialTheme.colorScheme.primary,
                checkmarkColor = MaterialTheme.colorScheme.onSecondary,
                disabledUncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}
