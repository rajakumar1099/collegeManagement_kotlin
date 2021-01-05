package com.androider.kotlin.ui.admin

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import com.androider.kotlin.R
import com.androider.kotlin.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_add_teacher.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class AddTeacherActivity : AppCompatActivity() {
    private var loginShow : Boolean = false
    private val TAG = "MyActivity"
    private lateinit var progressDialog: AlertDialog
    lateinit var radioButton: RadioButton
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_teacher)
        toolbar()
        alertDialog()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        tshowPasswordBtn.setOnClickListener() {
            showHidePassword(tPassword, tshowPasswordBtn)
        }
        addTeacherBtn.setOnClickListener(){
            hideKeyboard()
            addTeacher()
        }
        tDateOfBirth.setOnClickListener(){
            addTeacherDateOfBirth()
        }
    }

    private fun addTeacher(){
        progressDialog.show()
        var selectedID: Int = tradioGroup.checkedRadioButtonId
        when{
            tUsername.text.isEmpty()->{
                progressDialog.dismiss()
                tUsername.error = "Enter the Username"
            }
            tPassword.text.isEmpty()->{
                progressDialog.dismiss()
                tPassword.error = "Enter the Password"
            }
            tPassword.text.length<5 ->{
                progressDialog.dismiss()
                tPassword.error = "Password should be more than 6 characters"
            }
            selectedID == -1->{
                progressDialog.dismiss()
                Toast.makeText(this,"Select Any One Gender", Toast.LENGTH_SHORT).show()
            }
            tExperience.text.isEmpty() -> {
                progressDialog.dismiss()
                tExperience.error = "Enter the Total Experience"
            }
            tRollNumber.text.isEmpty()->{
                progressDialog.dismiss()
                tRollNumber.error = "Enter the Roll Number"
            }
            tMobileNumber.text.isEmpty() -> {
                progressDialog.dismiss()
                tMobileNumber.error = "Enter the Mobile number"
            }
            tEmailID.text.isEmpty() -> {
                progressDialog.dismiss()
                tEmailID.error = "Enter the Email ID"
            }
            tPrimaryAddress.text.isEmpty() -> {
                progressDialog.dismiss()
                tPrimaryAddress.error = "Enter the Primary Address"
            }
            tDateOfBirth.text.isEmpty() -> {
                progressDialog.dismiss()
                tDateOfBirth.error = "Enter the Date Of Birth Address"
            }
            else -> {
                radioButton = findViewById(selectedID)
                Log.d(TAG, "addTeacher: ${radioButton.text}")

                firebaseAuth.createUserWithEmailAndPassword(tEmailID.text.trim().toString(),tPassword.text.trim().toString()).addOnSuccessListener {
                    createTeacherAccount()
                }.addOnFailureListener(){
                    exception -> Toast.makeText(this,"Something went wrong $exception",Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
            }

        }


    }

    private fun createTeacherAccount() {
        Log.d(TAG, "createTeacherAccount: createTeacherAccount")
        val firestore = firebaseFirestore.collection("users").document(firebaseAuth.uid.toString())
        firestore.get().addOnSuccessListener {
            val map: HashMap<String,String> = HashMap<String,String>()
            map[Constants.Username] = tUsername.text.trim().toString()
            map[Constants.Password] = tPassword.text.trim().toString()
            map[Constants.Gender] = radioButton.text.toString()
            map[Constants.Experience] = tExperience.text.trim().toString()
            map[Constants.RollNumber] = tRollNumber.text.trim().toString()
            map[Constants.UserType] = "teacher"
            map[Constants.PhoneNumber] = tMobileNumber.text.trim().toString()
            map[Constants.EmailID] = tEmailID.text.trim().toString()
            map[Constants.PrimaryAddress] = tPrimaryAddress.text.trim().toString()
            map[Constants.DateOfBirth] = tDateOfBirth.text.trim().toString()
            map[Constants.DeviceToken] = "-"
            map[Constants.ImageURL] = "-"
            Log.d(TAG, "createTeacherAccount: $map")
            firestore.set(map)
            progressDialog.dismiss()
            finish()

        }.addOnFailureListener(){
            exception -> Toast.makeText(this,"Something Went Wrong $exception",Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }
    }

    private fun addTeacherDateOfBirth(){
        hideKeyboard()
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePicker = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                view, year, month, dayOfMonth ->
                var month = month+1
                var month1 = "0$month"
                var dayOfMonth1 = "0$dayOfMonth"

                if (month < 10 && dayOfMonth < 10){
                    tDateOfBirth.setText("$dayOfMonth1 - $month1 - $year")
                }else if(month < 10){
                    tDateOfBirth.setText("$dayOfMonth - $month1 - $year")
                }else if(dayOfMonth < 10){
                    tDateOfBirth.setText("$dayOfMonth1 - $month - $year")
                }else{
                    tDateOfBirth.setText("$dayOfMonth - $month - $year")
                }

            },year,month,day).show()
        } else {
            TODO("VERSION.SDK_INT < N")
            Toast.makeText(this,"Date Picker Note Supported",Toast.LENGTH_SHORT).show()
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
        toolbarText.text = "Add Teacher"
        toolbarText.visibility = View.VISIBLE
        toolbarBackBtn.visibility = View.VISIBLE
        toolbarBackBtn.setOnClickListener(){
            finish()
        }
    }

    private fun showHidePassword(passwordEv: EditText, tshowPasswordBtn: ImageView){
        if (tPassword.text.isNotEmpty()) {
            if (!loginShow) {
                loginShow = true
                tPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                Log.d(TAG, "showHidePassword: Show ${passwordEv.text}")
                tshowPasswordBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_showpassword))
            } else {
                tshowPasswordBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_hidepassword))
                loginShow = false
                tPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                Log.d(TAG, "showHidePassword: Hide ${passwordEv.text}")

            }
        }else{
            tshowPasswordBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_hidepassword))
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