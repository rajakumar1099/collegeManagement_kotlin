package com.androider.kotlin.ui

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.androider.kotlin.R
import com.androider.kotlin.utils.Constants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.emailEV
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class SignUp : AppCompatActivity() {
    private val TAG = "MyActivity"
    private lateinit var currentTime: String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var firebaseNewToken: String
    private lateinit var progressDialog: ProgressDialog
    private lateinit var sdf: SimpleDateFormat
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentTime = sdf.format(Date())
        alertDialog()
        generateFirebaseToken()
    }

    private fun alertDialog(){
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Alert")
        progressDialog.setMessage("Please Wait")
    }
    fun signUpBtn(view: View) {
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(emailsignupEV.text.toString(),passwordsignupEv.text.toString()).addOnSuccessListener {
            Toast.makeText(this,"Account created successful",Toast.LENGTH_LONG).show()
            saveData()


        }.addOnFailureListener { it ->
            it.toString()
            progressDialog.dismiss()
            Log.d(TAG,it.toString())
            Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()

        }
    }
    private fun saveData(){
        var firebaseAuthUid: String = firebaseAuth.uid.toString()
        val firestoreRef = firebaseFirestore.collection("users").document(firebaseAuthUid)
        firestoreRef.get().addOnSuccessListener {
            documentSnapshot ->
            if (documentSnapshot != null){
                val map: HashMap<String,String> = HashMap<String,String>()
                map[Constants.Username] = usernameEV.text.toString()
                map[Constants.Password] = passwordsignupEv.text.toString()
                map[Constants.DeviceToken] = firebaseNewToken
                map[Constants.Uid] = firebaseAuthUid
                map[Constants.FullName] = fullnameEV.text.toString()
                map[Constants.LastLogin] = currentTime
                map[Constants.PhoneNumber] = phonenumberEV.text.toString()
                map[Constants.ImageURL] = ""
                map[Constants.EmailID] = emailsignupEV.text.toString()
                map[Constants.PrimaryAddress] = primaryAddressEv.text.toString()
                map[Constants.Experience] = ""
                map[Constants.DateOfBirth] = ""
                map[Constants.FatherName] = fathernameEv.text.toString()
                map[Constants.MotherName] = mothernameEV.text.toString()
                map[Constants.FatherOccupation] = fatherOccupationEv.text.toString()
                map[Constants.MotherOccupation] = motherOccupationEV.text.toString()
                Log.d(TAG, "saveData: $map")
                firestoreRef.set(map)
                progressDialog.dismiss()
                startActivity(Intent(this, HomeActivity::class.java))
            }else{
                progressDialog.dismiss()
                Toast.makeText(this,"No Data Found",Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to save data $it", Toast.LENGTH_LONG).show()
            progressDialog.dismiss()
            Log.d(TAG,"Error: $it")
        }
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
}