package com.mukesh.rickmortyfan.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import com.mukesh.rickmortyfan.common.Constants
import com.mukesh.rickmortyfan.presentation.composables.character.characterdetail.CharacterDetailScreen
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
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(
                        title = { Text(text = "Character Detail") },
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
                                // Favourite implementation
                            }) {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "Mark as favorite"
                                )
                            }
                        }
                    )
                }
                ) { innerPadding ->
                    CharacterDetailScreen(
                        characterId = charId,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
