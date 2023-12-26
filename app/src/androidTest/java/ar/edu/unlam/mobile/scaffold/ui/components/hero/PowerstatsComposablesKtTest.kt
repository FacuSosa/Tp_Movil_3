package ar.edu.unlam.mobile.scaffold.ui.components.hero

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import org.junit.Rule
import org.junit.Test

class PowerstatsComposablesKtTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenProvidingDefaultStateToHeroStats_VerifyAllViewsExist() {
        composeTestRule.setContent {
            ComicWarTheme {
                HeroStats()
            }
        }
        composeTestRule.onNodeWithTag("Int Speed Durability text").assertExists()
        composeTestRule.onNodeWithTag("Str Power Combat text").assertExists()
    }
}
