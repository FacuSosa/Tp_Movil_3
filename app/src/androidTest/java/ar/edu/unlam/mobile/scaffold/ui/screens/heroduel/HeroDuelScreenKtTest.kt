package ar.edu.unlam.mobile.scaffold.ui.screens.heroduel

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import ar.edu.unlam.mobile.scaffold.domain.cardgame.Winner
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.domain.model.ImageModel
import ar.edu.unlam.mobile.scaffold.domain.model.StatModel
import org.junit.Rule
import org.junit.Test

class HeroDuelScreenKtTest {

    // compose rule is required to get access to the composable component
    @get: Rule
    val compose = createComposeRule()

    @Test
    fun whenHavingDefaultStatesToFinishedDuelUi_VerifyAllComposableExists() {
        compose.setContent {
            FinishedDuelUi()
        }
        compose.onNodeWithContentDescription(label = "FinishedDuelUi background").assertExists()
        compose.onNodeWithTag(testTag = "FinishedDuelUi result text").assertExists()
        compose.onNodeWithTag(testTag = "FinishedDuelUi final score").assertExists()
    }

    @Test
    fun whenTheWinnerIsTheAdversary_VerifyAllComposableInFinishedDuelUiExists() {
        compose.setContent {
            FinishedDuelUi(
                winner = Winner.ADVERSARY
            )
        }
        compose.onNodeWithContentDescription(label = "FinishedDuelUi background").assertExists()
        compose.onNodeWithTag(testTag = "FinishedDuelUi result text").assertExists()
        compose.onNodeWithTag(testTag = "FinishedDuelUi final score").assertExists()
    }

    @Test
    fun whenHavingDefaultStatesToDuelUi_VerifyAllComposableExists() {
        compose.setContent {
            DuelUi()
        }
        compose.onNodeWithTag(testTag = "DuelUi game score").assertExists()
        compose.onNodeWithTag(testTag = "DuelUi adversary card").assertExists()
        compose.onNodeWithTag(testTag = "DuelUi action menu").assertExists()
        compose.onNodeWithContentDescription(label = "DuelUi background").assertExists()
    }

    private val heroList = listOf(
        HeroModel(
            id = 1,
            name = "Mr. Test 1",
            image = ImageModel("https://loremflickr.com/400/400/cat?lock=1")
        ),
        HeroModel(
            id = 2,
            name = "Mr. Test 2",
            image = ImageModel("https://loremflickr.com/400/400/dog?lock=1"),
            stats = StatModel(
                combat = 1,
                durability = 10,
                intelligence = 100,
                power = 1,
                speed = 10,
                strength = 100
            )
        ),
        HeroModel(
            id = 3,
            name = "Mr. Test 3",
            image = ImageModel("https://loremflickr.com/400/400/mouse?lock=1")
        )
    )

    // utilizar este de ejemplo
    @Test
    fun whenClickingSecondHeroFromTheDeck_VerifyPlayerCardFromSelectCardUiIsTheClickedHero() {
        val expectedHeroText = heroList[1].name
        compose.setContent {
            SelectCardUi(
                playerDeck = heroList
            )
        }
        compose.onAllNodes(matcher = hasClickAction()).printToLog(tag = "SelectCardUi", maxDepth = 30)
        compose.onNode(
            matcher = hasText(
                text = "Inteligencia: 100",
                substring = true
            ) and hasClickAction(),
            useUnmergedTree = false
        ).performClick()
        compose
            .onNodeWithTag(
                testTag = "SelectCardUi player card",
                useUnmergedTree = true
            ).onChildren()
            .assertAny(hasText(expectedHeroText))
    }

    @Test
    fun whenHavingDefaultStatesToSelectCardUi_VerifyAllComposablesExists() {
        compose.setContent {
            SelectCardUi()
        }
        compose.onNode(
            matcher = hasContentDescription(value = "SelectCardUi background")
        ).assertExists()
        compose.onNodeWithTag(testTag = "SelectCardUi player card").assertExists()
        compose.onNodeWithTag(testTag = "SelectCardUi play card button").assertExists()
        compose.onNodeWithTag(testTag = "SelectCardUi player deck").assertExists()
    }
}
