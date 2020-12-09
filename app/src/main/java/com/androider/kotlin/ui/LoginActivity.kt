package com.androider.kotlin.ui

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androider.kotlin.R
import com.androider.kotlin.ui.driver.DriverMapActivity
import com.androider.kotlin.utils.Constants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_login.*
import java.sql.Driver
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private val TAG = "MyActivity"
    private var loginShow : Boolean = false
    private lateinit var pref : SharedPreferences
    private lateinit var firebaseDatabase: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: AlertDialog
    private lateinit var firebaseNewToken: String
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var sdf: SimpleDateFormat
    private lateinit var currentTime: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseDatabase = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        lastLoginTime()
        alertDialog()
        generateFirebaseToken()


        //show,hide password function
        showPasswordBtnLay.setOnClickListener(){
            showHidePassword(passwordEv,showPasswordBtn)
        }

    }

    fun loginBtn(view: View) {
        progressDialog.show()
        when {
            emailEV.text.toString().isEmpty() -> {
                emailEV.error = "Enter the Email"
                progressDialog.dismiss()
            }
            passwordEv.text.toString().isEmpty() -> {
                passwordEv.error = "Enter the Password"
                progressDialog.dismiss()
            }
            passwordEv.text.toString().length<6 -> {
                passwordEv.error = "Password should be more than 6 character"
                progressDialog.dismiss()
            }
            else -> {
                hideKeyboard()
                Log.d(TAG, "Email: " + emailEV.text.toString())
                Log.d(TAG, "Password: " + passwordEv.text.trim().toString())
                var email: String = emailEV.text.toString()
                var password: String = passwordEv.text.toString()
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                    Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
                        updateDate()
                    }.addOnFailureListener { p0 ->
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, p0.toString(), Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }



    private fun updateDate(){
        var uid: String = firebaseAuth.uid.toString()
        val firebaseUpdateDate = firebaseFirestore.collection("users").document(uid)
        firebaseUpdateDate.get().addOnSuccessListener {
        documentSnapshot -> if (documentSnapshot != null){
            val map: HashMap<String,String> = HashMap<String,String>()
            map[Constants.LastLogin] = currentTime
            map[Constants.DeviceToken] = firebaseNewToken
            firebaseUpdateDate.update(Constants.LastLogin,currentTime,
                                      Constants.DeviceToken,firebaseNewToken)
            Log.d(TAG, "updateData: $map")
            Log.d(TAG,"Updated Successfully")
            setUserData(uid)

        }else{
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
        }
        }.addOnFailureListener { it ->
            Toast.makeText(this, "Failed to update data $it", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUserData(uid: String){
        val firebaseFirestore = firebaseFirestore.collection("users").document(uid)
        firebaseFirestore.get().addOnSuccessListener { task ->
            if(task.exists()){
                when {
                    task.get(Constants.UserType).toString() == "admin" -> {
                        Log.d(TAG, "setUserData: ${task.get(Constants.UserType).toString()}")
                        Constants.TempUsername = task.get(Constants.Username).toString()
                        Constants.TempPassword = task.get(Constants.Password).toString()
                        Constants.TempGender = task.get(Constants.Gender).toString()
                        Constants.TempUserType = task.get(Constants.UserType).toString()
                        Constants.TempDeviceToken = task.get(Constants.DeviceToken).toString()
                        Constants.TempUid = task.get(Constants.Uid).toString()
                        Constants.TempFullName = task.get(Constants.FullName).toString()
                        Constants.TempLastLogin = task.get(Constants.LastLogin).toString()
                        Constants.TempBatch = task.get(Constants.Batch).toString()
                        Constants.TempPhoneNumber = task.get(Constants.PhoneNumber).toString()
                        Constants.TempImageURL = task.get(Constants.ImageURL).toString()
                        Constants.TempEmailID = task.get(Constants.EmailID).toString()
                        Constants.TempPrimaryAddress = task.get(Constants.PrimaryAddress).toString()
                        Constants.TempExperience = task.get(Constants.Experience).toString()
                        Constants.TempDateOfBirth = task.get(Constants.DateOfBirth).toString()
                        Constants.TempFatherName = task.get(Constants.FatherName).toString()
                        Constants.TempMotherName = task.get(Constants.FatherName).toString()
                        Constants.TempFatherOccupation = task.get(Constants.FatherOccupation).toString()
                        Constants.TempMotherOccupation = task.get(Constants.MotherOccupation).toString()
                        sharedPref()

                    }
                    task.get(Constants.UserType).toString() == "student" -> {
                        Log.d(TAG, "setUserData: ${task.get(Constants.UserType).toString()}")
                        Constants.TempUsername = task.get(Constants.Username).toString()
                        Constants.TempPassword = task.get(Constants.Password).toString()
                        Constants.TempGender = task.get(Constants.Gender).toString()
                        Constants.TempUserType = task.get(Constants.UserType).toString()
                        Constants.TempDepartment = task.get(Constants.Department).toString()
                        Constants.TempClass = task.get(Constants.Class).toString()
                        Constants.TempRollNumber = task.get(Constants.RollNumber).toString()
                        Constants.TempRegisterNumber = task.get(Constants.RegisterNumber).toString()
                        Constants.TempDeviceToken = task.get(Constants.DeviceToken).toString()
                        Constants.TempUid = task.get(Constants.Uid).toString()
                        Constants.TempFullName = task.get(Constants.FullName).toString()
                        Constants.TempLastLogin = task.get(Constants.LastLogin).toString()
                        Constants.TempBatch = task.get(Constants.Batch).toString()
                        Constants.TempPhoneNumber = task.get(Constants.PhoneNumber).toString()
                        Constants.TempImageURL = task.get(Constants.ImageURL).toString()
                        Constants.TempEmailID = task.get(Constants.EmailID).toString()
                        Constants.TempPrimaryAddress = task.get(Constants.PrimaryAddress).toString()
                        Constants.TempExperience = task.get(Constants.Experience).toString()
                        Constants.TempDateOfBirth = task.get(Constants.DateOfBirth).toString()
                        Constants.TempFatherName = task.get(Constants.FatherName).toString()
                        Constants.TempMotherName = task.get(Constants.FatherName).toString()
                        Constants.TempFatherOccupation = task.get(Constants.FatherOccupation).toString()
                        Constants.TempMotherOccupation = task.get(Constants.MotherOccupation).toString()
                        sharedPref()
                    }
                    task.get(Constants.UserType).toString() == "teacher" -> {
                        Log.d(TAG, "setUserData: ${task.get(Constants.UserType).toString()}")
                        Constants.TempUsername = task.get(Constants.Username).toString()
                        Constants.TempPassword = task.get(Constants.Password).toString()
                        Constants.TempGender = task.get(Constants.Gender).toString()
                        Constants.TempUserType = task.get(Constants.UserType).toString()
                        Constants.TempExperience = task.get(Constants.Experience).toString()
                        Constants.TempRollNumber = task.get(Constants.RollNumber).toString()
                        Constants.TempDeviceToken = task.get(Constants.DeviceToken).toString()
                        Constants.TempUid = task.get(Constants.Uid).toString()
                        Constants.TempFullName = task.get(Constants.FullName).toString()
                        Constants.TempLastLogin = task.get(Constants.LastLogin).toString()
                        Constants.TempPhoneNumber = task.get(Constants.PhoneNumber).toString()
                        Constants.TempImageURL = task.get(Constants.ImageURL).toString()
                        Constants.TempEmailID = task.get(Constants.EmailID).toString()
                        Constants.TempPrimaryAddress = task.get(Constants.PrimaryAddress).toString()
                        Constants.TempDateOfBirth = task.get(Constants.DateOfBirth).toString()
                        sharedPref()
                    }
                    else -> {
                        Log.d(TAG, "setUserData: ${task.get(Constants.UserType).toString()}")
                        Constants.TempUsername = task.get(Constants.Username).toString()
                        Constants.TempPassword = task.get(Constants.Password).toString()
                        Constants.TempUserType = task.get(Constants.UserType).toString()
                        Constants.TempUid = task.get(Constants.Uid).toString()
                        Constants.TempLastLogin = task.get(Constants.LastLogin).toString()
                        Constants.TempPhoneNumber = task.get(Constants.PhoneNumber).toString()
                        Constants.TempImageURL = task.get(Constants.ImageURL).toString()
                        Constants.TempEmailID = task.get(Constants.EmailID).toString()
                        sharedPref()

                    }
                }
            }
        }
        progressDialog.dismiss()
    }

    private fun sharedPref(){
        pref = getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = pref.edit()
        when (Constants.TempUserType) {
            "admin" -> {
                Log.d(TAG, "sharedPref: ${Constants.TempUserType}")
                editor.putString(Constants.Username, Constants.TempUsername)
                editor.putString(Constants.Password, Constants.TempPassword)
                editor.putString(Constants.Gender, Constants.TempGender)
                editor.putString(Constants.UserType, Constants.TempUserType)
                editor.putString(Constants.DeviceToken, Constants.TempDeviceToken)
                editor.putString(Constants.Uid, Constants.TempUid)
                editor.putString(Constants.FullName, Constants.TempFullName)
                editor.putString(Constants.LastLogin, Constants.TempLastLogin)
                editor.putString(Constants.Batch, Constants.TempBatch)
                editor.putString(Constants.PhoneNumber, Constants.TempPhoneNumber)
                editor.putString(Constants.ImageURL, Constants.TempImageURL)
                editor.putString(Constants.EmailID, Constants.TempEmailID)
                editor.putString(Constants.PrimaryAddress, Constants.TempPrimaryAddress)
                editor.putString(Constants.DateOfBirth, Constants.TempDateOfBirth)
                editor.putString(Constants.FatherName, Constants.TempFatherName)
                editor.putString(Constants.MotherName, Constants.TempMotherName)
                editor.putString(Constants.FatherOccupation, Constants.TempFatherOccupation)
                editor.putString(Constants.MotherName, Constants.TempMotherOccupation)
                editor.apply()
                startActivity(Intent(this,HomeActivity::class.java))
                finish()
            }
            "student" -> {
                Log.d(TAG, "sharedPref: ${Constants.TempUserType}")
                editor.putString(Constants.Username, Constants.TempUsername)
                editor.putString(Constants.Password, Constants.TempPassword)
                editor.putString(Constants.Gender, Constants.TempGender)
                editor.putString(Constants.UserType, Constants.TempUserType)
                editor.putString(Constants.DeviceToken, Constants.TempDeviceToken)
                editor.putString(Constants.Uid, Constants.TempUid)
                editor.putString(Constants.Department,Constants.TempDepartment)
                editor.putString(Constants.Class,Constants.TempClass)
                editor.putString(Constants.RegisterNumber,Constants.TempRegisterNumber)
                editor.putString(Constants.RollNumber,Constants.TempRollNumber)
                editor.putString(Constants.FullName, Constants.TempFullName)
                editor.putString(Constants.LastLogin, Constants.TempLastLogin)
                editor.putString(Constants.Batch, Constants.TempBatch)
                editor.putString(Constants.PhoneNumber, Constants.TempPhoneNumber)
                editor.putString(Constants.ImageURL, Constants.TempImageURL)
                editor.putString(Constants.EmailID, Constants.TempEmailID)
                editor.putString(Constants.PrimaryAddress, Constants.TempPrimaryAddress)
                editor.putString(Constants.DateOfBirth, Constants.TempDateOfBirth)
                editor.putString(Constants.FatherName, Constants.TempFatherName)
                editor.putString(Constants.MotherName, Constants.TempMotherName)
                editor.putString(Constants.FatherOccupation, Constants.TempFatherOccupation)
                editor.putString(Constants.MotherOccupation, Constants.TempMotherOccupation)
                editor.apply()
                startActivity(Intent(this,HomeActivity::class.java))
                finish()
            }"teacher" -> {
            Log.d(TAG, "sharedPref: ${Constants.TempUserType}")
            editor.putString(Constants.Username, Constants.TempUsername)
            editor.putString(Constants.Password, Constants.TempPassword)
            editor.putString(Constants.Gender, Constants.TempGender)
            editor.putString(Constants.UserType, Constants.TempUserType)
            editor.putString(Constants.DeviceToken, Constants.TempDeviceToken)
            editor.putString(Constants.Uid, Constants.TempUid)
            editor.putString(Constants.RollNumber,Constants.TempRollNumber)
            editor.putString(Constants.FullName, Constants.TempFullName)
            editor.putString(Constants.LastLogin, Constants.TempLastLogin)
            editor.putString(Constants.Batch, Constants.TempBatch)
            editor.putString(Constants.PhoneNumber, Constants.TempPhoneNumber)
            editor.putString(Constants.ImageURL, Constants.TempImageURL)
            editor.putString(Constants.EmailID, Constants.TempEmailID)
            editor.putString(Constants.PrimaryAddress, Constants.TempPrimaryAddress)
            editor.putString(Constants.DateOfBirth, Constants.TempDateOfBirth)
            editor.putString(Constants.Experience,Constants.TempExperience)
            editor.apply()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }"driver"->{
            Log.d(TAG, "sharedPref: ${Constants.TempUserType}")
            editor.putString(Constants.Username, Constants.TempUsername)
            editor.putString(Constants.Password, Constants.TempPassword)
            editor.putString(Constants.UserType, Constants.TempUserType)
            editor.putString(Constants.DeviceToken, Constants.TempDeviceToken)
            editor.putString(Constants.Uid, Constants.TempUid)
            editor.putString(Constants.PhoneNumber, Constants.TempPhoneNumber)
            editor.putString(Constants.ImageURL, Constants.TempImageURL)
            editor.putString(Constants.EmailID, Constants.TempEmailID)
            editor.apply()
            startActivity(Intent(this,DriverMapActivity::class.java))
            finish()
        }
            else -> {
                Log.d(TAG, "sharedPref: $Constants.TempUserType")
            }
        }

        Log.d(TAG, "sharedPref: Saved Successfully $editor")


    }


    private fun forgetPasswordBtn(view: View) {
        startActivity(Intent(this, ForgetPassword::class.java))
    }




    private fun showHidePassword(passwordEv: EditText, showPasswordBtn: ImageView){
        if (!loginShow) {
            loginShow = true
            passwordEv.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            Log.d(TAG, "showHidePassword: Show ${passwordEv.text}")
            showPasswordBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_showpassword))
        } else {
            showPasswordBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_hidepassword))
            loginShow = false
            passwordEv.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            Log.d(TAG, "showHidePassword: Hide ${passwordEv.text}")

        }
    }

    private fun hideKeyboard(){
        try {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            // TODO: handle exception
        }
    }
    private fun alertDialog(){
        progressDialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait")
            .setCancelable(false)
            .build()
    }

    private fun generateFirebaseToken(){
        var firebaseToken = FirebaseMessaging.getInstance().token
        firebaseToken.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "generateFirebaseToken: ${task.toString()}")
                firebaseNewToken = task.result.toString()
            } else {
                Log.d(TAG, "generateFirebaseToken: ${task.toString()}"+"Failed to generate token")
            }
        })
    }

    private fun lastLoginTime(){
        sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentTime = sdf.format(Date())
    }



}

