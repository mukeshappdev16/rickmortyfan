package com.mukesh.rickmortyfan.presentation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.ui.graphics.vector.ImageVector
import com.mukesh.rickmortyfan.R
import kotlinx.serialization.Serializable


@Serializable
object CharactersRoute

@Serializable
object LocationsRoute

@Serializable
object EpisodesRoute

@Serializable
object FavoriteRoute

@Serializable
object ProfileRoute
enum class Screen(
    val route: Any,
    val titleResId: Int,
    val icon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME(
        CharactersRoute, R.string.screen_characters, Icons.Default.Home,
        Icons.Outlined.Home
    ),
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
    FAVORITE(
        FavoriteRoute,
        R.string.screen_favorite,
        Icons.Default.Favorite,
        Icons.Outlined.FavoriteBorder
    ),
    PROFILE(
        ProfileRoute,
        R.string.screen_profile,
        Icons.Default.Person,
        Icons.Outlined.Person
    )
}