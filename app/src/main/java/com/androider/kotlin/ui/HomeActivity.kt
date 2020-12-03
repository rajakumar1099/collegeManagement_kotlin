package com.androider.kotlin.ui

import android.R.attr
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.androider.kotlin.R
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    lateinit var alertDialog: AlertDialog.Builder
    var startingPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val homeFragment = HomeFragment()
        val eventFragment = EventFragment()
        val chatFragment = ChatFragment()
        val profileFragment = ProfileFragment()

        makeCurrentFragment(homeFragment, 1)
//        val materialShapeDrawable : MaterialShapeDrawable = bottomNavigationBar.background as MaterialShapeDrawable
//        materialShapeDrawable.shapeAppearanceModel.toBuilder().setTopRightCorner(CornerFamily.ROUNDED,20f)
//                .setTopLeftCorner(CornerFamily.ROUNDED,20f).build()
        bottomNavigationBar.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.home -> makeCurrentFragment(homeFragment, 1)
                R.id.events -> makeCurrentFragment(eventFragment, 2)
                R.id.chat -> makeCurrentFragment(chatFragment, 3)
                R.id.profile -> makeCurrentFragment(profileFragment, 4)
            }
            true
        }


    }

    private fun makeCurrentFragment(fragment: Fragment, newPosition: Int)
//            = supportFragmentManager.beginTransaction().apply {
//        setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.frameLay, fragment, "h")
//                .addToBackStack("h").commit()
    {
        if (startingPosition > newPosition) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right)
            transaction.replace(R.id.frameLay, fragment)
            transaction.commit()
        }
        if (startingPosition < newPosition) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            transaction.replace(R.id.frameLay, fragment)
            transaction.commit()
        }
        startingPosition = newPosition

    }

    override fun onBackPressed() {
        alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Alert")
        alertDialog.setCancelable(true)
        alertDialog.setMessage("Are you sure you want to close?")

        val positiveButtonClick = { dialog: DialogInterface, which: Int ->
            finish()
        }
        val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        }
        alertDialog.setPositiveButton("Yes", DialogInterface.OnClickListener(function = positiveButtonClick))
        alertDialog.setNegativeButton("No", DialogInterface.OnClickListener(function = negativeButtonClick))
        alertDialog.create()
        alertDialog.show()
    }

}