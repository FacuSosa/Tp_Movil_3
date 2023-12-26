package ar.edu.unlam.mobile.scaffold.ui.screens.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.ui.components.CustomButton
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTextBodyLarge
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTextButton
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTextLabelMedium
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTitle
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxBackgroundImage
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxHeroImage

@Preview(showBackground = true)
@Composable
fun QuizResultPopup(
    modifier: Modifier = Modifier,
    isCorrectAnswer: () -> Boolean = { false },
    show: () -> Boolean = { false },
    correctHeroName: () -> String = { "Correct Hero" },
    chosenHero: () -> String = { "Chosen Hero" },
    onClickPlayAgain: () -> Unit = {},
    onClickMainMenu: () -> Unit = {}
) {
    val textToShow = if (isCorrectAnswer()) {
        "¡Felicidades! ${correctHeroName()} es la respuesta correcta."
    } else {
        "Lamentablemente, ${chosenHero()} es incorrecto. Respuesta correcta: ${correctHeroName()}."
    }
    if (show()) {
        AlertDialog(
            modifier = modifier.testTag("alert dialog"),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            onDismissRequest = {},
            title = { CustomTextLabelMedium(text = { "Resultado" }) },
            text = {
                CustomTextBodyLarge(
                    modifier = Modifier.testTag("body"),
                    text = { textToShow }
                )
            },
            confirmButton = {
                CustomTextButton(
                    modifier = Modifier.testTag("play again button"),
                    onClick = onClickPlayAgain,
                    label = { "JUGAR DE NUEVO" }
                )
            },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
            dismissButton = {
                CustomTextButton(
                    modifier = Modifier.testTag("return to main menu button"),
                    onClick = onClickMainMenu,
                    label = { "MENU PRINCIPAL" }
                )
            }
        )
    }
}

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    returnHomeScreen: () -> Unit = {},
    viewModel: QuizViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val imageUrl by viewModel.heroPortraitUrl.collectAsStateWithLifecycle()
    val option1Text by viewModel.option1.collectAsStateWithLifecycle()
    val option2Text by viewModel.option2.collectAsStateWithLifecycle()
    val option3Text by viewModel.option3.collectAsStateWithLifecycle()
    val option4Text by viewModel.option4.collectAsStateWithLifecycle()
    val onClickOption1 = viewModel::selectOption1
    val onClickOption2 = viewModel::selectOption2
    val onClickOption3 = viewModel::selectOption3
    val onClickOption4 = viewModel::selectOption4
    val isCorrectAnswer by viewModel.isCorrectAnswer.collectAsStateWithLifecycle()
    val showPopup by viewModel.showResult.collectAsStateWithLifecycle()
    val correctAnswer by viewModel.correctAnswer.collectAsStateWithLifecycle()
    val chosenHero by viewModel.chosenHero.collectAsStateWithLifecycle()

    val onNewGame = viewModel::newGame

    val sensorData by viewModel.sensorData
        .collectAsStateWithLifecycle(initialValue = SensorData(0f, 0f))

    DisposableEffect(Unit) {
        onDispose {
            viewModel.cancelSensorDataFlow()
        }
    }

    QuizUi(
        modifier = modifier.testTag("ui"),
        isLoading = isLoading,
        imageUrl = imageUrl,
        option1Text = { option1Text },
        option2Text = { option2Text },
        option3Text = { option3Text },
        option4Text = { option4Text },
        onClickOption1 = onClickOption1,
        onClickOption2 = onClickOption2,
        onClickOption3 = onClickOption3,
        onClickOption4 = onClickOption4,
        isCorrectAnswer = { isCorrectAnswer },
        showPopup = { showPopup },
        correctHeroName = { correctAnswer },
        chosenHero = { chosenHero },
        onClickPlayAgain = onNewGame,
        onClickMainMenu = returnHomeScreen,
        sensorData = { sensorData }
    )
}

@Preview(showBackground = true)
@Composable
fun QuizUi(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    imageUrl: String = "https://loremflickr.com/400/400/cat?lock=1",
    option1Text: () -> String = { "Option 1" },
    option2Text: () -> String = { "Option 2" },
    option3Text: () -> String = { "Option 3" },
    option4Text: () -> String = { "Option 4" },
    onClickOption1: () -> Unit = {},
    onClickOption2: () -> Unit = {},
    onClickOption3: () -> Unit = {},
    onClickOption4: () -> Unit = {},
    isCorrectAnswer: () -> Boolean = { false },
    showPopup: () -> Boolean = { false },
    correctHeroName: () -> String = { "Correct Hero" },
    chosenHero: () -> String = { "Chosen Hero" },
    onClickPlayAgain: () -> Unit = {},
    onClickMainMenu: () -> Unit = {},
    sensorData: () -> SensorData = { SensorData(0f, 0f) }
) {
    Box(modifier = modifier) {
        ParallaxBackgroundImage(
            modifier = Modifier
                .fillMaxSize()
                .testTag("background image"),
            contentDescription = "Pantalla Coleccion",
            painterResourceId = R.drawable.fondo_coleccion,
            data = sensorData
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = modifier
                    .align(Alignment.Center)
                    .testTag("loading composable")
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                CustomTitle(
                    modifier = Modifier
                        .padding(18.dp)
                        .testTag("title"),
                    text = { "¿Quien es este heroe?" }
                )
                ParallaxHeroImage(
                    modifier = Modifier
                        .testTag("hero image")
                        .fillMaxWidth()
                        .padding(all = 25.dp),
                    imageUrl = imageUrl,
                    data = sensorData
                )
                AnswerPanel(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .testTag("answer panel"),
                    option1Text = option1Text,
                    option2Text = option2Text,
                    option3Text = option3Text,
                    option4Text = option4Text,
                    onOption1Click = onClickOption1,
                    onOption2Click = onClickOption2,
                    onOption3Click = onClickOption3,
                    onOption4Click = onClickOption4
                )
            }
        }
        QuizResultPopup(
            modifier = Modifier.testTag("Result popup"),
            isCorrectAnswer = isCorrectAnswer,
            show = showPopup,
            correctHeroName = correctHeroName,
            chosenHero = chosenHero,
            onClickPlayAgain = onClickPlayAgain,
            onClickMainMenu = onClickMainMenu
        )
    }
}

@Preview(showBackground = true, widthDp = 600)
@Composable
fun AnswerPanel(
    modifier: Modifier = Modifier,
    onOption1Click: () -> Unit = {},
    onOption2Click: () -> Unit = {},
    onOption3Click: () -> Unit = {},
    onOption4Click: () -> Unit = {},
    option1Text: () -> String = { "option 1" },
    option2Text: () -> String = { "option 2" },
    option3Text: () -> String = { "option 3" },
    option4Text: () -> String = { "option 4" },
) {
    val buttonModifier = Modifier
        .width(180.dp)
        .height(80.dp)
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
        ) {
            CustomButton(
                modifier = buttonModifier.testTag("option 1 button"),
                onClick = onOption1Click,
                label = option1Text
            )
            Spacer(modifier = Modifier.padding(4.dp))
            CustomButton(
                modifier = buttonModifier.testTag("option 3 button"),
                onClick = onOption3Click,
                label = option3Text
            )
        }
        Column {
            CustomButton(
                modifier = buttonModifier.testTag("option 2 button"),
                onClick = onOption2Click,
                label = option2Text
            )
            Spacer(modifier = Modifier.padding(4.dp))
            CustomButton(
                modifier = buttonModifier.testTag("option 4 button"),
                onClick = onOption4Click,
                label = option4Text
            )
        }
    }
}
