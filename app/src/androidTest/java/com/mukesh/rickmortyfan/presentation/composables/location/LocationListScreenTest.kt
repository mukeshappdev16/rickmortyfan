package com.mukesh.rickmortyfan.presentation.composables.location

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail
import com.mukesh.rickmortyfan.presentation.composables.location.locationlist.LocationListScreen
import com.mukesh.rickmortyfan.presentation.composables.location.locationlist.LocationListState
import org.junit.Rule
import org.junit.Test

class LocationListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val sampleLocation = LocationDetail(
        id = 1,
        name = "Earth (C-137)",
        type = "Planet",
        dimension = "Dimension C-137",
        residents = emptyList(),
        url = "",
        created = ""
    )

    @Test
    fun locationList_displaysItems() {
        val state = LocationListState(
            list = listOf(sampleLocation)
        )

        composeTestRule.setContent {
            LocationListScreen(
                state = state,
                onLocationClickListener = {}
            )
        }

        composeTestRule.onNodeWithText("Earth (C-137)").assertIsDisplayed()
        composeTestRule.onNodeWithText("Planet • Dimension C-137").assertIsDisplayed()
    }

    @Test
    fun locationList_clickTriggersCallback() {
        var clickedLocation: LocationDetail? = null
        val state = LocationListState(
            list = listOf(sampleLocation)
        )

        composeTestRule.setContent {
            LocationListScreen(
                state = state,
                onLocationClickListener = { clickedLocation = it }
            )
        }

        composeTestRule.onNodeWithText("Earth (C-137)").performClick()
        assert(clickedLocation?.id == 1)
    }
}
