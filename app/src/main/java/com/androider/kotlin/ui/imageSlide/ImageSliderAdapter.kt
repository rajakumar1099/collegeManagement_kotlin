package com.androider.kotlin.ui.imageSlide

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.androider.kotlin.R
import com.bumptech.glide.Glide


class ImageSliderAdapter(var imageURL: MutableList<String>) : RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image_slider,parent,false))
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_image_slider, parent, false)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageURL.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(imageURL[position]).into(holder.itemImageView)
        holder.itemView.setOnClickListener(){
            view : View ->
            val position: Int = position
            Toast.makeText(view.context,"$position",Toast.LENGTH_SHORT).show()
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemImageView: ImageView = itemView.findViewById(R.id.iv_auto_image_slider)
    }


}




