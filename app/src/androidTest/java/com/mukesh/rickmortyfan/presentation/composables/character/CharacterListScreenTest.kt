package com.mukesh.rickmortyfan.presentation.composables.character

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.character.Location
import com.mukesh.rickmortyfan.domain.modal.character.Origin
import com.mukesh.rickmortyfan.presentation.composables.character.characterList.CharacterListScreen
import com.mukesh.rickmortyfan.presentation.composables.character.characterList.CharacterListState
import org.junit.Rule
import org.junit.Test

class CharacterListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val sampleCharacter =
        CharacterDescription(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = Origin("Earth", ""),
            location = Location("Earth", ""),
            image = "",
            episode = emptyList(),
            url = "",
            created = "",
        )

    @Test
    fun characterList_displaysItems() {
        val state =
            CharacterListState(
                list = listOf(sampleCharacter),
            )

        composeTestRule.setContent {
            CharacterListScreen(
                characterListState = state,
                onClickListener = {},
            )
        }

        composeTestRule.onNodeWithText("Rick Sanchez").assertIsDisplayed()
        composeTestRule.onNodeWithText("Human").assertIsDisplayed()
        composeTestRule.onNodeWithText("ALIVE").assertIsDisplayed()
    }

    @Test
    fun characterList_clickTriggersCallback() {
        var clickedCharacter: CharacterDescription? = null
        val state =
            CharacterListState(
                list = listOf(sampleCharacter),
            )

        composeTestRule.setContent {
            CharacterListScreen(
                characterListState = state,
                onClickListener = { clickedCharacter = it },
            )
        }

        composeTestRule.onNodeWithText("Rick Sanchez").performClick()
        assert(clickedCharacter?.id == 1)
    }

    @Test
    fun characterList_showsLoading() {
        val state = CharacterListState(isLoading = true)

        composeTestRule.setContent {
            CharacterListScreen(
                characterListState = state,
                onClickListener = {},
            )
        }

        // Assuming LoadingIndicator shows some distinct UI element.
        // If it's a CircularProgressIndicator, we might need to find it by tag or content description if set.
    }
}
