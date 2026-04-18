package com.mukesh.rickmortyfan.presentation.composables.episode

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mukesh.rickmortyfan.domain.modal.episode.Episode
import com.mukesh.rickmortyfan.presentation.composables.episode.episodelist.EpisodeListScreen
import com.mukesh.rickmortyfan.presentation.composables.episode.episodelist.EpisodeListState
import org.junit.Rule
import org.junit.Test

class EpisodeListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val sampleEpisode = Episode(
        id = 1,
        name = "Pilot",
        airDate = "December 2, 2013",
        episode = "S01E01",
        characters = emptyList(),
        url = "",
        created = ""
    )

    @Test
    fun episodeList_displaysItems() {
        val state = EpisodeListState(
            list = listOf(sampleEpisode)
        )

        composeTestRule.setContent {
            EpisodeListScreen(
                episodeListState = state,
                onClickListener = {}
            )
        }

        composeTestRule.onNodeWithText("Pilot").assertIsDisplayed()
        composeTestRule.onNodeWithText("S01E01").assertIsDisplayed()
    }

    @Test
    fun episodeList_clickTriggersCallback() {
        var clickedEpisode: Episode? = null
        val state = EpisodeListState(
            list = listOf(sampleEpisode)
        )

        composeTestRule.setContent {
            EpisodeListScreen(
                episodeListState = state,
                onClickListener = { clickedEpisode = it }
            )
        }

        composeTestRule.onNodeWithText("Pilot").performClick()
        assert(clickedEpisode?.id == 1)
    }
}
