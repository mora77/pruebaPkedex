package com.example.pruebapokedex.ui.radar

import android.Manifest
import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pruebapokedex.R
import com.example.pruebapokedex.ui.Dest
import com.example.pruebapokedex.ui.components.AvatarSprite
import com.example.pruebapokedex.ui.util.vibrateOnce
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RadarScreen(nav: NavHostController, vm: RadarViewModel = hiltViewModel()) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()

    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    var showAlert by remember { mutableStateOf(false) }
    val fused = remember { LocationServices.getFusedLocationProviderClient(context) }
    var last by remember { mutableStateOf<android.location.Location?>(null) }

    LaunchedEffect(Unit) {
        if (!locationPermission.status.isGranted) {
            locationPermission.launchPermissionRequest()
        }
    }

    DisposableEffect(locationPermission.status.isGranted) {
        if (!locationPermission.status.isGranted) {
            onDispose { }
        } else {
            val request = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                10_000L
            ).setMinUpdateDistanceMeters(10f).build()

            val cb = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    val latest = result.lastLocation ?: return
                    val prev = last
                    last = latest
                    if (prev != null && latest.distanceTo(prev) >= 10f) {
                        showAlert = true
                        scope.launch {
                            vm.getRandom()
                            vibrateOnce(context)
                        }
                    }
                }
            }

            @SuppressLint("MissingPermission")
            fun startLocationUpdates() {
                fused.requestLocationUpdates(request, cb, Looper.getMainLooper())
            }
            startLocationUpdates()

            onDispose {
                fused.removeLocationUpdates(cb)
            }
        }
    }

    RadarContent(
        showAlert = showAlert,
        onDismiss = { showAlert = false },
        vm = vm,
        nav = nav,
        onRandomClick = {
            scope.launch {
                vm.getRandom()
                vibrateOnce(context)
                showAlert = true
            }
        }
    )
}

@Composable
private fun RadarContent(
    showAlert: Boolean,
    onDismiss: () -> Unit,
    vm: RadarViewModel,
    nav: NavHostController,
    onRandomClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            stringResource(R.string.pokemon_radar_string),
            style = MaterialTheme.typography.headlineMedium
        )
        Button(onClick = onRandomClick) { Text(stringResource(R.string.pokemon_random_string)) }

        vm.found?.let { p ->
            Card(Modifier.fillMaxWidth()) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    AvatarSprite(p.name, p.imageUrl, p.spriteUrl, Modifier.size(72.dp))
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(p.name, style = MaterialTheme.typography.titleLarge)
                        Text("${stringResource(R.string.types_string)}: ${p.types.joinToString(" / ")}")
                        TextButton(onClick = { nav.navigate(Dest.Detail.route(p.id)) }) {
                            Text(stringResource(R.string.detail_string))
                        }
                    }
                }
            }
        }
    }

    if (showAlert) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(stringResource(R.string.pokemon_found_string)) },
            text = { Text(vm.found?.name ?: stringResource(R.string.searching_string)) },
            confirmButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.ok_string)) } }
        )
    }
}