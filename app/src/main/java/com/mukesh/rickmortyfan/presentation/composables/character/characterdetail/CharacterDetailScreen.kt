package com.mukesh.rickmortyfan.presentation.composables.character.characterdetail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.mukesh.rickmortyfan.R
import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.episode.Episode
import com.mukesh.rickmortyfan.presentation.composables.common.ErrorMessageWithTryAgainButton
import com.mukesh.rickmortyfan.presentation.composables.common.LoadingIndicator

@Composable
fun CharacterDetailScreen(
    modifier: Modifier,
    characterDetailScreenState: CharacterDetailScreenState,
    noInternetTryAgainClicked: () -> Unit,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        when {
            characterDetailScreenState.noInternet -> {
                ErrorMessageWithTryAgainButton(
                    errorMessage = stringResource(R.string.error_no_internet),
                    butonLabel = stringResource(R.string.action_try_again),
                ) {
                    noInternetTryAgainClicked()
                }
            }

            characterDetailScreenState.isLoading -> {
                LoadingIndicator()
            }

            characterDetailScreenState.errorMessage != "" -> {
                ErrorMessageWithTryAgainButton(errorMessage = characterDetailScreenState.errorMessage)
            }

            characterDetailScreenState.characterDescription != null -> {
                characterDetailScreenState.characterDescription?.let {
                    CharacterDetail(
                        characterDescription = it,
                        episodeList = characterDetailScreenState.episodes,
                    )
                }
            }
        }
    }
}

@Composable
private fun CharacterDetail(
    characterDescription: CharacterDescription,
    episodeList: List<Episode>,
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        CharacterDetailLandscape(characterDescription, episodeList)
    } else {
        CharacterDetailPortrait(characterDescription, episodeList)
    }
}

@Composable
private fun CharacterDetailPortrait(
    characterDescription: CharacterDescription,
    episodeList: List<Episode>,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier =
            Modifier
                .verticalScroll(scrollState)
                .fillMaxSize(),
    ) {
        // Hero Image with Gradient Overlay
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
        ) {
            AsyncImage(
                model = characterDescription.image,
                error = painterResource(R.drawable.account_circle_24),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            // Gradient Overlay for text readability
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors =
                                    listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                                        MaterialTheme.colorScheme.background,
                                    ),
                                startY = 300f,
                            ),
                        ),
            )

            // Name and Status over Image
            Column(
                modifier =
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(24.dp),
            ) {
                StatusBadgeDetail(characterDescription.status)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = characterDescription.name,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            CharacterInfoContent(characterDescription, episodeList)
        }
    }
}

@Composable
private fun CharacterDetailLandscape(
    characterDescription: CharacterDescription,
    episodeList: List<Episode>,
) {
    Row(
        modifier = Modifier.fillMaxSize(),
    ) {
        // Left Side: Sticky Image
        Box(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxHeight(),
        ) {
            AsyncImage(
                model = characterDescription.image,
                error = painterResource(R.drawable.account_circle_24),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors =
                                    listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
                                        MaterialTheme.colorScheme.background,
                                    ),
                                startX = 400f,
                            ),
                        ),
            )

            Column(
                modifier =
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(24.dp),
            ) {
                StatusBadgeDetail(characterDescription.status)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = characterDescription.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        // Right Side: Scrollable Details
        val scrollState = rememberScrollState()
        Column(
            modifier =
                Modifier
                    .weight(1.2f)
                    .fillMaxHeight()
                    .verticalScroll(scrollState)
                    .padding(24.dp),
        ) {
            CharacterInfoContent(characterDescription, episodeList)
        }
    }
}

@Composable
private fun CharacterInfoContent(
    characterDescription: CharacterDescription,
    episodeList: List<Episode>,
) {
    // Stats Grid
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        InfoCardDetail(
            label = stringResource(R.string.label_gender),
            value = characterDescription.gender,
            modifier = Modifier.weight(1f),
        )
        InfoCardDetail(
            label = stringResource(R.string.label_species),
            value = characterDescription.species,
            modifier = Modifier.weight(1f),
        )
    }

    Spacer(modifier = Modifier.height(24.dp))
    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    Spacer(modifier = Modifier.height(24.dp))

    // Location Section
    LocationSection(
        icon = Icons.Default.Public,
        title = stringResource(R.string.label_origin),
        location = characterDescription.origin?.name ?: "",
    )
    Spacer(modifier = Modifier.height(20.dp))
    LocationSection(
        icon = Icons.Default.LocationOn,
        title = stringResource(R.string.label_last_known_location),
        location = characterDescription.location?.name ?: "",
    )

    if (episodeList.isNotEmpty()) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = stringResource(R.string.label_appears_in),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 1.5.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            episodeList.forEach {
                EpisodeItemDetail(it)
            }
        }
    }

    Spacer(modifier = Modifier.height(40.dp))
}

@Composable
fun StatusBadgeDetail(status: String) {
    val color =
        when (status.lowercase()) {
            "alive" -> Color(0xFF4CAF50)
            "dead" -> Color(0xFFF44336)
            else -> Color.White
        }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .background(color.copy(alpha = 0.2f), RoundedCornerShape(100.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .size(8.dp)
                    .background(color, CircleShape),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = status.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Black,
            color = color,
            letterSpacing = 1.sp,
        )
    }
}

@Composable
fun InfoCardDetail(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                .padding(16.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun LocationSection(
    icon: ImageVector,
    title: String,
    location: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier =
                Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp),
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp,
            )
            Text(
                text = location,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun EpisodeItemDetail(episode: Episode) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Videocam,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = episode.episode,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = episode.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
