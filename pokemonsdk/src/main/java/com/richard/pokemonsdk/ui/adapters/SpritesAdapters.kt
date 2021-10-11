package com.richard.pokemonsdk.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.richard.pokemonsdk.R
import com.squareup.picasso.Picasso

/**
 * Simple adapter to attached incomming images
 */
class SpritesAdapters : RecyclerView.Adapter<SpritesAdapters.MyViewHolder> {


    private lateinit var context : Context
    private  var mData: ArrayList<String>
    private lateinit var layoutInflater : LayoutInflater

    constructor(bitmaps: ArrayList<String>){
        mData = bitmaps
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        layoutInflater = LayoutInflater.from(context)
        val vH = layoutInflater.inflate(R.layout.sprites_single_image,parent,false);
        return MyViewHolder(vH)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(mData.get(position)).into(holder.imageView)
        holder.imageView.setOnClickListener({

        })
    }

    override fun getItemCount(): Int {
        return mData.size
    }



    inner class MyViewHolder: RecyclerView.ViewHolder {

        var imageView : ImageView


        constructor(itemView: View) : super(itemView) {
            imageView = itemView.findViewById(R.id.icon);
        }
    }
}