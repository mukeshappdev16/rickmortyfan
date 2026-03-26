package com.mukesh.rickmortyfan.presentation.composables.episode.episodedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.mukesh.rickmortyfan.R
import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.episode.Episode

@Composable
fun EpisodeDetailScreen(
    episodeId: String,
    modifier: Modifier,
    episodeDetailViewModel: EpisodeDetailViewModel = hiltViewModel()
) {
    val episodeDetailScreenState by episodeDetailViewModel.episodeDetailScreenState

    LaunchedEffect(episodeId) {
        episodeDetailViewModel.getEpisodeDetail(episodeId)
    }

    when {
        episodeDetailScreenState.isLoading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        episodeDetailScreenState.errorMessage != "" -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = episodeDetailScreenState.errorMessage,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        episodeDetailScreenState.episode != null -> {
            episodeDetailScreenState.episode?.let {
                EpisodeDetail(it, episodeDetailScreenState.episodeCharList)
            }
        }
    }
}

@Composable
fun EpisodeDetail(episode: Episode, episodeCharList: List<CharacterDescription>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        HeaderSection(episode.name)

        Spacer(modifier = Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            InfoCard("Episode", episode.episode, Modifier.weight(1f))
            InfoCard("Air Date", episode.air_date, Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(20.dp))

        if (episodeCharList.isNotEmpty()) {
            Text(
                "Characters",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))
            episodeCharList.forEach {
                CharacterListItem(it)
            }

        }

        Spacer(modifier = Modifier.height(24.dp))

    }
}


@Composable
fun HeaderSection(title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Videocam, contentDescription = null)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Episode", style = MaterialTheme.typography.bodySmall, color = Color.Gray)

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun InfoCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Text(value, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun CharacterListItem(characterDescription: CharacterDescription) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
        ,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Light background
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = characterDescription.image,
                error = painterResource(R.drawable.account_circle_24),
                contentDescription = "Avatar of ${characterDescription.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)) // Slightly rounded square looks modern
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = characterDescription.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A), // Near black for high legibility
                    textAlign = TextAlign.Left
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Gender Label
                Text(
                    text = "Gender: ${characterDescription.gender}",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Left
                )

                // Status with a subtle color hint
                Text(
                    text = "Status: ${characterDescription.status}",
                    fontSize = 14.sp,
                    color = when (characterDescription.status.lowercase()) {
                        "alive" -> Color(0xFF2E7D32) // Soft Green
                        "dead" -> Color(0xFFC62828)  // Soft Red
                        else -> Color.DarkGray
                    },
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}