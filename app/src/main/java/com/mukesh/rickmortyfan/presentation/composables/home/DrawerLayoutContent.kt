package com.mukesh.rickmortyfan.presentation.composables.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mukesh.rickmortyfan.R
import com.mukesh.rickmortyfan.auth.domain.modal.RickMortyUser
import kotlinx.coroutines.launch

@Composable
fun DrawerLayoutContent(
    drawerState: DrawerState,
    user: RickMortyUser?,
    logoutClicked: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = stringResource(R.string.profile_picture),
                modifier =
                    Modifier
                        .size(50.dp)
                        .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = user?.name ?: "",
            )
        }
        HorizontalDivider()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        scope.launch { drawerState.close() }
                    },
        ) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
            Spacer(modifier = Modifier.width(4.dp))
            Text(stringResource(R.string.settings))
        }
        HorizontalDivider()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        scope.launch {
                            drawerState.close()
                            logoutClicked()
                        }
                    },
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = null)
            Spacer(modifier = Modifier.width(4.dp))
            Text(stringResource(R.string.logout))
        }
    }
}
