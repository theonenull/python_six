package com.example.homework

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import java.lang.RuntimeException
import kotlinx.android.synthetic.main.activity_detail.*
import kotlin.coroutines.coroutineContext


class UpAdapter (val upList:List<Up>,val view3:ImageView,val mainActivity: MainActivity):

    RecyclerView.Adapter<UpAdapter.ViewHolder>() {
    private val tag = "FruitAdapter"


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fruitImage: ImageView = view.findViewById(R.id.fruitImage)
        val fruitName: TextView = view.findViewById(R.id.fruitName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(tag, "onCreateViewHolder")

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.up_item, parent, false)
        val viewHolder = ViewHolder(view)


        viewHolder.fruitImage.setOnClickListener {

            val position = viewHolder.layoutPosition
            val up = upList[position]
            Toast.makeText(parent.context, "you clicked image ${up.name}", Toast.LENGTH_SHORT)
                .show()
            view3.setImageResource(up.imageId2)

        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = upList[position]
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text = fruit.name
        val mHolder=holder
        mHolder.fruitImage.setOnLongClickListener(OnLongClickListener { v ->
            val layoutPosition: Int = mHolder.getLayoutPosition()
            val intent=Intent(mainActivity.baseContext,DetailActivity::class.java)
            val apple_pic1=fruit.imageId
            val apple_pic2=fruit.imageId2
            val apple_pic3=fruit.imageId3
            val person=fruit.person
            val name=fruit.name
            intent.putExtra("fruitimage1",apple_pic1)
            intent.putExtra("fruitimage2",apple_pic2)
            intent.putExtra("fruitimage3",apple_pic3)
            intent.putExtra("number",person)
            intent.putExtra("position",position)
            intent.putExtra("name",name)
            mainActivity.startActivityForResult(intent,1)
            true

        })
    }

    override fun getItemCount(): Int = upList.size

}