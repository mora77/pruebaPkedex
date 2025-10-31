package com.example.pruebapokedex.ui.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pruebapokedex.ui.Dest
import com.example.pruebapokedex.ui.components.AvatarSprite


@Composable
fun FavoritesScreen(nav: NavHostController, vm: FavoritesViewModel = hiltViewModel()) {
    val items = vm.paging.collectAsLazyPagingItems()
    LazyColumn(Modifier.fillMaxSize()) {
        items(items.itemCount) { index ->
            val p = items[index]
            if (p == null) return@items
            ListItem(
                headlineContent = { Text(p.name) },
                leadingContent = { AvatarSprite(p.name, p.imageUrl, p.spriteUrl) },
                modifier = Modifier.clickable { nav.navigate(Dest.Detail.route(p.id)) }
            )
            HorizontalDivider(Modifier)
        }
    }
}