package ar.edu.unlam.mobile.scaffold.ui.screens.heroduel

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.domain.cardgame.Stat
import ar.edu.unlam.mobile.scaffold.domain.cardgame.Winner
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.ui.components.ActionMenu
import ar.edu.unlam.mobile.scaffold.ui.components.AnimatedHeroCard
import ar.edu.unlam.mobile.scaffold.ui.components.CustomButton
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTitle
import ar.edu.unlam.mobile.scaffold.ui.components.GameScore
import ar.edu.unlam.mobile.scaffold.ui.components.PlayerDeck
import ar.edu.unlam.mobile.scaffold.ui.components.hero.adversaryCardColor

@Preview(showBackground = true)
@Composable
fun FinishedDuelUi(
    modifier: Modifier = Modifier,
    winner: Winner = Winner.PLAYER,
    playerScore: Int = 0,
    adversaryScore: Int = 0,
    onPlayerWinner: () -> Unit = { }
) {
    Box(
        modifier = modifier.clickable {
            if (winner == Winner.PLAYER) {
                onPlayerWinner()
            }
        }
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo_pantalla_pelea),
            contentDescription = "FinishedDuelUi background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTitle(
                modifier = Modifier
                    .padding(16.dp)
                    .testTag("FinishedDuelUi result text"),
                text = { "El ganador es " + if (winner == Winner.PLAYER) "el jugador!" else "el adversario." }
            )
            GameScore(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .testTag("FinishedDuelUi final score"),
                playerScore = playerScore,
                adversaryScore = adversaryScore
            )
        }
    }
}

@Composable
fun HeroDuelScreen(
    modifier: Modifier = Modifier,
    viewModel: HeroDuelViewModelv2 = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    if (isLoading) {
        Box(modifier = modifier) {
            Image(
                painter = painterResource(id = R.drawable.fondo_pantalla_pelea),
                contentDescription = "Fondo loading",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }
    } else {
        val playerDeck by viewModel.currentPlayerDeck.collectAsStateWithLifecycle()
        val onPlayCardClick = viewModel::playCard
        val onPlayerCardClick = viewModel::selectPlayerCard
        val currentPlayerCard by viewModel.currentPlayerCard.collectAsStateWithLifecycle()
        val currentAdversaryCard by viewModel.currentAdversaryCard.collectAsStateWithLifecycle()
        val playerScore by viewModel.playerScore.collectAsStateWithLifecycle()
        val adversaryScore by viewModel.adversaryScore.collectAsStateWithLifecycle()
        val onClickSelectedStat = viewModel::selectStat
        val useMultiplierX2 = viewModel::useMultiplierX2
        val onFightClick = viewModel::fight
        val winner by viewModel.winner.collectAsStateWithLifecycle()
        val canMultix2BeUsed by viewModel.isMultiplierAvailable.collectAsStateWithLifecycle()
        val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
        val wonHero by viewModel.wonHero.collectAsStateWithLifecycle()
        val checkIfPlayerWonHero = viewModel::checkIfPlayerWonHero

        val animationDuration = 500

        AnimatedContent(
            targetState = currentScreen,
            label = "",
            transitionSpec = {
                if (targetState == DuelScreen.SELECT_CARD_UI) {
                    slideInHorizontally(
                        animationSpec = tween(
                            durationMillis = animationDuration
                        ),
                        initialOffsetX = { fullWidth -> -fullWidth }
                    ) togetherWith (
                        slideOutHorizontally(
                            animationSpec = tween(
                                durationMillis = animationDuration
                            ),
                            targetOffsetX = { fullWidth -> fullWidth }
                        )
                        )
                } else {
                    slideInHorizontally(
                        animationSpec = tween(
                            durationMillis = animationDuration
                        ),
                        initialOffsetX = { fullWidth -> fullWidth }
                    ) togetherWith (
                        slideOutHorizontally(
                            animationSpec = tween(
                                durationMillis = animationDuration
                            ),
                            targetOffsetX = { fullWidth -> -fullWidth }
                        )
                        )
                }
            }
        ) { screen ->
            when (screen) {
                DuelScreen.DUEL_UI -> DuelUi(
                    modifier = modifier,
                    currentPlayerCard = currentPlayerCard,
                    currentAdversaryCard = currentAdversaryCard,
                    playerScore = playerScore,
                    adversaryScore = adversaryScore,
                    onClickSelectedStat = onClickSelectedStat,
                    useMultiplier = useMultiplierX2,
                    onFightClick = onFightClick,
                    canMultix2BeUsed = canMultix2BeUsed
                )
                DuelScreen.SELECT_CARD_UI -> SelectCardUi(
                    modifier = modifier,
                    playerDeck = playerDeck,
                    onPlayCardClick = onPlayCardClick,
                    onPlayerCardClick = onPlayerCardClick
                )
                DuelScreen.FINISHED_DUEL_UI -> FinishedDuelUi(
                    modifier = modifier,
                    winner = winner,
                    playerScore = playerScore,
                    adversaryScore = adversaryScore,
                    onPlayerWinner = checkIfPlayerWonHero
                )
                DuelScreen.WIN_CARD_UI -> WinCardUi(modifier = modifier, hero = wonHero)
            }
        }
    }
}

@Preview
@Composable
fun WinCardUi(
    modifier: Modifier = Modifier,
    hero: HeroModel = HeroModel()
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.fondo_coleccion),
            contentDescription = "WinCardUi background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = 25.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTitle(
                text = { "Ganaste la siguiente carta!" }
            )
            AnimatedHeroCard(
                hero = hero
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DuelUi(
    modifier: Modifier = Modifier,
    currentPlayerCard: HeroModel = HeroModel(),
    currentAdversaryCard: HeroModel = HeroModel(),
    playerScore: Int = 0,
    adversaryScore: Int = 0,
    onClickSelectedStat: (Stat) -> Unit = {},
    canMultix2BeUsed: Boolean = true,
    useMultiplier: (Boolean) -> Unit = {},
    onFightClick: () -> Unit = {}
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.fondo_pantalla_pelea),
            contentDescription = "DuelUi background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GameScore(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .testTag("DuelUi game score"),
                playerScore = playerScore,
                adversaryScore = adversaryScore
            )
            val cardModifier = Modifier
                .padding(7.dp)
            AnimatedHeroCard(
                modifier = cardModifier.testTag("DuelUi adversary card"),
                hero = currentAdversaryCard,
                cardColors = adversaryCardColor()
            )
            ActionMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                    .testTag("DuelUi action menu"),
                onClickSelectedStat = onClickSelectedStat,
                useMultiplier = useMultiplier,
                onFightClick = onFightClick,
                isMultiplierEnabled = canMultix2BeUsed,
                heroStats = currentPlayerCard.stats
            )
            AnimatedHeroCard(
                modifier = cardModifier.testTag("DuelUi player card"),
                hero = currentPlayerCard
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectCardUi(
    modifier: Modifier = Modifier,
    playerDeck: List<HeroModel> = listOf(
        HeroModel(id = 1, name = "test 1"),
        HeroModel(id = 2, name = "test 2")
    ),
    onPlayCardClick: () -> Unit = {},
    onPlayerCardClick: (Int) -> Unit = {},
) {
    var selectedCard by remember { mutableStateOf(playerDeck[0]) }

    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.fondo_pantalla_pelea),
            contentDescription = "SelectCardUi background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = 5.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedHeroCard(
                modifier = Modifier
                    .padding(8.dp)
                    .testTag("SelectCardUi player card"),
                hero = selectedCard
            )
            CustomButton(
                modifier = Modifier.testTag("SelectCardUi play card button"),
                label = { "Jugar carta!" },
                onClick = onPlayCardClick
            )
            PlayerDeck(
                modifier = Modifier
                    .weight(1f)
                    .testTag("SelectCardUi player deck"),
                playerDeck = playerDeck,
                onCardClick = { index ->
                    selectedCard = playerDeck[index]
                    onPlayerCardClick(index)
                }
            )
        }
    }
}
