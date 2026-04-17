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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowHeightSizeClass
import com.mukesh.rickmortyfan.auth.presentation.AuthGraph
import com.mukesh.rickmortyfan.auth.presentation.authNavGraph
import com.mukesh.rickmortyfan.common.Constants
import com.mukesh.rickmortyfan.presentation.composables.character.characterList.CharacterListScreen
import com.mukesh.rickmortyfan.presentation.composables.character.characterList.CharacterListViewModel
import com.mukesh.rickmortyfan.presentation.composables.episode.episodelist.EpisodeListScreen
import com.mukesh.rickmortyfan.presentation.composables.episode.episodelist.EpisodeListViewModel
import com.mukesh.rickmortyfan.presentation.composables.favorite.FavoriteScreen
import com.mukesh.rickmortyfan.presentation.composables.favorite.FavoriteViewModel
import com.mukesh.rickmortyfan.presentation.composables.home.RickMortyTopBar
import com.mukesh.rickmortyfan.presentation.composables.location.locationlist.LocationListScreen
import com.mukesh.rickmortyfan.presentation.composables.location.locationlist.LocationListViewModel
import com.mukesh.rickmortyfan.presentation.composables.profile.ProfileScreen
import com.mukesh.rickmortyfan.presentation.composables.profile.ProfileViewModel
import com.mukesh.rickmortyfan.ui.theme.RickMortyFanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RickMortyHome : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickMortyFanTheme {
                val homeViewModel = hiltViewModel<RickMortyHomeViewModel>()
                val navController = rememberNavController()
                var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
                val user by homeViewModel.user
                val adaptiveInfo = currentWindowAdaptiveInfo()

                NavigationSuiteScaffold(
                    layoutType = if (user == null) {
                        NavigationSuiteType.None
                    } else if (adaptiveInfo.windowSizeClass.windowHeightSizeClass ==
                        WindowHeightSizeClass.COMPACT
                    ) {
                        NavigationSuiteType.NavigationRail
                    } else {
                        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
                    },
                    navigationSuiteItems = {
                        if (user != null) {
                            Screen.entries.forEachIndexed { index, screen ->
                                val isSelected = index == selectedItemIndex
                                item(
                                    selected = isSelected,
                                    onClick = {
                                        selectedItemIndex = index
                                        navItemClick(navController, screen.route)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (isSelected) {
                                                screen.icon
                                            } else {
                                                screen.unselectedIcon
                                            },
                                            contentDescription = null,
                                            tint = if (isSelected) {
                                                MaterialTheme.colorScheme.primary
                                            } else {
                                                MaterialTheme.colorScheme.onSurfaceVariant
                                            }
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = getString(screen.titleResId),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (isSelected) {
                                                FontWeight.Bold
                                            } else {
                                                FontWeight.Medium
                                            },
                                            color = if (isSelected) {
                                                MaterialTheme.colorScheme.primary
                                            } else {
                                                MaterialTheme.colorScheme.onSurfaceVariant
                                            }
                                        )
                                    }
                                )
                            }
                        }
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            if (user != null) {
                                RickMortyTopBar()
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
                                startDestination = if (user != null) {
                                    Screen.HOME.route
                                } else {
                                    AuthGraph
                                }
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
                                composable<FavoriteRoute> {
                                    DisplayFavoriteListScreen()
                                }
                                composable<ProfileRoute> {
                                    DisplayProfileScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun DisplayLocationsScreen() {
        val viewModel: LocationListViewModel = hiltViewModel()
        val state by viewModel.locationListState
        LocationListScreen(state = state, onLocationClickListener = { location ->
            val intent = Intent(this, LocationDetailActivity::class.java).apply {
                putExtra(
                    Constants.LOCATION_ID_KEY, location.id.toString()
                )
            }
            startActivity(intent)
        }, noInternetTryAgainClicked = {
            viewModel.getLocations()
        })
    }

    @Composable
    private fun DisplayEpisodesListScreen() {
        val episodeListViewModel: EpisodeListViewModel = hiltViewModel()
        val episodeListState by episodeListViewModel.episodeListState
        EpisodeListScreen(episodeListState = episodeListState, onClickListener = { episode ->
            val intent = Intent(this, EpisodeDetailActivity::class.java).apply {
                putExtra(
                    Constants.EPISODE_ID_KEY, episode.id.toString()
                )
            }
            startActivity(intent)
        }, noInternetTryAgainClicked = {
            episodeListViewModel.getEpisodes()
        })
    }

    @Composable
    private fun DisplayCharactersListScreen() {
        val characterListViewModel: CharacterListViewModel = hiltViewModel()
        val characterListState by characterListViewModel.characterListState
        CharacterListScreen(
            characterListState = characterListState,
            onClickListener = { characterDescription ->
                val intent = Intent(this, CharacterDetailActivity::class.java).apply {
                    putExtra(
                        Constants.CHARACTER_ID_KEY, characterDescription.id.toString()
                    )
                }
                startActivity(intent)
            },
            onInternetTryAgainClicked = {
                characterListViewModel.getCharacters()
            })
    }

    @Composable
    private fun DisplayFavoriteListScreen() {
        val favoriteViewModel: FavoriteViewModel = hiltViewModel()
        val favoriteState by favoriteViewModel.favoriteState
        FavoriteScreen(
            favoriteState = favoriteState,
            onClickListenerCharacter = {
                val intent = Intent(this, CharacterDetailActivity::class.java).apply {
                    putExtra(
                        Constants.CHARACTER_ID_KEY, it.id.toString()
                    )
                }
                startActivity(intent)
            },
            onClickListenerLocation = {
                val intent = Intent(this, LocationDetailActivity::class.java).apply {
                    putExtra(
                        Constants.LOCATION_ID_KEY, it.id.toString()
                    )
                }
                startActivity(intent)
            },
            onClickListenerEpisode = {
                val intent = Intent(this, EpisodeDetailActivity::class.java).apply {
                    putExtra(
                        Constants.EPISODE_ID_KEY, it.id.toString()
                    )
                }
                startActivity(intent)
            })
    }

    @Composable
    private fun DisplayProfileScreen() {
        val viewModel: ProfileViewModel = hiltViewModel()
        val state by viewModel.state
        ProfileScreen(state = state, onLogoutSuccess = {
            viewModel.logout()
        })
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
