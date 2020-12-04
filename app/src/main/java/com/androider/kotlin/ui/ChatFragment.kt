package com.androider.kotlin.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androider.kotlin.R
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*

class ChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_chat, container, false)
        view.toolbarBackBtn.visibility = View.INVISIBLE
        view.toolbarText.text = "Messages"

        openNewMessagePage(view)
        return view
    }


    private fun openNewMessagePage(view: View){
        view.newMessageBtn.setOnClickListener(){
            startActivity(Intent(context,NewMessageActivity::class.java))
        }
    }




}