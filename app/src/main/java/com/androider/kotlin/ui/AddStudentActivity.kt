package com.androider.kotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.androider.kotlin.R
import kotlinx.android.synthetic.main.activity_add_student.*
import kotlinx.android.synthetic.main.toolbar.*

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        toolbar()
        addStudentBtn.setOnClickListener(){
            addStudent()
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
                sDateOfBirth.error = "Enter the Primary Address"
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

            }
        }
    }

    private fun addStudentDate(){

    }
}