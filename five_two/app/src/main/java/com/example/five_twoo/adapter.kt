//recyclerview需要对应的adapter为recyclerview来提供数据及绑定视图
package com.example.five_twoo

import android.app.Activity
import android.content.Context
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
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext


class adapter(val List: ArrayList<String>,var context: Context, var boolean: Boolean,var List2: ArrayList<String>) :

    RecyclerView.Adapter<adapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        Log.d("nnnnnnnnnnnn","m2222222222222222222222222222222222222222222mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = List[position]
        Log.d("nnnnnnnnnnnn","mmmmmmmmmmm1111111111111111111111111111111111mmmmmmmmmmmmmmmmmmmmmm")

                Glide.with(context)
                    .load(itemData)
                    .into(holder.image)



//        holder.image.setImageResource(R.drawable.ic_launcher_background)
        if(position<8){
            holder.image.setOnClickListener {
                Toast.makeText(context,position.toString(),Toast.LENGTH_SHORT).show()
                holder.image.setOnClickListener {
                    Toast.makeText(context,position.toString(),Toast.LENGTH_SHORT).show()
                    var intent=Intent(context,DetailActivity::class.java)
                    intent.putExtra("string",itemData)
                    context.startActivity(intent)
                }
            }
        }
        if(position==8){
            if(!boolean){
                holder.image.setOnClickListener {
                    Toast.makeText(context,position.toString(),Toast.LENGTH_SHORT).show()
                    var intent=Intent(context,DetailActivity::class.java)
                    intent.putExtra("string",itemData)
                    context.startActivity(intent)
                }
            }
            else{
                holder.image.setOnClickListener {
                Toast.makeText(context,position.toString(),Toast.LENGTH_SHORT).show()
                holder.image.setOnClickListener {
                    var intent=Intent(context,AllImageView::class.java)
                    intent.putStringArrayListExtra("list",List2)
                    context.startActivity(intent)
                }
                }

            }
        }


    }


    override fun getItemCount(): Int = List.size




}