package com.mukesh.rickmortyfan.presentation.composables.location.locationlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import com.mukesh.rickmortyfan.R
import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail
import com.mukesh.rickmortyfan.presentation.composables.common.ErrorMessageWithTryAgainButton
import com.mukesh.rickmortyfan.presentation.composables.common.LoadingIndicator
import com.mukesh.rickmortyfan.ui.theme.RickMortyFanTheme

@Composable
fun LocationListScreen(
    modifier: Modifier = Modifier,
    state: LocationListState,
    onLocationClickListener: (LocationDetail) -> Unit,
    noInternetTryAgainClicked: () -> Unit = {},
    loadMoreLocations: () -> Unit = {},
) {
    val listState = rememberLazyListState()

    val shouldLoadMore =
        remember {
            derivedStateOf {
                val totalItemsCount = listState.layoutInfo.totalItemsCount
                val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                lastVisibleItemIndex >= totalItemsCount - 5 && totalItemsCount > 0
            }
        }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && !state.isPaginating && !state.endReached) {
            loadMoreLocations()
        }
    }

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
    ) {
        when {
            state.noInternet && state.list.isEmpty() -> {
                ErrorMessageWithTryAgainButton(
                    errorMessage = stringResource(R.string.error_no_internet),
                    butonLabel = stringResource(R.string.action_try_again),
                ) {
                    noInternetTryAgainClicked()
                }
            }

            state.isLoading && state.list.isEmpty() -> {
                LoadingIndicator()
            }

            state.errorMessage != "" && state.list.isEmpty() -> {
                ErrorMessageWithTryAgainButton(errorMessage = state.errorMessage) {
                    noInternetTryAgainClicked()
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    state = listState,
                ) {
                    itemsIndexed(state.list) { index, item ->
                        LocationListRow(item, onLocationClickListener)
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    if (state.isPaginating) {
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
fun LocationListRow(
    location: LocationDetail,
    onClickListener: (LocationDetail) -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClickListener(location) },
        shape = RoundedCornerShape(12.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Public,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp),
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = location.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${location.type} • ${location.dimension}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationListScreenPreview() {
    RickMortyFanTheme {
        val sampleLocation =
            LocationDetail(
                id = 1,
                name = "Earth (C-137)",
                type = "Planet",
                dimension = "Dimension C-137",
                residents = listOf(),
                url = "",
                created = "",
            )
        LocationListScreen(
            state =
                LocationListState(
                    list =
                        listOf(
                            sampleLocation,
                            sampleLocation.copy(id = 2, name = "Abadango", type = "Cluster"),
                            sampleLocation.copy(id = 3, name = "Citadel of Ricks", type = "Space station"),
                            sampleLocation.copy(id = 4, name = "Worldender's lair", type = "Planet"),
                            sampleLocation.copy(id = 5, name = "Anatomy Park", type = "Microverse"),
                        ),
                ),
            onLocationClickListener = {},
        )
    }
}
