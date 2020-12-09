package com.androider.kotlin.ui.driver

import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.androider.kotlin.R

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.database.FirebaseDatabase

class DriverMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var locationManager: LocationManager
    private var ACCESS_LOCATION_REQUEST_CODE: Int = 10001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.drivermap) as SupportMapFragment
        mapFragment.getMapAsync(this)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        firebaseDatabase = FirebaseDatabase.getInstance()

    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            }else{
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_LOCATION_REQUEST_CODE)
                }else{
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_LOCATION_REQUEST_CODE)
                }
            }
        }

    }
}