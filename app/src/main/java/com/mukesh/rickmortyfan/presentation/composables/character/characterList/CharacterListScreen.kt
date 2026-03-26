package com.mukesh.rickmortyfan.presentation.composables.character.characterList

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    characterListViewModal: CharacterListViewModal = hiltViewModel(),
    onClickListener: (CharacterDescription) -> Unit
) {
    val characterListState by characterListViewModal.characterListState

    when {
        characterListState.isLoading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        characterListState.errorMessage != "" -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = characterListState.errorMessage,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        characterListState.list.isNotEmpty() -> {
            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(characterListState.list) { item ->
                    CharacterListRow(item, onClickListener)
                }
            }
        }

    }
}

@Composable
fun CharacterListRow(
    characterDescription: CharacterDescription,
    onClickListener: (CharacterDescription) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClickListener(characterDescription) },
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

            // Optional: Add a chevron icon to indicate clickability
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.LightGray
            )
        }
    }
}