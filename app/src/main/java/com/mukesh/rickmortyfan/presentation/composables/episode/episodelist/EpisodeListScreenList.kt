package com.mukesh.rickmortyfan.presentation.composables.episode.episodelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mukesh.rickmortyfan.domain.modal.episode.Episode

@Composable
fun EpisodeListScreen(
    modifier: Modifier = Modifier,
    episodeListViewModel: EpisodeListViewModel = hiltViewModel(),
    onClickListener: (Episode) -> Unit
) {
    val episodeListState by episodeListViewModel.episodeListState

    when {
        episodeListState.isLoading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        episodeListState.errorMessage != "" -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = episodeListState.errorMessage,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        episodeListState.list.isNotEmpty() -> {
            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(episodeListState.list) { item ->
                    EpisodeListRow(item, onClickListener)
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
            .padding(8.dp)
            .clickable { onClickListener(episode) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        // Set the light row background color here
        colors = CardDefaults.cardColors(
            containerColor = Color.White // or any light color, e.g., Color(0xFFF5F5F5)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Episode Code (e.g., S01E10)
                Text(
                    text = episode.episode,
                    style = MaterialTheme.typography.labelMedium,
                    // Keeping the Rick Green, but you might want to adjust it slightly for contrast
                    color = Color(0xFF97CE4C)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Episode Name
            Text(
                text = episode.name,
                fontSize = 18.sp,
                style = MaterialTheme.typography.headlineSmall,
                // Changed from White to a dark color for visibility
                color = Color.Black, // or MaterialTheme.colorScheme.onSurface
                fontWeight = FontWeight.Bold
            )

            // Air Date
            Text(
                text = "Aired: ${episode.air_date}",
                style = MaterialTheme.typography.bodyMedium,
                // Gray might still work, but you can make it slightly darker if needed
                color = Color.DarkGray // or MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}