package com.androider.kotlin.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.androider.kotlin.R
import com.androider.kotlin.ui.imageSlide.ImageSliderAdapter
import com.androider.kotlin.ui.teacher.AddAttendanceActivity
import com.androider.kotlin.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlin.math.abs


class HomeFragment : Fragment() {

    private var imageURLList = mutableListOf<String>()
    private var handler: Handler = Handler()
    lateinit var runnable: Runnable
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        statusBarColor()
        val view : View = inflater.inflate(R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment
        when (Constants.TempUserType) {
            "student" -> {
                view.studentLay.visibility = View.VISIBLE
            }
            "admin" -> {
                view.adminLay.visibility = View.VISIBLE
            }
            "teacher" -> {
                view.teacherLay.visibility = View.VISIBLE
            }
            "busdriver" ->{

            }
            else -> {
                Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
            }
        }

        //Notice board banner
        noticeBanner(view)
        //Set User Profile Picture
        view.usernameTv.text = Constants.TempUsername
        Glide.with(view.context).load(Constants.TempImageURL).error(R.drawable.default_profile).centerCrop().into(view.profileImg)

        teacherLay(view)
        studentLay(view)
        adminLay(view)



        return view
    }



    private fun teacherLay(view: View){
        view.teacherTrackBusLay.setOnClickListener(){
            startActivity(Intent(context,TrackBusMapActivity::class.java))
        }
        view.teacherAttendanceLay.setOnClickListener {
            startActivity(Intent(context,AddAttendanceActivity::class.java))
        }
    }
    private fun studentLay(view: View){
        view.studentTrackBusLay.setOnClickListener(){
            startActivity(Intent(context,TrackBusMapActivity::class.java))
        }
    }
    private fun adminLay(view: View){
        view.adminAddUserLay.setOnClickListener {
            openAddUserPage()
        }

        view.adminTrackBusLay.setOnClickListener(){
            startActivity(Intent(context,TrackBusMapActivity::class.java))
        }
    }

    private fun noticeBanner(view: View){
        addImageURLList()
        view.imageSliderViewPager.adapter = ImageSliderAdapter(imageURLList)
        view.imageSliderViewPager.clipToPadding = false
        view.imageSliderViewPager.clipChildren = false
        view.imageSliderViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(10))
        compositePageTransformer.addTransformer(ViewPager2.PageTransformer() { page: View, position: Float ->
            val float: Float = 1 - abs(position)
            page.scaleY = 0.85f + float * 0.15f
        })
        view.imageSliderViewPager.setPageTransformer(compositePageTransformer)
        view.imageSliderViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 10000)
            }
        })

        runnable = Runnable{
            view.imageSliderViewPager.currentItem = view.imageSliderViewPager.currentItem + 1
        }
        view.imageSliderViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 10000)
    }



    private fun statusBarColor(){
        val window: Window? = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = resources.getColor(R.color.colorPrimary)
    }

    private fun openAddUserPage(){
        startActivity(Intent(context, AddUserActivity::class.java))
    }

    private fun addImageURLList(){
        imageURLList.add("https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80")
        imageURLList.add("https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=2134&q=80")
        imageURLList.add("https://images.unsplash.com/photo-1569913486515-b74bf7751574?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=635&q=80")
        imageURLList.add("https://images.unsplash.com/photo-1599566150163-29194dcaad36?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=634&q=80")
        imageURLList.add("https://images.unsplash.com/photo-1601455763557-db1bea8a9a5a?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=1386&q=80")
    }











}

