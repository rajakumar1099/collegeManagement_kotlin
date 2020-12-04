package com.androider.kotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androider.kotlin.R
import kotlinx.android.synthetic.main.toolbar.*

class NewMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        setUpToolbar()
    }

    private fun setUpToolbar() {
        toolbarBackBtn.setOnClickListener(){
            onBackPressed()
        }
        toolbarText.text = "Select Person"
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
