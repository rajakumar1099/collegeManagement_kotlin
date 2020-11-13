package com.androider.kotlin.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.androider.kotlin.R
import com.androider.kotlin.ui.imageSlide.ImageSliderAdapter
import com.androider.kotlin.utils.Constants
import com.bumptech.glide.Glide
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
        if (Constants.TempUserType == "student") {
            view.timeTableLay.visibility = View.VISIBLE
        }else {
            view.timeTableLay.visibility = View.GONE
        }

        //Notice board banner
        noticeBanner(view)



        //Set User Profile Picture
        view.usernameTv.text = Constants.TempUsername
        Glide.with(view.context).load(Constants.TempImageURL).into(view.profileImg)



        view.adminAddUserLay.setOnClickListener {
            openAddUserPage()
        }

        return view
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
        for(i in 1..5){
            imageURLList.add("https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80")
        }
    }











}

