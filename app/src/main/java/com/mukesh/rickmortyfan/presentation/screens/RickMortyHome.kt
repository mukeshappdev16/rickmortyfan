package com.mukesh.rickmortyfan.presentation.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mukesh.rickmortyfan.R
import com.mukesh.rickmortyfan.auth.presentation.AuthGraph
import com.mukesh.rickmortyfan.auth.presentation.authNavGraph
import com.mukesh.rickmortyfan.common.Constants
import com.mukesh.rickmortyfan.presentation.composables.character.characterList.CharacterListScreen
import com.mukesh.rickmortyfan.presentation.composables.episode.episodelist.EpisodeListScreen
import com.mukesh.rickmortyfan.presentation.composables.location.locationlist.LocationListScreen
import com.mukesh.rickmortyfan.ui.theme.RickMortyFanTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class RickMortyHome : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickMortyFanTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                // Determine if we should show the bottom bar and top bar
                val isAuthRoute = currentDestination?.route?.contains("LoginRoute") == true ||
                        currentDestination?.route?.contains("SignUpRoute") == true ||
                        currentDestination?.route?.contains("AuthGraph") == true

                var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (!isAuthRoute) {
                            RickMortyTopBar()
                        }
                    },
                    bottomBar = {
                        if (!isAuthRoute) {
                            BottomNavigationBar(selectedItemIndex) { index, screen ->
                                selectedItemIndex = index
                                navItemClick(navController, screen.route)
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = AuthGraph
                        ) {
                            authNavGraph(
                                navController = navController,
                                onLoginSuccess = {
                                    navController.navigate(CharactersRoute) {
                                        popUpTo(AuthGraph) { inclusive = true }
                                    }
                                }
                            )
                            composable<CharactersRoute> {
                                DisplayCharactersListScreen()
                            }
                            composable<LocationsRoute> {
                                DisplayLocationsScreen()
                            }
                            composable<EpisodesRoute> {
                                DisplayEpisodesListScreen()
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun RickMortyTopBar() {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.title_rick_and_morty),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-1).sp
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }

    @Composable
    private fun BottomNavigationBar(selectedItemIndex: Int, onItemSelected: (Int, Screen) -> Unit) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp
        ) {
            Screen.entries.forEachIndexed { index, screen ->
                val isSelected = index == selectedItemIndex
                val label = stringResource(screen.titleResId)
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        onItemSelected(index, screen)
                    },
                    icon = {
                        Icon(
                            imageVector = if (isSelected) screen.icon else screen.unselectedIcon,
                            contentDescription = label
                        )
                    },
                    label = {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.1f
                        ),
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }


    @Composable
    private fun DisplayLocationsScreen() {
        LocationListScreen { location ->
            val intent = Intent(this, LocationDetailActivity::class.java).apply {
                putExtra(
                    Constants.LOCATION_ID_KEY, location.id.toString()
                )
            }
            startActivity(intent)
        }
    }

    @Composable
    private fun DisplayEpisodesListScreen() {
        EpisodeListScreen { episode ->
            val intent = Intent(this, EpisodeDetailActivity::class.java).apply {
                putExtra(
                    Constants.EPISODE_ID_KEY, episode.id.toString()
                )
            }
            startActivity(intent)
        }
    }

    @Composable
    private fun DisplayCharactersListScreen() {
        CharacterListScreen { characterDescription ->
            val intent = Intent(this, CharacterDetailActivity::class.java).apply {
                putExtra(
                    Constants.CHARACTER_ID_KEY, characterDescription.id.toString()
                )
            }
            startActivity(intent)
        }
    }
}

private fun navItemClick(navController: NavController, route: Any) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}


@Serializable
object CharactersRoute

@Serializable
object LocationsRoute

@Serializable
object EpisodesRoute

enum class Screen(
    val route: Any,
    val titleResId: Int,
    val icon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME(CharactersRoute, R.string.screen_characters, Icons.Default.Home, Icons.Outlined.Home),
    LOCATION(
        LocationsRoute,
        R.string.screen_locations,
        Icons.Default.LocationOn,
        Icons.Outlined.LocationOn
    ),
    EPISODES(
        EpisodesRoute,
        R.string.screen_episodes,
        Icons.Default.Videocam,
        Icons.Outlined.Videocam
    ),
}
