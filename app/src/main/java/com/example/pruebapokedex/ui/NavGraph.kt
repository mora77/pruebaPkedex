package com.example.pruebapokedex.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.pruebapokedex.theme.PokedexTheme
import com.example.pruebapokedex.ui.detail.DetailScreen
import com.example.pruebapokedex.ui.favorites.FavoritesScreen
import com.example.pruebapokedex.ui.list.PokemonListScreen
import com.example.pruebapokedex.ui.radar.RadarScreen
import dagger.hilt.android.AndroidEntryPoint


sealed class Dest(val route: String, @DrawableRes val icon: Int, val label: String) {
    data object List : Dest("list", android.R.drawable.ic_menu_search, "Pokédex")
    data object Radar : Dest("radar", android.R.drawable.ic_menu_mylocation, "Radar")
    data object Favorites : Dest("favorites", android.R.drawable.star_big_on, "Favoritos")
    data object Detail : Dest("detail/{id}", 0, "Detalle") {
        fun route(id: Int) = "detail/$id"
    }
}


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppRoot() }
    }
}


@Composable
fun AppRoot() {
    PokedexTheme {
        val nav = rememberNavController()
        Scaffold(
            bottomBar = {
                NavigationBar {
                    listOf(Dest.List, Dest.Radar, Dest.Favorites).forEach { d ->
                        val current by nav.currentBackStackEntryAsState()
                        val sel = current?.destination?.route == d.route
                        NavigationBarItem(
                            selected = sel,
                            onClick = {
                                nav.navigate(d.route) {
                                    launchSingleTop = true; popUpTo(Dest.List.route)
                                }
                            },
                            icon = {
                                Icon(
                                    painterResource(id = d.icon),
                                    contentDescription = d.label
                                )
                            },
                            label = { Text(d.label) }
                        )
                    }
                }
            }
        ) { padding ->
            NavHost(
                nav,
                startDestination = Dest.List.route,
                modifier = androidx.compose.ui.Modifier.padding(padding)
            ) {
                composable(Dest.List.route) { PokemonListScreen(nav) }
                composable(Dest.Radar.route) { RadarScreen(nav) }
                composable(Dest.Favorites.route) { FavoritesScreen(nav) }
                composable(
                    Dest.Detail.route,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) {
                    val id = it.arguments!!.getInt("id")
                    DetailScreen(id)
                }
            }
        }
    }
}