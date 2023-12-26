package ar.edu.unlam.mobile.scaffold.ui.components.hero

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import org.junit.Rule
import org.junit.Test

class ConnectionsComposablesKtTest {

    @get: Rule
    val composeRule = createComposeRule()

    @Test
    fun whenProvidingDefaultStateToHeroConnections_VerifyAllViewsExist() {
        composeRule.setContent {
            ComicWarTheme {
                HeroConnections()
            }
        }
        composeRule.onNodeWithTag("connections text").assertExists()
    }
}
