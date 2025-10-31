package com.example.pruebapokedex.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pruebapokedex.R
import com.example.pruebapokedex.ui.Dest
import com.example.pruebapokedex.ui.components.AvatarSprite
import kotlinx.coroutines.launch

@Composable
fun PokemonListScreen(nav: NavHostController, vm: ListViewModel = hiltViewModel()) {
    val items = vm.paging.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()


    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items.itemCount) { index ->
            val p = items[index]
            if (p == null) return@items
            ListItem(
                headlineContent = { Text(p.name) },
                leadingContent = { AvatarSprite(p.name, p.imageUrl, p.spriteUrl) },
                trailingContent = {
                    IconButton(onClick = { scope.launch { vm.toggleFavorite(p.id) } }) {
                        Icon(
                            if (p.isFavorite) Icons.Filled.Star else Icons.Outlined.FavoriteBorder,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.clickable { nav.navigate(Dest.Detail.route(p.id)) }
            )
            HorizontalDivider(Modifier)
        }


        when {
            items.loadState.refresh is LoadState.Loading -> item { CenteredProgress(stringResource(R.string.loading_string)) }
            items.loadState.append is LoadState.Loading -> item { CenteredProgress(stringResource(R.string.loading_more_string)) }
            items.loadState.refresh is LoadState.Error -> item { ErrorRow(stringResource(R.string.error_reload_string)) }
            items.loadState.append is LoadState.Error -> item { ErrorRow(stringResource(R.string.error_reload_more_string)) }
        }
    }
}


@Composable
private fun CenteredProgress(msg: String) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) { Row { CircularProgressIndicator(); Spacer(Modifier.width(12.dp)); Text(msg) } }
}

@Composable
private fun ErrorRow(msg: String) {
    Text(msg, modifier = Modifier.padding(16.dp))
}