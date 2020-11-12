package com.androider.kotlin.ui

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.androider.kotlin.R
import com.androider.kotlin.utils.Constants
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeActivity : AppCompatActivity() {

    lateinit var alertDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val homeFragment = HomeFragment()
        val eventFragment = EventFragment()
        val chatFragment = ChatFragment()
        val profileFragment = ProfileFragment()

        makeCurrentFragment(homeFragment)
        bottomNavigationBar.setItemSelected(R.id.home)
        bottomNavigationBar.setOnItemSelectedListener {

            when(it){
                R.id.home -> makeCurrentFragment(homeFragment)
                R.id.events -> makeCurrentFragment(eventFragment)
                R.id.chat -> makeCurrentFragment(chatFragment)
                R.id.profile -> makeCurrentFragment(profileFragment)
            }
        }


    }

    private fun makeCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.frameLay,fragment).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        alertDialog()
    }

    private fun alertDialog(){
        alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Alert")
        alertDialog.setCancelable(true)
        alertDialog.setMessage("Are you sure?")

        alertDialog.setPositiveButton("Yes"){dialogInterface, which ->
            finish()
        }
        alertDialog.setPositiveButton("No"){dialogInterface, which ->
            Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
        }
        alertDialog.create()
        alertDialog.show()

//        val positiveButtonClick = { dialog: DialogInterface, which: Int ->
//            finish()
//        }
//        val negativeButtonClick = { dialog: DialogInterface, which: Int ->
//        }
//        alertDialog.setPositiveButton("Yes", DialogInterface.OnClickListener(function = positiveButtonClick))
//        alertDialog.setNegativeButton("No", DialogInterface.OnClickListener(function = negativeButtonClick))

    }
}