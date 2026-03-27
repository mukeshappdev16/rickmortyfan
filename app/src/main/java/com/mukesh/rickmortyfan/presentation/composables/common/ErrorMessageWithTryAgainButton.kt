package com.mukesh.rickmortyfan.presentation.composables.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorMessageWithTryAgainButton(
    errorMessage: String,
    butonLabel: String = "",
    onTryAgainClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = errorMessage,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
            if (butonLabel.isNotEmpty()) {
                Button(onClick = {
                    onTryAgainClick()
                }) {
                    Text(text = butonLabel)
                }
            }
        }
    }
}