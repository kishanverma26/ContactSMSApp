package com.contactandroidapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.contactandroidapp.R
import com.contactandroidapp.ViewModel.Frag_ViewModel
import com.contactandroidapp.contactapp.Adapter.MessageAdapter
import com.contactandroidapp.databinding.ContactRecyclerviewFragBinding

class Message_Frag : Fragment() {


    private lateinit var viewModel: Frag_ViewModel
    lateinit var contactRecyclerviewFragBinding: ContactRecyclerviewFragBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contactRecyclerviewFragBinding= DataBindingUtil.inflate(inflater, R.layout.contact_recyclerview_frag,container,false)
        return contactRecyclerviewFragBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //load data initially

        viewModel = ViewModelProvider(this).get(Frag_ViewModel::class.java)
        contactRecyclerviewFragBinding.recyclerviewf1.layoutManager= LinearLayoutManager(context)
        contactRecyclerviewFragBinding.recyclerviewf1.addItemDecoration(
            DividerItemDecoration(
                this.activity,
                LinearLayout.VERTICAL
            )
        )
        viewModel.refresh(requireActivity())
        contactRecyclerviewFragBinding.recyclerviewf1.adapter= MessageAdapter(requireContext(),viewModel.messagelist)
    }
}