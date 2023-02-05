package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices

fun MainActivity.initElements() {
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    binding?.tvLocation?.setOnClickListener {
        showAlert(successButton = {
            locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
        }, negaClicked = {
            Toast.makeText(this, "No obtuviste el permiso",
                    Toast.LENGTH_LONG).show()
        })
    }
}

fun MainActivity.checkPermission() {
    if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        binding?.tvMessage?.setText("Tienes el permiso")
        startListening()
    }
    run {
        showAlert(successButton = {
            locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
        }, negaClicked = {
            Toast.makeText(this, "No obtuviste el permiso",
                    Toast.LENGTH_LONG).show()
        })
    }
}

@SuppressLint("MissingPermission")
fun MainActivity.startListening() {
    fusedLocationProviderClient?.lastLocation?.addOnSuccessListener(this) { location ->
        if (location != null) {
            val accuracy = location.accuracy.toString()
            val latitude = location.latitude.toString()
            val longitude = location.longitude.toString()
            Log.d("Localizacion", accuracy)
            Log.d("Localizacion", latitude)
            Log.d("Localizacion", longitude)
            binding?.tvLocation!!.text = "$accuracy $latitude, $longitude"
        }
    }
}

fun MainActivity.showAlert(tittle: String = "Soy una Alerta", okButton: String = "Aceptar", negativeButton: String = "Rechazar", successButton: () -> Unit, negaClicked: () -> Unit) {
    AlertDialog.Builder(this)
            .setTitle(tittle).setMessage("Aceptas el permiso?").setNegativeButton(negativeButton) { dialogInterface, i ->
                negaClicked()
            }.setPositiveButton(okButton) { dialogInterface, i ->
                successButton()
            }.create().show()
}