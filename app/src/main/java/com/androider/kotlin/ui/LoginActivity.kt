package com.androider.kotlin.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androider.kotlin.R
import com.androider.kotlin.utils.Constants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_login.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class LoginActivity : AppCompatActivity() {
    private val TAG = "MyActivity"
    private lateinit var pref : SharedPreferences
    private lateinit var firebaseDatabase: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
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

    }

    fun LoginBtn(view: View) {
        progressDialog.show()
        if (emailEV.text.toString().isEmpty()){
            emailEV.error = "Enter the Email"
            progressDialog.dismiss()
        }
        else if(passwordEv.text.toString().isEmpty()){
            passwordEv.error = "Enter the Password"
            progressDialog.dismiss()
        }else if(passwordEv.text.toString().length<6){
            passwordEv.error = "Password should be more than 6 character"
            progressDialog.dismiss()
        }else{
            hideKeyboard()
            Log.d(TAG, "Email: " + emailEV.text.toString())
            Log.d(TAG, "Password: " + passwordEv.text.toString())
            var email: String = emailEV.text.toString()
            var password: String = passwordEv.text.toString()
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
//                Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
                    updateDate()
                }.addOnFailureListener { p0 ->
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, p0.toString(), Toast.LENGTH_SHORT).show()
            }
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
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Alert")
        progressDialog.setMessage("Please Wait")
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
            Toast.makeText(this,"Updated Successfully",Toast.LENGTH_SHORT).show()
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
        }
        progressDialog.dismiss()
    }

    fun ForgetPasswordBtn(view: View) {
        startActivity(Intent(this, ForgetPassword::class.java))
    }

    private fun sharedPref(){
        pref = getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = pref.edit()
        editor.putString("username", Constants.TempUsername)
        editor.putString("password", Constants.TempPassword)
        editor.putString("gender", Constants.TempGender)
        editor.putString("userType", Constants.TempUserType)
        editor.putString("deviceToken", Constants.TempDeviceToken)
        editor.putString("uid", Constants.TempUid)
        editor.putString("fullName", Constants.TempFullName)
        editor.putString("lastLogin", Constants.TempLastLogin)
        editor.putString("Batch", Constants.TempBatch)
        editor.putString("phoneNumber", Constants.TempPhoneNumber)
        editor.putString("imageURL", Constants.TempImageURL)
        editor.putString("emailID", Constants.TempEmailID)
        editor.putString("primaryAddress", Constants.TempPrimaryAddress)
        editor.putString("dateOfBirth", Constants.TempDateOfBirth)
        editor.putString("fatherName", Constants.TempFatherName)
        editor.putString("motherName", Constants.TempMotherName)
        editor.putString("fatherOccupation", Constants.TempFatherOccupation)
        editor.putString("motherOccupation", Constants.TempMotherOccupation)
        editor.apply()

        Log.d(TAG, "sharedPref: Saved Successfully $editor")

        startActivity(Intent(this,HomeActivity::class.java))
        finish()

    }

}

