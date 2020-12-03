package com.androider.kotlin.ui.teacher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.androider.kotlin.R
import kotlinx.android.synthetic.main.activity_add_attendance.*
import kotlinx.android.synthetic.main.activity_add_attendance.sDepartment
import kotlinx.android.synthetic.main.toolbar.*

class AddAttendanceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_attendance)
        initView()
        toolbar()
    }

    private fun initView(){
        sDepartment.visibility = View.VISIBLE
        sDepartment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (sDepartment.selectedItem.toString() == "Select The Department"){
                    sClass.visibility = View.GONE
                }else{
                    sClass.visibility = View.VISIBLE
                }
             }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    private fun toolbar(){
        toolbarText.text = "Add Attendance"
        toolbarBackBtn.visibility = View.VISIBLE
        toolbarBackBtn.setOnClickListener(){
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}