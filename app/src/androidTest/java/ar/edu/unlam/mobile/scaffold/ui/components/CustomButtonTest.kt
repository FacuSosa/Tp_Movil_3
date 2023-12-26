package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import org.junit.Rule
import org.junit.Test

class CustomButtonTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenHavingDefaultStatesInCustomButton_VerifyIfAllViewsExists() {
        composeTestRule.setContent {
            ComicWarTheme {
                CustomButton()
            }
        }
        composeTestRule.onNodeWithTag(testTag = "elevated button").assertExists()
        composeTestRule.onNodeWithTag(testTag = "label", useUnmergedTree = true).assertExists()
    }

    @Test
    fun whenHavingDefaultStatesInCustomTextButton_VerifyIfAllViewsExists() {
        composeTestRule.setContent {
            ComicWarTheme {
                CustomTextButton()
            }
        }
        composeTestRule.onNodeWithTag("text button").assertExists()
        composeTestRule.onNodeWithTag(testTag = "label", useUnmergedTree = true).assertExists()
    }
}
