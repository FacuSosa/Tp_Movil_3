package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import org.junit.Rule
import org.junit.Test

class CustomTextKtTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenProvidingDefaultStateToCustomTitle_VerifyAllViewsExist() {
        composeTestRule.setContent {
            ComicWarTheme {
                CustomTitle()
            }
        }
        composeTestRule.onNodeWithTag("text").assertExists()
    }

    @Test
    fun whenProvidingDefaultStateToCustomTextLabelMedium_VerifyAllViewsExist() {
        composeTestRule.setContent {
            ComicWarTheme {
                CustomTextLabelMedium()
            }
        }
        composeTestRule.onNodeWithTag("text").assertExists()
    }

    @Test
    fun whenProvidingDefaultStateToCustomTextLabelSmall_VerifyAllViewsExist() {
        composeTestRule.setContent {
            ComicWarTheme {
                CustomTextLabelSmall()
            }
        }
        composeTestRule.onNodeWithTag("text").assertExists()
    }

    @Test
    fun whenProvidingDefaultStateToCustomTextBodyLarge_VerifyAllViewsExist() {
        composeTestRule.setContent {
            ComicWarTheme {
                CustomTextBodyLarge()
            }
        }
        composeTestRule.onNodeWithTag("text").assertExists()
    }
}
