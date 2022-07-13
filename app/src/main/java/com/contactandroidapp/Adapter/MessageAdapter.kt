package com.contactandroidapp.contactapp.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.contactandroidapp.R
import com.contactandroidapp.databinding.MessageItemContactsBinding


class MessageAdapter(val contextCompat: Context, val list: ArrayList<String>):RecyclerView.Adapter<MessageAdapter.ViewHolder>(){

    class ViewHolder( messageItemContactsBinding :MessageItemContactsBinding):RecyclerView.ViewHolder(messageItemContactsBinding.root) {
        val count=messageItemContactsBinding.count
        val mobileno=messageItemContactsBinding.mobileno
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        val messageItemContactsBinding :MessageItemContactsBinding
        val view = LayoutInflater.from(parent.context)
        messageItemContactsBinding=  DataBindingUtil.inflate(view, R.layout.message_item_contacts, parent, false)
        return ViewHolder(messageItemContactsBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        holder.count.text="${position+1}"
        holder.mobileno.text=list[position].toString()
    }



}