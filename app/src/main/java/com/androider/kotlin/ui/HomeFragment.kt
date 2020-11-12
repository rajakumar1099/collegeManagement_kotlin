package com.androider.kotlin.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.androider.kotlin.R
import com.androider.kotlin.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        statusBarColor()
        val view : View = inflater.inflate(R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment
        if (Constants.TempUserType == "student") {
            view.timeTableLay.visibility = View.VISIBLE
        }else {
            view.timeTableLay.visibility = View.GONE
        }


        view.usernameTv.text = Constants.TempUsername
        Glide.with(view.context).load(Constants.TempImageURL.toString()).into(view.profileImg)



        view.adminAddUserLay.setOnClickListener {
            openAddUserPage()
        }

        return view
    }

    private fun statusBarColor(){
        val window: Window? = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = resources.getColor(R.color.colorPrimary)
    }

    private fun openAddUserPage(){
        startActivity(Intent(context,AddUserActivity::class.java))
    }










}

