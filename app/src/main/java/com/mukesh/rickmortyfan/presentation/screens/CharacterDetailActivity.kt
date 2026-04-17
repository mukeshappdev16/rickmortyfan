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
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import com.mukesh.rickmortyfan.presentation.composables.character.characterdetail.CharacterDetailScreen
import com.mukesh.rickmortyfan.presentation.composables.character.characterdetail.CharacterDetailViewModel
import com.mukesh.rickmortyfan.ui.theme.RickMortyFanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val charId = intent.getStringExtra(Constants.CHARACTER_ID_KEY) ?: ""
        enableEdgeToEdge()
        setContent {
            RickMortyFanTheme {
                val characterDetailViewModel = hiltViewModel<CharacterDetailViewModel>()
                val characterDetailScreenState by characterDetailViewModel.characterDetailScreenState
                LaunchedEffect(charId) {
                    characterDetailViewModel.getCharacterDetail(charId)
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Character Detail",
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
                                    characterDetailScreenState.characterDescription?.let {
                                        if (characterDetailScreenState.isFavorite) {
                                            characterDetailViewModel.removeFavoriteCharacter(it)
                                        } else {
                                            characterDetailViewModel.addFavoriteCharacter(it)
                                        }
                                    }
                                }) {
                                    Icon(
                                        imageVector = if (characterDetailScreenState.isFavorite) {
                                            Icons.Default.Favorite
                                        } else {
                                            Icons.Default.FavoriteBorder
                                        },
                                        contentDescription = "Mark as favorite",
                                        tint = if (characterDetailScreenState.isFavorite) {
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
                    CharacterDetailScreen(
                        modifier = Modifier.padding(innerPadding),
                        characterDetailScreenState = characterDetailScreenState
                    ) {
                        //No internet try again clicked listener
                        characterDetailViewModel.getCharacterDetail(charId)
                    }
                }
            }
        }
    }
}
