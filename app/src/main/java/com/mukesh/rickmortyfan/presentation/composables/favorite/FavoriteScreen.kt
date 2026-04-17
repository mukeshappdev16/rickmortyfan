package com.mukesh.rickmortyfan.presentation.composables.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mukesh.rickmortyfan.R
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
            Text("Your favorite list is empty.")
        }
    } else {
        Column(modifier = Modifier.padding(vertical = 16.dp).fillMaxSize()) {
            if (favoriteState.characterList.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.screen_characters),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                CharacterListScreen(
                    characterListState = CharacterListState(list = favoriteState.characterList),
                    onClickListener = onClickListenerCharacter
                )
            }
            if (favoriteState.locationList.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.screen_locations),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                LocationListScreen(
                    state = LocationListState(list = favoriteState.locationList),
                    onLocationClickListener = onClickListenerLocation
                )
            }
            if (favoriteState.episodeList.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.screen_episodes),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                EpisodeListScreen(
                    episodeListState = EpisodeListState(list = favoriteState.episodeList),
                    onClickListener = onClickListenerEpisode
                )
            }
        }
    }
}