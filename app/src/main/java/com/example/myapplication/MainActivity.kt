package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class   MainActivity : AppCompatActivity() {
//    private var tvMessage: TextView? = null
//    private var tvLocation: TextView? = null
    var binding : ActivityMainBinding? = null
    var fusedLocationProviderClient: FusedLocationProviderClient? = null

    @SuppressLint("MissingPermission")
    var locationPermissionRequest = registerForActivityResult(RequestMultiplePermissions(), ActivityResultCallback<Map<String?, Boolean?>> { result: Map<String?, Boolean?> ->
        val fineLocationGranted = result.getOrDefault(
                Manifest.permission.ACCESS_FINE_LOCATION, false)
        val coarseLocationGranted = result.getOrDefault(
                Manifest.permission.ACCESS_COARSE_LOCATION, false)
        binding?.apply{
            if (fineLocationGranted != null && fineLocationGranted) {
                // Precise location access granted.
                tvMessage.text = getString(R.string.main_location_permission)
                startListening()
            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                // Only approximate location access granted.
                tvMessage.setText(R.string.main_location_permission)
                startListening()
            } else {
                // No location access granted.
                tvMessage.setText(R.string.main_location_permission_denied)
            }
        }

    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding?.let {
        setContentView(it.root)
        }
//        tvMessage = findViewById(R.id.tv_message)
//        tvLocation = findViewById(R.id.tv_location)
        initElements()
        checkPermission()
    }
}