package com.mukesh.rickmortyfan.presentation.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import com.mukesh.rickmortyfan.common.Constants
import com.mukesh.rickmortyfan.presentation.composables.character.characterList.CharacterList
import com.mukesh.rickmortyfan.ui.theme.RickMortyFanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RickMortyHome : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickMortyFanTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(
                        title = { Text(text = "Ricky Morty Fan") }
                    )
                }
                ) { innerPadding ->
                    var selectedItemIndex by remember { mutableIntStateOf(0) }
                    val sizeClass = currentWindowAdaptiveInfo().windowSizeClass
                    NavigationSuiteScaffold(
                        modifier = Modifier.padding(innerPadding),
                        navigationSuiteItems = {
                            Screen.entries.forEachIndexed { index, screen ->
                                item(
                                    selected = index == selectedItemIndex,
                                    onClick = {
                                        selectedItemIndex = index
                                        navItemClick(navController, screen)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = screen.icon,
                                            contentDescription = screen.title
                                        )
                                    },
                                    label = { Text(text = screen.title) }
                                )
                            }
                        },
                        layoutType = navigationSuiteType(sizeClass)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "Characters"
                        ) {
                            composable("Characters") {
                                DisplayCharactersListScreen()
                            }
                            composable("Locations") {
                                DisplayLocationsScreen()
                            }
                            composable("Episodes") {
                                DisplayEpisodesScreen()
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun DisplayLocationsScreen() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Location")
        }
    }

    @Composable
    private fun DisplayEpisodesScreen() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Episodes")
        }
    }

    @Composable
    private fun DisplayCharactersListScreen() {
        CharacterList { characterDescription ->
            val intent = Intent(this, CharacterDetailActivity::class.java).apply {
                putExtra(
                    Constants.CHARACTER_ID_KEY, characterDescription.id.toString()
                )
            }
            startActivity(intent)
        }
    }
}

@Composable
private fun navigationSuiteType(sizeClass: WindowSizeClass): NavigationSuiteType {
    return if (sizeClass.isWidthAtLeastBreakpoint(
            widthDpBreakpoint = WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND
        )
    ) {
        NavigationSuiteType.NavigationDrawer
    } else {
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
            currentWindowAdaptiveInfo()
        )
    }
}

private fun navItemClick(navController: NavController, screen: Screen) {
    navController.navigate(screen.title) {
        popUpTo(
            navController.graph.startDestinationRoute ?: ""
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

enum class Screen(val title: String, val icon: ImageVector) {
    HOME("Characters", Icons.Default.Home),
    LOCATION("Locations", Icons.Default.LocationOn),
    EPISODES("Episodes", Icons.Default.Videocam),
}