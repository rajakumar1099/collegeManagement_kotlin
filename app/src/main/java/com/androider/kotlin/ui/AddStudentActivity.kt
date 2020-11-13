package com.androider.kotlin.ui

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.androider.kotlin.R
import kotlinx.android.synthetic.main.activity_add_student.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        toolbar()
        addStudentBtn.setOnClickListener(){
            addStudent()
        }

        sDateOfBirth.setOnClickListener(){
            addStudentDateOfBirth()
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

    private fun addStudent(){
        when {
            sUsername.text.isEmpty() -> {
                sUsername.error = "Enter the Username"
            }
            sPassword.text.isEmpty() -> {
                sPassword.error = "Enter the Password"
            }
            sBatch.text.isEmpty() -> {
                sBatch.error = "Enter the Batch"
            }
            sMobileNumber.text.isEmpty() -> {
                sMobileNumber.error = "Enter the Mobile number"
            }
            sEmailID.text.isEmpty() -> {
                sEmailID.error = "Enter the Email ID"
            }
            sPrimaryAddress.text.isEmpty() -> {
                sPrimaryAddress.error = "Enter the Primary Address"
            }
            sDateOfBirth.text.isEmpty() -> {
                sDateOfBirth.error = "Enter the Date Of Birth Address"
            }
            sFatherName.text.isEmpty() -> {
                sFatherName.error = "Enter the Father Name"
            }
            sFatherOccupation.text.isEmpty() -> {
                sFatherOccupation.error = "Enter the Father occupation"
            }
            sMotherName.text.isEmpty() -> {
                sMotherName.error = "Enter the Mother Name"
            }
            sMotherOccupation.text.isEmpty() -> {
                sMotherOccupation.error = "Enter the Mother occupation"
            }else -> {
                var username = sUsername.text.trim()
                var password = sPassword.text.trim()
                var batch = sBatch.text.trim()
                var mobileNumber = sMobileNumber.text.trim()
                var emailID = sEmailID.text.trim()
                var primaryAddress = sPrimaryAddress.text.trim()
                var dataOfBirth = sDateOfBirth.text.trim()
                var fatherName = sFatherName.text.trim()
                var fatherOccupation = sFatherOccupation.text.trim()
                var motherName = sMotherName.text.trim()
                var motherOccupation = sMotherOccupation.text.trim()


        }
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