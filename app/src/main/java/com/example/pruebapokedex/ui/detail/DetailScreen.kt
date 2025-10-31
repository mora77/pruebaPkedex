package com.example.pruebapokedex.ui.detail


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pruebapokedex.R
import com.example.pruebapokedex.ui.components.AvatarSprite


@Composable
fun DetailScreen(id: Int, vm: DetailViewModel = hiltViewModel()) {
    val p = vm.pokemon.collectAsState().value
    if (p == null) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
        return
    }
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AvatarSprite(p.name, p.imageUrl, p.spriteUrl, Modifier.size(96.dp))
            Spacer(Modifier.width(12.dp))
            Column {
                Text(p.name, style = MaterialTheme.typography.headlineMedium)
                Text("${stringResource(R.string.types_string)}: ${p.types.joinToString(" / ")}")
            }
        }
        Text("${stringResource(R.string.height_string)}: ${p.height}")
        Text("${stringResource(R.string.weight_string)}: ${p.weight}")
        Button(onClick = vm::toggleFavorite) {
            Text(
                if (p.isFavorite) stringResource(R.string.delete_favorites_string) else stringResource(
                    R.string.add_favorites_string
                )
            )
        }
    }
}