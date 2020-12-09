package com.androider.kotlin.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.androider.kotlin.R
import com.androider.kotlin.ui.driver.DriverMapActivity
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.android.synthetic.main.toolbar.*

class AddUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        toolbar()


        addStudentLay.setOnClickListener(){
            startActivity(Intent(this,AddStudentActivity::class.java))
        }

        addTeacherLay.setOnClickListener(){
            startActivity(Intent(this,AddTeacherActivity::class.java))
        }
        addBusDriverLay.setOnClickListener(){
            startActivity(Intent(this,AddDriverActivity::class.java))
        }
    }

    private fun toolbar(){
        toolbarText.text = "Add Users"
        toolbarText.visibility = View.VISIBLE
        toolbarBackBtn.visibility = View.VISIBLE
        toolbarBackBtn.setOnClickListener(){
            finish()
        }
    }


}