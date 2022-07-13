package com.contactandroidapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.contactandroidapp.ContactDetails
import com.contactandroidapp.R
import com.contactandroidapp.Model.ContactListModel
import com.contactandroidapp.databinding.ListItemContactsBinding
import org.json.JSONException


class ContactAdapter(val contextCompat: Context,val list: ArrayList<ContactListModel>):RecyclerView.Adapter<ContactAdapter.ViewHolder>(){
    class ViewHolder(itemView: ListItemContactsBinding):RecyclerView.ViewHolder(itemView.root) {
        val name=itemView.name
        val mobileno=itemView.mobileno
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var listItemContactsBinding: ListItemContactsBinding
        val view = LayoutInflater.from(parent.context)
        listItemContactsBinding = DataBindingUtil.inflate(view,R.layout.list_item_contacts, parent, false)
        return ViewHolder(listItemContactsBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text="${list[position].firstName} ${list[position].lastName}"
        holder.mobileno.text=list[position].mobileno
        holder.itemView.setOnClickListener{
            val i = Intent(contextCompat, ContactDetails::class.java)
            try {
                i.putExtra("name",holder.name.text.toString())
                i.putExtra("number",holder.mobileno.text.toString())
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            contextCompat.startActivity(i)
        }
    }

}