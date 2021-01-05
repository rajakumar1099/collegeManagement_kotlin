package com.androider.kotlin.ui.driver

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.androider.kotlin.R
import com.androider.kotlin.utils.Constants
import com.androider.kotlin.utils.toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_driver_map.*
import kotlinx.android.synthetic.main.dialog_bus_no.*
import kotlinx.android.synthetic.main.dialog_bus_no.view.*
import kotlinx.android.synthetic.main.toolbar.*

class DriverMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var locationManager: LocationManager
    private var ACCESS_LOCATION_REQUEST_CODE: Int = 10001
    private lateinit var locationTask: Task<Location>
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.drivermap) as SupportMapFragment
        mapFragment.getMapAsync(this)
        toolbar()
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        driverMapOnlineSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Log.d("TAG", "driverMapOnlineSwitch: enabled")
                openDialogBox()
            } else {

            }
        }

    }

    private fun openDialogBox(){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_bus_no, null)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
        dialogView.busNumberFAB.setOnClickListener {
                if (dialogView.enterBusNumberTv.text.toString().isNotEmpty()) {
                    alertDialog.dismiss()
                    makeBusAvailable(dialogView.enterBusNumberTv.text.toString())
                    onMapReady(mMap)

                }else{
                    toast("Enter the Bus Number to continue")
                }
        }
    }

    private fun makeBusAvailable(busNumber: String){
        firebaseFirestore.collection("available-buses").get().addOnSuccessListener {
        }.addOnFailureListener {
            toast(it.toString())
        }
    }




    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                enableLocationUpdates()
            }else{
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_LOCATION_REQUEST_CODE)
                }else{
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_LOCATION_REQUEST_CODE)
                }
            }
        }else{
            showGPSDisabledAlertToUser()
        }

    }

    @SuppressLint("MissingPermission")
    private fun enableLocationUpdates(){
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = false

        locationTask = mFusedLocationClient!!.lastLocation
        locationTask.addOnSuccessListener(OnSuccessListener {
            if (locationTask.isSuccessful) {
                if (it != null){
                    val latLng = LatLng(it.latitude, it.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16F))
                }

            } else {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun toolbar(){
        toolbarText.text = "Welcome ${Constants.TempUsername}"
        toolbarText.visibility = View.VISIBLE
        toolbarBackBtn.visibility = View.INVISIBLE
    }

    private fun showGPSDisabledAlertToUser() {
        val alertDialogBuilder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
            .setCancelable(false)
            .setPositiveButton("Goto Settings Page To Enable GPS",
                DialogInterface.OnClickListener { dialog, id ->
                    val callGPSSettingIntent = Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(callGPSSettingIntent)
                })
        alertDialogBuilder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert: android.app.AlertDialog? = alertDialogBuilder.create()
        alert?.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                enableLocationUpdates()
            }else{
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_LOCATION_REQUEST_CODE)
                }else{
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_LOCATION_REQUEST_CODE)
                }
            }
        }else{
            showGPSDisabledAlertToUser()
        }
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_LOCATION_REQUEST_CODE){
            if ( grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                enableLocationUpdates()
            }else{
                Toast.makeText(this, "Turn On Location", Toast.LENGTH_SHORT).show()
            }
        }
    }

}