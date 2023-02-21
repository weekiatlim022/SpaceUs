package com.example.spaceus.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceus.R
import com.google.android.material.imageview.ShapeableImageView

class Adapter (val listItem:ArrayList<Locations>) : RecyclerView.Adapter<Adapter.RecycleViewHolder>(){
    inner class RecycleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val itemImage: ShapeableImageView = itemView.findViewById(R.id.item_image)
        val heading: TextView = itemView.findViewById(R.id.item_title)
        val detail: TextView = itemView.findViewById(R.id.item_detail)
        val category: TextView = itemView.findViewById(R.id.item_categories)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return RecycleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val item = listItem[position]
        holder.itemImage.setImageResource(item.itemImage)
        holder.heading.text = item.headings
        holder.detail.text = item.detail
        holder.category.text = item.category

    }


}