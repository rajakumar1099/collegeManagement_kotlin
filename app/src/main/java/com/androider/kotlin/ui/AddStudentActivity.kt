package com.androider.kotlin.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.util.rangeTo
import com.androider.kotlin.R
import com.androider.kotlin.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_add_student.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*
import kotlin.math.log

class AddStudentActivity : AppCompatActivity() {
    private var loginShow : Boolean = false
    private val TAG = "MyActivity"
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: AlertDialog
    lateinit var firebaseFirestore : FirebaseFirestore
    var gender : String = ""
    lateinit var radioButton: RadioButton
    var spinnerVal: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        toolbar()
        alertDialog()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        spinnerListener()

        showPasswordBtn.setOnClickListener() {
            showHidePassword(sPassword, showPasswordBtn)
        }
        addStudentBtn.setOnClickListener(){
            hideKeyboard()
            addStudent()
        }

        sDateOfBirth.setOnClickListener(){
            addStudentDateOfBirth()
        }

    }

    private fun spinnerListener(){
        sDepartment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinnerVal = sDepartment.selectedItem.toString()
                if (spinnerVal=="Select The Department"){
                    sClassSpinnerLay.visibility = View.GONE
                }else{
                    sClassSpinnerLay.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }



    private fun toolbar(){
        toolbarText.text = "Add Student"
        toolbarText.visibility = View.VISIBLE
        toolbarBackBtn.visibility = View.VISIBLE
        toolbarBackBtn.setOnClickListener(){
            finish()
        }
    }

    private fun showHidePassword(passwordEv: EditText, showPasswordBtn: ImageView){
        if (sPassword.text.isNotEmpty()) {
            if (!loginShow) {
                loginShow = true
                sPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                Log.d(TAG, "showHidePassword: Show ${passwordEv.text}")
                showPasswordBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_showpassword))
            } else {
                showPasswordBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_hidepassword))
                loginShow = false
                sPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                Log.d(TAG, "showHidePassword: Hide ${passwordEv.text}")

            }
        }else{
            showPasswordBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_hidepassword))
        }
    }


    private fun addStudent(){
        progressDialog.show()
        var selectedID: Int = radioGroup.checkedRadioButtonId

        when {
            sUsername.text.isEmpty() -> {
                progressDialog.dismiss()
                sUsername.error = "Enter the Username"
            }
            sPassword.text.isEmpty() -> {
                progressDialog.dismiss()
                sPassword.error = "Enter the Password"
            }
            sPassword.text.length<5 ->{
                progressDialog.dismiss()
                sPassword.error = "Password should be more than 6 characters"
            }
            selectedID == -1->{
                progressDialog.dismiss()
                Toast.makeText(this,"Select Any One Gender",Toast.LENGTH_SHORT).show()
            }
            sBatch.text.isEmpty() -> {
                progressDialog.dismiss()
                sBatch.error = "Enter the Batch"
            }
            sRegisterNumber.text.isEmpty()->{
                progressDialog.dismiss()
                sRegisterNumber.error = "Enter the Register Number"
            }
            sRollNumber.text.isEmpty()->{
                progressDialog.dismiss()
                sRollNumber.error = "Enter the Roll Number"
            }
            sDepartment.selectedItem.toString().isEmpty() || sDepartment.selectedItem.toString() == "Select The Department" ->{
                progressDialog.dismiss()
                Toast.makeText(this,"Select The Department",Toast.LENGTH_SHORT).show()
            }
            sClass.selectedItem.toString().isEmpty() || sClass.selectedItem.toString() == "Select The Class" ->{
                progressDialog.dismiss()
                Toast.makeText(this,"Select The Class",Toast.LENGTH_SHORT).show()
            }
            sMobileNumber.text.isEmpty() -> {
                progressDialog.dismiss()
                sMobileNumber.error = "Enter the Mobile number"
            }
            sEmailID.text.isEmpty() -> {
                progressDialog.dismiss()
                sEmailID.error = "Enter the Email ID"
            }
            sPrimaryAddress.text.isEmpty() -> {
                progressDialog.dismiss()
                sPrimaryAddress.error = "Enter the Primary Address"
            }
            sDateOfBirth.text.isEmpty() -> {
                progressDialog.dismiss()
                sDateOfBirth.error = "Enter the Date Of Birth Address"
            }
            sFatherName.text.isEmpty() -> {
                progressDialog.dismiss()
                sFatherName.error = "Enter the Father Name"
            }
            sFatherOccupation.text.isEmpty() -> {
                progressDialog.dismiss()
                sFatherOccupation.error = "Enter the Father occupation"
            }
            sMotherName.text.isEmpty() -> {
                progressDialog.dismiss()
                sMotherName.error = "Enter the Mother Name"
            }
            sMotherOccupation.text.isEmpty() -> {
                progressDialog.dismiss()
                sMotherOccupation.error = "Enter the Mother occupation"
            }else -> {
                radioButton = findViewById(selectedID)
                Log.d(TAG, "addStudent: ${radioButton.text}")
                var username = sUsername.text.trim()
                var password = sPassword.text.trim()
                var gender = radioButton.text
                var batch = sBatch.text.trim()
                var registerNumber = sRegisterNumber.text.toString()
                var rollNumber = sRollNumber.text.toString()
                var department = sDepartment.selectedItem
                var type = "student"
                var mobileNumber = sMobileNumber.text.trim()
                var emailID = sEmailID.text.trim()
                var primaryAddress = sPrimaryAddress.text.trim()
                var dataOfBirth = sDateOfBirth.text.trim()
                var fatherName = sFatherName.text.trim()
                var fatherOccupation = sFatherOccupation.text.trim()
                var motherName = sMotherName.text.trim()
                var motherOccupation = sMotherOccupation.text.trim()

                Log.d(TAG, "addStudent: \nEmail: ${sEmailID.text.trim().toString()}, \nPassword: ${sPassword.text.trim().toString()}")
                firebaseAuth.createUserWithEmailAndPassword(sEmailID.text.trim().toString(),sPassword.text.trim().toString()).addOnSuccessListener {
                    createStudentAccount()
                }.addOnFailureListener(){
                    exception -> Toast.makeText(this,"Something went wrong $exception",Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
            }
        }
    }
    private fun alertDialog(){
        progressDialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait")
            .setCancelable(false)
            .build()
    }

    private fun createStudentAccount(){
        Log.d(TAG, "createStudentAccount: CreatingStudentAccount")
        val firestore = firebaseFirestore.collection("users").document(firebaseAuth.uid.toString())
        firestore.get().addOnSuccessListener {
            val map: HashMap<String,String> = HashMap<String,String>()
            map[Constants.Username] = sUsername.text.trim().toString()
            map[Constants.Password] = sPassword.text.trim().toString()
            map[Constants.Gender] = radioButton.text.toString()
            map[Constants.Batch] = sBatch.text.trim().toString()
            map[Constants.RegisterNumber] = sRegisterNumber.text.trim().toString()
            map[Constants.RollNumber] = sRollNumber.text.trim().toString()
            map[Constants.Department] = sDepartment.selectedItem.toString()
            map[Constants.Class] = sClass.selectedItem.toString()
            map[Constants.UserType] = "student"
            map[Constants.PhoneNumber] = sMobileNumber.text.trim().toString()
            map[Constants.EmailID] = sEmailID.text.trim().toString()
            map[Constants.PrimaryAddress] = sPrimaryAddress.text.trim().toString()
            map[Constants.DateOfBirth] = sDateOfBirth.text.trim().toString()
            map[Constants.FatherName] = sFatherName.text.trim().toString()
            map[Constants.FatherOccupation] = sFatherOccupation.text.trim().toString()
            map[Constants.MotherName] = sMotherName.text.trim().toString()
            map[Constants.MotherOccupation] = sMotherOccupation.text.trim().toString()
            map[Constants.DeviceToken] = "-"
            map[Constants.ImageURL] = "-"
            Log.d(TAG, "createStudentAccount: $map")
            firestore.set(map)
            progressDialog.dismiss()
            finish()

        }.addOnFailureListener(){
            exception -> Toast.makeText(this,"Something Went Wrong $exception",Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()

        }
    }


    private fun addStudentDateOfBirth(){
        hideKeyboard()
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePicker = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            DatePickerDialog(this,DatePickerDialog.OnDateSetListener{
                view, year, month, dayOfMonth ->
                var month = month+1
                var month1 = "0$month"
                var dayOfMonth1 = "0$dayOfMonth"

                if (month < 10 && dayOfMonth < 10){
                    sDateOfBirth.setText("$dayOfMonth1 - $month1 - $year")
                }else if(month < 10){
                    sDateOfBirth.setText("$dayOfMonth - $month1 - $year")
                }else if(dayOfMonth < 10){
                    sDateOfBirth.setText("$dayOfMonth1 - $month - $year")
                }else{
                    sDateOfBirth.setText("$dayOfMonth - $month - $year")
                }

            },year,month,day).show()
        } else {
            TODO("VERSION.SDK_INT < N")
            Toast.makeText(this,"Date Picker Note Supported",Toast.LENGTH_SHORT).show()
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