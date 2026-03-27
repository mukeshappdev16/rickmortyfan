package com.mukesh.rickmortyfan.presentation.composables.location.locationdetail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.mukesh.rickmortyfan.R
import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription
import com.mukesh.rickmortyfan.domain.modal.location.LocationDetail
import com.mukesh.rickmortyfan.presentation.composables.common.ErrorMessageWithTryAgainButton
import com.mukesh.rickmortyfan.presentation.composables.common.LoadingIndicator

@Composable
fun LocationDetailScreen(
    locationId: String,
    modifier: Modifier,
    viewModel: LocationDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state

    LaunchedEffect(locationId) {
        viewModel.getLocationDetail(locationId)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when {
            state.noInternet -> {
                ErrorMessageWithTryAgainButton(
                    errorMessage = stringResource(R.string.error_no_internet),
                    butonLabel = stringResource(R.string.action_try_again)
                ) {
                    viewModel.getLocationDetail(locationId)
                }
            }

            state.isLoading -> {
                LoadingIndicator()
            }

            state.errorMessage != "" -> {
                ErrorMessageWithTryAgainButton(state.errorMessage)
            }

            state.location != null -> {
                state.location?.let {
                    LocationDetailContent(it, state.residents)
                }
            }
        }
    }
}

@Composable
fun LocationDetailContent(location: LocationDetail, residents: List<CharacterDescription>) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LocationDetailLandscape(location, residents)
    } else {
        LocationDetailPortrait(location, residents)
    }
}

@Composable
private fun LocationDetailPortrait(
    location: LocationDetail,
    residents: List<CharacterDescription>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Hero Header Section
        LocationHeader(location)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            LocationInfoContent(location, residents)
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun LocationDetailLandscape(
    location: LocationDetail,
    residents: List<CharacterDescription>
) {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        // Left Side: Fixed Header and Info
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp)
        ) {
            Text(
                text = location.type.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = location.name,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(32.dp))

            InfoItem(
                label = stringResource(R.string.label_dimension),
                value = location.dimension
            )
        }

        // Right Side: Scrollable Residents list
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(1.5f)
                .fillMaxHeight()
                .verticalScroll(scrollState)
                .padding(24.dp)
        ) {
            if (residents.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.label_residents),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    residents.forEach {
                        ResidentItem(it)
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(R.string.no_residents_available),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun LocationHeader(location: LocationDetail) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp)
    ) {
        Column {
            Text(
                text = location.type.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = location.name,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun LocationInfoContent(location: LocationDetail, residents: List<CharacterDescription>) {
    InfoItem(
        label = stringResource(R.string.label_dimension),
        value = location.dimension
    )

    if (residents.isNotEmpty()) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = stringResource(R.string.label_residents),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            residents.forEach {
                ResidentItem(it)
            }
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun ResidentItem(character: CharacterDescription) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = character.image,
                error = painterResource(R.drawable.account_circle_24),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = character.species,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
