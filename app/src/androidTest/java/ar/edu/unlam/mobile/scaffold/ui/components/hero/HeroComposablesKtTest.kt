package ar.edu.unlam.mobile.scaffold.ui.components.hero

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import org.junit.Rule
import org.junit.Test

class HeroComposablesKtTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    private val heroList = listOf(
        HeroModel(id = 1, name = "Test 1"),
        HeroModel(id = 2, name = "Test 2"),
        HeroModel(id = 3, name = "Test 3"),
        HeroModel(id = 4, name = "Test 4"),
        HeroModel(id = 5, name = "Test 5"),
        HeroModel(id = 6, name = "Test 6"),
        HeroModel(id = 7, name = "Test 7"),
        HeroModel(id = 8, name = "Test 8"),
        HeroModel(id = 9, name = "Test 9"),
        HeroModel(id = 10, name = "Test 10")
    )

    @Test
    fun whenProvidingDefaultStateInGalleryItem_VerifyAllViewsExists() {
        composeTestRule.setContent {
            ComicWarTheme {
                GalleryItem(onItemClick = {})
            }
        }
        composeTestRule.onNodeWithTag(testTag = "profile image", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag(testTag = "id name text", useUnmergedTree = true).assertExists()
        composeTestRule.onNode(matcher = hasClickAction()).assertExists()
    }

    @Test
    fun whenProvidingDefaultStateInHeroGallery_VerifyAllViewsExists() {
        composeTestRule.setContent {
            ComicWarTheme {
                HeroGallery(heroList = heroList, onItemClick = {})
            }
        }
        composeTestRule.onNodeWithTag(testTag = "lazy grid").assertExists()
        for (i in heroList.indices) {
            composeTestRule.onNodeWithTag(testTag = "gallery item $i").assertExists()
            composeTestRule.onNode(matcher = hasText("${i + 1} Test ${i + 1}")).assertExists()
        }
    }
}
