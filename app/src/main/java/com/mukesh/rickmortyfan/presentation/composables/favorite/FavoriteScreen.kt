package com.mukesh.rickmortyfan.presentation.composables.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.episode.Episode
import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail
import com.mukesh.rickmortyfan.presentation.composables.character.characterList.CharacterListScreen
import com.mukesh.rickmortyfan.presentation.composables.character.characterList.CharacterListState
import com.mukesh.rickmortyfan.presentation.composables.episode.episodelist.EpisodeListScreen
import com.mukesh.rickmortyfan.presentation.composables.episode.episodelist.EpisodeListState
import com.mukesh.rickmortyfan.presentation.composables.location.locationlist.LocationListScreen
import com.mukesh.rickmortyfan.presentation.composables.location.locationlist.LocationListState

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    favoriteState: FavoriteState,
    onClickListenerCharacter: (CharacterDescription) -> Unit,
    onClickListenerLocation: (LocationDetail) -> Unit,
    onClickListenerEpisode: (Episode) -> Unit
) {
    if (favoriteState.characterList.isEmpty() &&
        favoriteState.locationList.isEmpty() &&
        favoriteState.episodeList.isEmpty()
    ) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Your favorite list is empty. Add some favorites!")
        }
    } else {
        Column(modifier = modifier.fillMaxSize()) {
            if (favoriteState.characterList.isNotEmpty()) {
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CharacterListScreen(
                        characterListState = CharacterListState(list = favoriteState.characterList),
                        onClickListener = onClickListenerCharacter
                    )
                }
            }
            if (favoriteState.locationList.isNotEmpty()) {
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    LocationListScreen(
                        state = LocationListState(list = favoriteState.locationList),
                        onLocationClickListener = onClickListenerLocation
                    )
                }
            }
            if (favoriteState.episodeList.isNotEmpty()) {
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    EpisodeListScreen(
                        episodeListState = EpisodeListState(list = favoriteState.episodeList),
                        onClickListener = onClickListenerEpisode
                    )
                }
            }
        }
    }
}