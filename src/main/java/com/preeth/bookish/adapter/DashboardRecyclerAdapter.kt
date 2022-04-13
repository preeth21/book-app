package com.preeth.bookish.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.preeth.bookish.R
import com.preeth.bookish.model.Book
import com.squareup.picasso.Picasso

class DashboardRecyclerAdapter(val context:Context,val itemList:ArrayList<Book>):RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {
    class DashboardViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val txtBookName: TextView =view.findViewById<TextView>(R.id.txtBookName)
        val txtAuthor:TextView=view.findViewById(R.id.txtAuthorName)
        val txtPrice:TextView=view.findViewById(R.id.txtPrice)
        val txtRating=view.findViewById<TextView>(R.id.txtRating)
        val imgBook=view.findViewById<ImageView>(R.id.imgOfBook)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.single_row,parent,false)
        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {

        holder.txtBookName.text=itemList[position].name
        holder.txtAuthor.text=itemList[position].author
        holder.txtPrice.text=itemList[position].price
        holder.txtRating.text=itemList[position].rating
        Picasso.get().load(itemList[position].image).error(R.drawable.default_book_cover).into(holder.imgBook)

    }
}