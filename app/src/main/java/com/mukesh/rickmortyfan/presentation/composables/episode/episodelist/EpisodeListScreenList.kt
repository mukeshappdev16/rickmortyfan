package com.mukesh.rickmortyfan.presentation.composables.episode.episodelist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mukesh.rickmortyfan.R
import com.mukesh.rickmortyfan.domain.modal.episode.Episode
import com.mukesh.rickmortyfan.presentation.composables.common.ErrorMessageWithTryAgainButton
import com.mukesh.rickmortyfan.presentation.composables.common.LoadingIndicator

@Composable
fun EpisodeListScreen(
    modifier: Modifier = Modifier,
    episodeListViewModel: EpisodeListViewModel = hiltViewModel(),
    onClickListener: (Episode) -> Unit
) {
    val episodeListState by episodeListViewModel.episodeListState

    Box(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        when {
            episodeListState.noInternet -> {
                ErrorMessageWithTryAgainButton(
                    errorMessage = stringResource(R.string.error_no_internet),
                    butonLabel = stringResource(R.string.action_try_again)
                ) {
                    episodeListViewModel.getEpisodes()
                }
            }

            episodeListState.isLoading -> {
                LoadingIndicator()
            }

            episodeListState.errorMessage != "" -> {
                ErrorMessageWithTryAgainButton(errorMessage = episodeListState.errorMessage)
            }

            episodeListState.list.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(episodeListState.list) { item ->
                        EpisodeListRow(item, onClickListener)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun EpisodeListRow(
    episode: Episode,
    onClickListener: (Episode) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickListener(episode) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = episode.episode,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = episode.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.released_prefix, episode.air_date),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
