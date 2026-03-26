package com.mukesh.rickmortyfan.presentation.composables.character.characterdetail

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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
fun CharacterDetailScreen(
    characterId: String,
    modifier: Modifier,
    characterDetailViewModal: CharacterDetailViewModal = hiltViewModel()
) {
    val characterDetailScreenState by characterDetailViewModal.characterDetailScreenState
    LaunchedEffect(characterId) {
        characterDetailViewModal.getCharacterDetail(characterId)
    }

    when {
        characterDetailScreenState.isLoading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        characterDetailScreenState.errorMessage != "" -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = characterDetailScreenState.errorMessage,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        characterDetailScreenState.characterDescription != null -> {
            characterDetailScreenState.characterDescription?.let {
                CharacterDetail(
                    modifier = modifier,
                    characterDescription = it,
                    episodeList = characterDetailScreenState.episodes
                )
            }

        }
    }


}

@Composable
private fun CharacterDetail(
    modifier: Modifier,
    characterDescription: CharacterDescription,
    episodeList: List<Episode>
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Image Placeholder
        Box(
            modifier = Modifier
                .size(148.dp)
                .clip(CircleShape)
                .background(Color.Green.copy(alpha = 0.2f))
                .padding(4.dp)
        ) {
            AsyncImage(
                model = characterDescription.image,
                error = painterResource(R.drawable.account_circle_24),
                contentDescription = null,
            )
        }

        Text(
            text = "Status: ${characterDescription.status}",
            color = if (characterDescription.status == "Alive") {
                Color.Green
            } else {
                Color.Red
            },
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = characterDescription.name,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoCard(
                label = "Gender",
                value = characterDescription.gender,
                modifier = Modifier.weight(1f)
            )
            InfoCard(
                label = "Species",
                value = characterDescription.species,
                modifier = Modifier.weight(1f)
            )
        }

        if (characterDescription.type.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            InfoCard(
                label = "Type",
                value = characterDescription.type,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Location Section
        Text(
            text = "Location",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 8.dp)
        )

        LocationItem(
            icon = Icons.Default.Public,
            title = "Origin",
            location = characterDescription.origin.name
        )
        Spacer(modifier = Modifier.height(8.dp))
        LocationItem(
            icon = Icons.Default.LocationOn,
            title = "Current",
            location = characterDescription.location.name
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (episodeList.isNotEmpty()) {
            // Episodes Section
            Text(
                text = "Episodes",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )

            Column(modifier = Modifier.padding(16.dp)) {
                episodeList.forEach {
                    EpisodeItem(it)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun InfoCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = label, fontSize = 12.sp, color = Color.Gray)
            Text(text = value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun LocationItem(icon: ImageVector, title: String, location: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, fontSize = 12.sp, color = Color.Gray)
                Text(text = location, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun EpisodeItem(episode: Episode) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Videocam,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Row(Modifier.fillMaxWidth()) {
                    Text(text = episode.episode, fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = episode.air_date, fontSize = 12.sp, color = Color.Gray)
                }
                Text(text = episode.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            }
        }
    }
}