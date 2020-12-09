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
import com.androider.kotlin.ui.driver.DriverMapActivity
import com.androider.kotlin.utils.Constants

class SplashActivity : AppCompatActivity() {

    private val splashTimeOut:Long = 3000
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
        when {
            pref.getString("usertype", Constants.TempUserType).toString() == "admin" -> {
                Log.d("TAG", "loadData: Admin")
                Constants.TempUsername = pref.getString(Constants.Username,Constants.TempUsername).toString()
                Constants.TempPassword = pref.getString(Constants.Password, Constants.TempPassword).toString()
                Constants.TempGender = pref.getString(Constants.Gender,Constants.TempGender).toString()
                Constants.TempUserType = pref.getString(Constants.UserType, Constants.TempUserType).toString()
                Constants.TempDeviceToken = pref.getString(Constants.DeviceToken, Constants.TempDeviceToken).toString()
                Constants.TempUid = pref.getString(Constants.Uid, Constants.TempUid).toString()
                Constants.TempFullName = pref.getString(Constants.FullName, Constants.TempFullName).toString()
                Constants.TempLastLogin = pref.getString(Constants.LastLogin, Constants.TempLastLogin).toString()
                Constants.TempBatch = pref.getString(Constants.Batch, Constants.TempBatch).toString()
                Constants.TempPhoneNumber = pref.getString(Constants.PhoneNumber, Constants.TempPhoneNumber).toString()
                Constants.TempImageURL = pref.getString(Constants.ImageURL, Constants.TempImageURL).toString()
                Constants.TempEmailID = pref.getString(Constants.EmailID, Constants.TempEmailID).toString()
                Constants.TempPrimaryAddress = pref.getString(Constants.PrimaryAddress, Constants.TempPrimaryAddress).toString()
                Constants.TempDateOfBirth = pref.getString(Constants.DateOfBirth, Constants.TempDateOfBirth).toString()
                Constants.TempFatherName = pref.getString(Constants.FatherName, Constants.TempFatherName).toString()
                Constants.TempMotherName = pref.getString(Constants.MotherName, Constants.TempMotherName).toString()
                Constants.TempFatherOccupation = pref.getString(Constants.FatherOccupation, Constants.TempFatherOccupation).toString()
                Constants.TempMotherOccupation = pref.getString(Constants.MotherOccupation, Constants.TempMotherOccupation).toString()
                moveToHomePage()
            }
            pref.getString("usertype", Constants.TempUserType).toString() == "student" -> {
                Log.d("TAG", "loadData: Student")
                Constants.TempUsername = pref.getString(Constants.Username,Constants.TempUsername).toString()
                Constants.TempPassword = pref.getString(Constants.Password, Constants.TempPassword).toString()
                Constants.TempGender = pref.getString(Constants.Gender,Constants.TempGender).toString()
                Constants.TempUserType = pref.getString(Constants.UserType, Constants.TempUserType).toString()
                Constants.TempDeviceToken = pref.getString(Constants.DeviceToken, Constants.TempDeviceToken).toString()
                Constants.TempRegisterNumber = pref.getString(Constants.RegisterNumber,Constants.TempRegisterNumber).toString()
                Constants.TempRollNumber = pref.getString(Constants.RollNumber,Constants.TempRollNumber).toString()
                Constants.TempDepartment = pref.getString(Constants.Department,Constants.TempDepartment).toString()
                Constants.TempClass = pref.getString(Constants.Class,Constants.TempClass).toString()
                Constants.TempUid = pref.getString(Constants.Uid, Constants.TempUid).toString()
                Constants.TempFullName = pref.getString(Constants.FullName, Constants.TempFullName).toString()
                Constants.TempLastLogin = pref.getString(Constants.LastLogin, Constants.TempLastLogin).toString()
                Constants.TempBatch = pref.getString(Constants.Batch, Constants.TempBatch).toString()
                Constants.TempPhoneNumber = pref.getString(Constants.PhoneNumber, Constants.TempPhoneNumber).toString()
                Constants.TempImageURL = pref.getString(Constants.ImageURL, Constants.TempImageURL).toString()
                Constants.TempEmailID = pref.getString(Constants.EmailID, Constants.TempEmailID).toString()
                Constants.TempPrimaryAddress = pref.getString(Constants.PrimaryAddress, Constants.TempPrimaryAddress).toString()
                Constants.TempDateOfBirth = pref.getString(Constants.DateOfBirth, Constants.TempDateOfBirth).toString()
                Constants.TempFatherName = pref.getString(Constants.FatherName, Constants.TempFatherName).toString()
                Constants.TempMotherName = pref.getString(Constants.MotherName, Constants.TempMotherName).toString()
                Constants.TempFatherOccupation = pref.getString(Constants.FatherOccupation, Constants.TempFatherOccupation).toString()
                Constants.TempMotherOccupation = pref.getString(Constants.MotherOccupation, Constants.TempMotherOccupation).toString()
                moveToHomePage()
            }
            pref.getString("usertype", Constants.TempUserType).toString() == "teacher" -> {
                Log.d("TAG", "loadData: Teacher")
                Constants.TempUsername = pref.getString(Constants.Username,Constants.TempUsername).toString()
                Constants.TempPassword = pref.getString(Constants.Password, Constants.TempPassword).toString()
                Constants.TempGender = pref.getString(Constants.Gender,Constants.TempGender).toString()
                Constants.TempUserType = pref.getString(Constants.UserType, Constants.TempUserType).toString()
                Constants.TempDeviceToken = pref.getString(Constants.DeviceToken, Constants.TempDeviceToken).toString()
                Constants.TempRollNumber = pref.getString(Constants.RollNumber,Constants.TempRollNumber).toString()
                Constants.TempUid = pref.getString(Constants.Uid, Constants.TempUid).toString()
                Constants.TempFullName = pref.getString(Constants.FullName, Constants.TempFullName).toString()
                Constants.TempLastLogin = pref.getString(Constants.LastLogin, Constants.TempLastLogin).toString()
                Constants.TempBatch = pref.getString(Constants.Batch, Constants.TempBatch).toString()
                Constants.TempPhoneNumber = pref.getString(Constants.PhoneNumber, Constants.TempPhoneNumber).toString()
                Constants.TempImageURL = pref.getString(Constants.ImageURL, Constants.TempImageURL).toString()
                Constants.TempEmailID = pref.getString(Constants.EmailID, Constants.TempEmailID).toString()
                Constants.TempPrimaryAddress = pref.getString(Constants.PrimaryAddress, Constants.TempPrimaryAddress).toString()
                Constants.TempDateOfBirth = pref.getString(Constants.DateOfBirth, Constants.TempDateOfBirth).toString()
                moveToHomePage()
            }
            pref.getString("usertype", Constants.TempUserType).toString() == "driver" ->{
                Constants.TempUsername = pref.getString(Constants.Username,Constants.TempUsername).toString()
                Constants.TempPassword = pref.getString(Constants.Password, Constants.TempPassword).toString()
                Constants.TempUserType = pref.getString(Constants.UserType, Constants.TempUserType).toString()
                Constants.TempDeviceToken = pref.getString(Constants.DeviceToken, Constants.TempDeviceToken).toString()
                Constants.TempUid = pref.getString(Constants.Uid, Constants.TempUid).toString()
                Constants.TempPhoneNumber = pref.getString(Constants.PhoneNumber, Constants.TempPhoneNumber).toString()
                Constants.TempImageURL = pref.getString(Constants.ImageURL, Constants.TempImageURL).toString()
                Constants.TempEmailID = pref.getString(Constants.EmailID, Constants.TempEmailID).toString()
                startActivity(Intent(applicationContext,DriverMapActivity::class.java))
                finish()
            }
                else -> {
                Log.d("TAG", "loadData: NoDataFound")
                moveToLoginPage()
            }
        }
    }

    private fun moveToLoginPage() {
        Handler().postDelayed({
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        },splashTimeOut)
    }

    private fun moveToHomePage(){

        Handler().postDelayed({
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        },splashTimeOut)
    }
}