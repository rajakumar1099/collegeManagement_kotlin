package com.androider.kotlin.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.androider.kotlin.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_track_bus_map.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class TrackBusMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mLastLocation: Location
    internal lateinit var mLocationResult: LocationRequest
    private lateinit var mLocationCallback: LocationCallback
    private var mCurrLocationMarker: Marker? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private lateinit var mLocationRequest: LocationRequest
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var ACCESS_LOCATION_REQUEST_CODE: Int = 10001
    lateinit var locationTask: Task<Location>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_bus_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setToolbar()
        floatingCurrentLocationBtn.setOnClickListener {
            zoomToUserLocation()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            enableUserLocation()
            zoomToUserLocation()
        }else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),ACCESS_LOCATION_REQUEST_CODE)
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),ACCESS_LOCATION_REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_LOCATION_REQUEST_CODE){
            if ( grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                enableUserLocation()
                zoomToUserLocation()
            }else{
                Toast.makeText(this,"Turn On Location",Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableUserLocation(){
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = false
    }

    @SuppressLint("MissingPermission")
    private fun zoomToUserLocation(){
        locationTask = mFusedLocationClient!!.lastLocation
        locationTask.addOnSuccessListener (OnSuccessListener {
            if(locationTask.isSuccessful){
                var latLng = LatLng(it.latitude,it.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16F))
            }else{
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()            }
        })

    }




    private fun setToolbar(){
        toolbarBackBtn.setOnClickListener(){
            onBackPressed()
        }
        toolbarBackBtn.visibility = View.VISIBLE
        toolbarText.text = "Track Your Bus"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}