package com.example.pruebapokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage


@Composable
fun AvatarSprite(
    name: String,
    imageUrl: String?,
    spriteUrl: String?,
    modifier: Modifier = Modifier
) {
    val url = imageUrl ?: spriteUrl
    if (url != null) {
        AsyncImage(model = url, contentDescription = name, modifier = modifier.clip(CircleShape))
    } else {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text((name.firstOrNull()?.uppercase() ?: "?"))
        }
    }
}