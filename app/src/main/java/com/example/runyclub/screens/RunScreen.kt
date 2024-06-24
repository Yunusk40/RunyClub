package com.example.runyclub.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.google.android.gms.location.LocationServices
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView

@Composable
fun RunScreen(navController: NavHostController, requestPermissionLauncher: ActivityResultLauncher<String>) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    var mapViewInitialized by remember { mutableStateOf(false) }

    DisposableEffect(mapView) {
        mapView.onCreate(Bundle())
        mapView.onResume()
        mapView.getMapAsync { googleMap ->
            googleMap.uiSettings.isZoomControlsEnabled = true
            googleMap.uiSettings.isCompassEnabled = true
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
                googleMap.isMyLocationEnabled = true
            }
        }
        onDispose {
            mapView.onPause()
            mapView.onDestroy()
        }
    }

    AndroidView({ mapView }, Modifier.fillMaxSize())
}
