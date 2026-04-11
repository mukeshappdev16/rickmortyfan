package com.mukesh.rickmortyfan.presentation.composables.home

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mukesh.rickmortyfan.presentation.screens.Screen

@Composable
fun BottomNavigationBar(
    selectedItemIndex: Int,
    onItemSelected: (Int, Screen) -> Unit
) {
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
