package com.mukesh.rickmortyfan.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mukesh.rickmortyfan.common.Constants
import com.mukesh.rickmortyfan.presentation.composables.location.locationdetail.LocationDetailScreen
import com.mukesh.rickmortyfan.presentation.composables.location.locationdetail.LocationDetailViewModel
import com.mukesh.rickmortyfan.ui.theme.RickMortyFanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationDetailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locationId = intent.getStringExtra(Constants.LOCATION_ID_KEY) ?: ""
        enableEdgeToEdge()
        setContent {
            RickMortyFanTheme {
                val viewModel: LocationDetailViewModel = hiltViewModel()
                val state by viewModel.stateDetailState
                LaunchedEffect(locationId) {
                    viewModel.getLocationDetail(locationId)
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Location Detail",
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Go back"
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = {
                                    state.location?.let {
                                        if (state.isFavorite) {
                                            viewModel.removeFavoriteLocation(it)
                                        } else {
                                            viewModel.addFavoriteLocation(it)
                                        }
                                    }
                                }) {
                                    Icon(
                                        imageVector = if (state.isFavorite) {
                                            Icons.Default.Favorite
                                        } else {
                                            Icons.Default.FavoriteBorder
                                        },
                                        contentDescription = "Mark as favorite",
                                        tint = if (state.isFavorite) {
                                            Color.Red
                                        } else {
                                            Color.Black
                                        }
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                titleContentColor = MaterialTheme.colorScheme.onSurface,
                                navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                                actionIconContentColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                ) { innerPadding ->
                    LocationDetailScreen(
                        state = state,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        viewModel.getLocationDetail(locationId)
                    }
                }
            }
        }
    }
}
