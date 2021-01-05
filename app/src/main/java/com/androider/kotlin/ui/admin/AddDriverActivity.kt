package com.androider.kotlin.ui.admin

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.androider.kotlin.R
import com.androider.kotlin.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_add_driver.*
import kotlinx.android.synthetic.main.activity_add_student.*
import kotlinx.android.synthetic.main.activity_add_student.showPasswordBtn
import kotlinx.android.synthetic.main.toolbar.*
import java.util.HashMap

class AddDriverActivity : AppCompatActivity() {
    private var loginShow : Boolean = false
    private lateinit var progressDialog: AlertDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_driver)
        toolbar()
        alertDialog()
        showPasswordBtn.setOnClickListener() {
            showHidePassword(sPassword, showPasswordBtn)
        }
        addDriverBtn.setOnClickListener(){
            hideKeyboard()
            addDriverAccount()
        }
    }

    private fun addDriverAccount(){
        progressDialog.show()
        when {
            dUsername.text.toString().trim().isEmpty() -> {
                progressDialog.dismiss()
                dUsername.error = "Enter the Username"
            }
            dPassword.text.toString().trim().isEmpty() -> {
                progressDialog.dismiss()
                dPassword.error = "Enter the Password"
            }
            dPhoneNumber.text.toString().trim().isEmpty() -> {
                progressDialog.dismiss()
                dPhoneNumber.error = "Enter the Phone number"
            }
            dEmailID.text.toString().trim().isEmpty() -> {
                progressDialog.dismiss()
                dEmailID.error = "Enter the Email ID"
            }
            dbusNumber.text.toString().trim().isEmpty() -> {
                progressDialog.dismiss()
                dbusNumber.error = "Enter the Bus number"
            }
            else -> {
                firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.createUserWithEmailAndPassword(dEmailID.text.toString().trim(),dPassword.text.toString().trim()).addOnSuccessListener {
                     createDriverAccount()
                }.addOnFailureListener(){
                    Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createDriverAccount(){
        firebaseFirestore = FirebaseFirestore.getInstance()
        val firestore = firebaseFirestore.collection("users").document(firebaseAuth.uid.toString())
        firestore.get().addOnSuccessListener {
            val map: HashMap<String, String> = HashMap<String,String>()
            map[Constants.TAG_DRIVER_USERNAME] = dUsername.text.trim().toString()
            map[Constants.TAG_DRIVER_PASSWORD] = dPassword.text.trim().toString()
            map[Constants.TAG_DRIVER_EMAILID] = dEmailID.text.trim().toString()
            map[Constants.TAG_DRIVER_PHONENUMBER] = dPhoneNumber.text.trim().toString()
            map[Constants.TAG_DRIVER_BUS_NUMBER] = dbusNumber.text.trim().toString()
            map[Constants.UserType] = "driver"
            Log.d("TAG", "createDriverAccount: $map")
            firestore.set(map)
            progressDialog.dismiss()
            finish()
        }.addOnFailureListener(){
            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }
    }




    private fun alertDialog(){
        progressDialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait")
            .setCancelable(false)
            .build()
    }
    private fun toolbar(){
        toolbarText.text = "Add Driver"
        toolbarText.visibility = View.VISIBLE
        toolbarBackBtn.visibility = View.GONE
        toolbarBackBtn.setOnClickListener(){
            finish()
        }
    }
    private fun showHidePassword(passwordEv: EditText, showPasswordBtn: ImageView){
        if (sPassword.text.isNotEmpty()) {
            if (!loginShow) {
                loginShow = true
                sPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                Log.d("TAG", "showHidePassword: Show ${passwordEv.text}")
                showPasswordBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_showpassword))
            } else {
                showPasswordBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_hidepassword))
                loginShow = false
                sPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                Log.d("TAG", "showHidePassword: Hide ${passwordEv.text}")

            }
        }else{
            showPasswordBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_hidepassword))
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
}