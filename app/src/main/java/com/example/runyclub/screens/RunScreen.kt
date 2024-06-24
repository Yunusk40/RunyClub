package com.example.runyclub.screens

import android.Manifest
import android.content.pm.PackageManager
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
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var currentLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    } else {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = LatLng(location.latitude, location.longitude)
            }
        }
    }

    val cameraPosition = CameraPosition.fromLatLngZoom(currentLocation, 15f)

    AndroidView({ context ->
        GoogleMapOptions().apply {
            mapType(1)
            zoomControlsEnabled(true)
            compassEnabled(true)
        }.let { options ->
            MapView(context, options).apply {
                getMapAsync { googleMap ->
                    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                        googleMap.isMyLocationEnabled = true
                    }
                }
            }
        }
    }, modifier = Modifier.fillMaxSize())
}