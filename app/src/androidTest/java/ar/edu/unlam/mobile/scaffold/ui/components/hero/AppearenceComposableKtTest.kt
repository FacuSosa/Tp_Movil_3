package ar.edu.unlam.mobile.scaffold.ui.components.hero

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import org.junit.Rule
import org.junit.Test

class AppearenceComposableKtTest {

    @get: Rule
    val composableTestRule = createComposeRule()

    @Test
    fun whenProvidingDefaultStateToHeroAppearance_VerifyAllViewsExist() {
        composableTestRule.setContent {
            ComicWarTheme {
                HeroAppearance()
            }
        }
        composableTestRule.onNodeWithTag("appearance text").assertExists()
    }
}
