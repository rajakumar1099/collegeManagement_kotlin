package com.androider.kotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androider.kotlin.R
import kotlinx.android.synthetic.main.toolbar.view.*


class EventFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_event, container, false)
        view.toolbarBackBtn.visibility = View.INVISIBLE
        view.toolbarText.text = "Events"
        return view
    }

}