package com.androider.kotlin.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androider.kotlin.R
import com.androider.kotlin.model.BusList
import kotlinx.android.synthetic.main.activity_available_bus.*
import kotlinx.android.synthetic.main.toolbar.*

class AvailableBusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_bus)
        toolbar()

    }

    private fun addAvailableBus() {

     /*   availableBusRecycler.adapter = AvailableBusRecyclerAdapter(exampleList,this)
        availableBusRecycler.layoutManager = LinearLayoutManager(this)
        availableBusRecycler.setHasFixedSize(true)*/
    }


    private fun toolbar() {
        toolbarText.text = "Add Users"
        toolbarText.visibility = View.VISIBLE
        toolbarBackBtn.visibility = View.VISIBLE
        toolbarBackBtn.setOnClickListener() {
            finish()
        }
    }
}

class AvailableBusRecyclerAdapter(private val availableBusList: List<BusList>, val context: Context): RecyclerView.Adapter<AvailableBusRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AvailableBusRecyclerAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_available_buses,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AvailableBusRecyclerAdapter.ViewHolder, position: Int) {
        holder.availableBusTv.text = availableBusList[position].busNumber.toString()
    }

    override fun getItemCount() = availableBusList.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val availableBusTv: TextView = itemView.findViewById(R.id.availableBus_tv)
    }

}
