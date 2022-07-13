package com.contactandroidapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.contactandroidapp.databinding.ActivityContactDetailsBinding
import org.json.JSONException

//class for Contact Details
class ContactDetails : AppCompatActivity() {

    lateinit var activityContactDetailsBinding: ActivityContactDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContactDetailsBinding = DataBindingUtil.setContentView(this ,R.layout.activity_contact_details)

        activityContactDetailsBinding.name.text = "Name : ${intent.getStringExtra("name")}"
        activityContactDetailsBinding.contact.text = "Contact No. : ${intent.getStringExtra("number")}"

        activityContactDetailsBinding.ivback.setOnClickListener{
            finish()
        }
        activityContactDetailsBinding.sendMessageButton.setOnClickListener {
            val i = Intent(this@ContactDetails, sendMessagePage::class.java)
            try {
                i.putExtra("name", intent.getStringExtra("name"))
                i.putExtra("number", intent.getStringExtra("number"))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            startActivity(i)
        }
    }

}