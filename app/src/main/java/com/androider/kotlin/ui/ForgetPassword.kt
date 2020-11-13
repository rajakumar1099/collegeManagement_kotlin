package com.androider.kotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.androider.kotlin.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.toolbar.*

class ForgetPassword : AppCompatActivity() {
    lateinit var firebaseAuth :FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        setToolbar()

        firebaseAuth = FirebaseAuth.getInstance()


    }



    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setToolbar(){
        toolbarText.text = "Forget Password"
        toolbarBackBtn.visibility = View.VISIBLE
        toolbarBackBtn.setOnClickListener(){
            onBackPressed()
        }
    }
}