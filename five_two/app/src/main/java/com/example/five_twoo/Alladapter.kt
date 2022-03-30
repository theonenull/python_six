//recyclerview需要对应的adapter为recyclerview来提供数据及绑定视图
package com.example.five_twoo

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import java.lang.reflect.Array


class Alladapter(val List: ArrayList<String>, val Activity: Activity) :

    RecyclerView.Adapter<Alladapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Alladapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        Log.d("nnnnnnnnnnnn","m2222222222222222222222222222222222222222222mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = List[position]
        Log.d("nnnnnnnnnnnn","mmmmmmmmmmm1111111111111111111111111111111111mmmmmmmmmmmmmmmmmmmmmm")
        Glide.with(Activity.baseContext)
            .load(itemData)
            .into(holder.image);
        holder.image.setOnClickListener {
            var intent=Intent(Activity.baseContext,DetailActivity::class.java)
            intent.putExtra("string",itemData)
            Activity.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = List.size




}