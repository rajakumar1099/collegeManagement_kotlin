package com.androider.kotlin.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SharedMemory
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androider.kotlin.R
import com.androider.kotlin.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : Fragment() {

    private lateinit var pref : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_profile, container, false)
        view.logoutImg.setOnClickListener {
            logOutBtn()
        }

        setUpProfile(view)
        return view
    }

    private fun setUpProfile(view: View){
        Glide.with(view.context).load(Constants.TempImageURL).error(R.drawable.avatar).into(view.profileImg)
    }

    private fun logOutBtn(){
        pref = context?.getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE)!!

        val editor : SharedPreferences.Editor = pref.edit()
        editor.clear()
        editor.apply()

        Constants.TempUsername = ""
        Constants.TempPassword = ""
        Constants.TempUserType = ""
        Constants.TempDeviceToken = ""
        Constants.TempUid = ""
        Constants.TempFullName =""
        Constants.TempLastLogin = ""
        Constants.TempBatch = ""
        Constants.TempPhoneNumber = ""
        Constants.TempImageURL = ""
        Constants.TempEmailID =""
        Constants.TempPrimaryAddress =""
        Constants.TempExperience = ""
        Constants.TempDateOfBirth = ""
        Constants.TempFatherName = ""
        Constants.TempMotherName = ""
        Constants.TempFatherOccupation =""
        Constants.TempMotherOccupation = ""

        startActivity(Intent(context,LoginActivity::class.java))
        this.activity?.finish()
    }

}