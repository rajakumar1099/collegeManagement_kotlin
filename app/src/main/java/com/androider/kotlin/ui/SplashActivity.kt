package com.androider.kotlin.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.androider.kotlin.R
import com.androider.kotlin.utils.Constants

class SplashActivity : AppCompatActivity() {

    val splashTimeOut:Long = 3000
    lateinit var pref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Hiding the navigation bar and status bar
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        setContentView(R.layout.activity_splash)
        loadData()
    }

    private fun loadData(){
        pref = getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE)
        if (pref.contains(Constants.Username)){
            Log.d("TAG", "loadData: DataFound")
            Constants.TempUsername = pref.getString("username",Constants.TempUsername).toString()
            Constants.TempPassword = pref.getString("password", Constants.TempPassword).toString()
            Constants.TempUserType = pref.getString("userType", Constants.TempUserType).toString()
            Constants.TempDeviceToken = pref.getString("deviceToken", Constants.TempDeviceToken).toString()
            Constants.TempUid = pref.getString("uid", Constants.TempUid).toString()
            Constants.TempFullName = pref.getString("fullName", Constants.TempFullName).toString()
            Constants.TempLastLogin = pref.getString("lastLogin", Constants.TempLastLogin).toString()
            Constants.TempBatch = pref.getString("Batch", Constants.TempBatch).toString()
            Constants.TempPhoneNumber = pref.getString("phoneNumber", Constants.TempPhoneNumber).toString()
            Constants.TempImageURL = pref.getString("imageURL", Constants.TempImageURL).toString()
            Constants.TempEmailID = pref.getString("emailID", Constants.TempEmailID).toString()
            Constants.TempPrimaryAddress = pref.getString("primaryAddress", Constants.TempPrimaryAddress).toString()
            Constants.TempDateOfBirth = pref.getString("dateOfBirth", Constants.TempDateOfBirth).toString()
            Constants.TempFatherName = pref.getString("fatherName", Constants.TempFatherName).toString()
            Constants.TempMotherName = pref.getString("motherName", Constants.TempMotherName).toString()
            Constants.TempFatherOccupation = pref.getString("fatherOccupation", Constants.TempFatherOccupation).toString()
            Constants.TempMotherOccupation = pref.getString("motherOccupation", Constants.TempMotherOccupation).toString()
            MoveToHomePage()
        }else{
            Log.d("TAG", "loadData: NoDataFound")
            moveToLoginPage()
        }
    }

    private fun moveToLoginPage() {
        Handler().postDelayed({
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        },splashTimeOut)
    }

    private fun MoveToHomePage(){

        Handler().postDelayed({
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        },splashTimeOut)
    }
}