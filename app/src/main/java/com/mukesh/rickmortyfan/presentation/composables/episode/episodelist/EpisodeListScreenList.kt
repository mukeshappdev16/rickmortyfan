package com.mukesh.rickmortyfan.presentation.composables.episode.episodelist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mukesh.rickmortyfan.R
import com.mukesh.rickmortyfan.domain.modal.episode.Episode
import com.mukesh.rickmortyfan.presentation.composables.common.ErrorMessageWithTryAgainButton
import com.mukesh.rickmortyfan.presentation.composables.common.LoadingIndicator
import com.mukesh.rickmortyfan.ui.theme.RickMortyFanTheme

@Composable
fun EpisodeListScreen(
    episodeListState: EpisodeListState,
    modifier: Modifier = Modifier,
    onClickListener: (Episode) -> Unit = {},
    noInternetTryAgainClicked: () -> Unit = {},
    loadMoreEpisodes: () -> Unit = {},
) {
    val listState = rememberLazyListState()

    val shouldLoadMore =
        remember {
            derivedStateOf {
                val totalItemsCount = listState.layoutInfo.totalItemsCount
                val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                // Load more when we are 5 items away from the end
                lastVisibleItemIndex >= totalItemsCount - 5 && totalItemsCount > 0
            }
        }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && !episodeListState.isPaginating && !episodeListState.endReached) {
            loadMoreEpisodes()
        }
    }

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
    ) {
        when {
            episodeListState.noInternet && episodeListState.list.isEmpty() -> {
                ErrorMessageWithTryAgainButton(
                    errorMessage = stringResource(R.string.error_no_internet),
                    butonLabel = stringResource(R.string.action_try_again),
                ) {
                    noInternetTryAgainClicked()
                }
            }

            episodeListState.isLoading && episodeListState.list.isEmpty() -> {
                LoadingIndicator()
            }

            episodeListState.errorMessage != "" && episodeListState.list.isEmpty() -> {
                ErrorMessageWithTryAgainButton(errorMessage = episodeListState.errorMessage) {
                    noInternetTryAgainClicked()
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    state = listState,
                ) {
                    itemsIndexed(episodeListState.list) { index, item ->
                        EpisodeListRow(item, onClickListener)
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    if (episodeListState.isPaginating) {
                        item {
                            Box(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(32.dp),
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EpisodeListRow(
    episode: Episode,
    onClickListener: (Episode) -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClickListener(episode) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = episode.episode,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = episode.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.released_prefix, episode.airDate),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EpisodeListScreenPreview() {
    RickMortyFanTheme {
        val sampleEpisode =
            Episode(
                id = 1,
                name = "Pilot",
                airDate = "December 2, 2013",
                episode = "S01E01",
                characters = listOf(),
                url = "",
                created = "",
            )
        EpisodeListScreen(
            episodeListState =
                EpisodeListState(
                    list =
                        listOf(
                            sampleEpisode,
                            sampleEpisode.copy(id = 2, name = "Lawnmower Dog", episode = "S01E02"),
                            sampleEpisode.copy(id = 3, name = "Anatomy Park", episode = "S01E03"),
                            sampleEpisode.copy(id = 4, name = "M. Night Shaym-Aliens!", episode = "S01E04"),
                            sampleEpisode.copy(id = 5, name = "Meeseeks and Destroy", episode = "S01E05"),
                        ),
                ),
            onClickListener = {},
        )
    }
}
