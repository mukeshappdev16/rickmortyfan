package com.mukesh.rickmortyfan.presentation.composables.character.characterList

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.mukesh.rickmortyfan.R
import com.mukesh.rickmortyfan.domain.modal.character.CharacterDescription

@SuppressLint("UnrememberedMutableState")
@Composable
fun CharacterList(
    modifier: Modifier = Modifier,
    characterListViewModal: CharacterListViewModal = hiltViewModel()
) {
    val characterListState by characterListViewModal.characterListState

    if (characterListState.isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (characterListState.errorMessage != "") {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = characterListState.errorMessage,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(characterListState.list) { item ->
                CharacterListRow(item)
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun CharacterListRow(characterDescription: CharacterDescription) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = characterDescription.image,
            error = painterResource(R.drawable.account_circle_24),
            contentDescription = null,
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = characterDescription.name,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                textAlign = TextAlign.Left
            )
            Text(
                text = "Gender: ${characterDescription.gender}",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 14.sp,
                textAlign = TextAlign.Left
            )
            Text(
                text = "Status: ${characterDescription.status}",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 14.sp,
                textAlign = TextAlign.Left
            )

        }
    }
}
