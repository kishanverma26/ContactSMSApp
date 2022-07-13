package com.contactandroidapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.contactandroidapp.ViewModel.Frag_ViewModel
import com.contactandroidapp.ViewUtils.JSONCaching
import com.contactandroidapp.databinding.ActivitySendMessagePageBinding
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class sendMessagePage : AppCompatActivity() {

    var otp: String = ""
    private lateinit var viewModel: Frag_ViewModel
    lateinit var progress:ProgressDialog
    lateinit var binding: ActivitySendMessagePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil. setContentView(this,R.layout.activity_send_message_page)

        viewModel = ViewModelProvider(this).get(Frag_ViewModel::class.java)
        otp = viewModel.generateRandomNumber()
        binding.message.text = "Hi, your OTP is"
        binding.textView.text = otp
        binding.ivback.setOnClickListener{
            finish()
        }
        progress = ProgressDialog(this)
        progress.setMessage("Please wait....")
        binding.send.setOnClickListener {

           progress.show()
            viewModel.getapiData(intent.getStringExtra("number"), otp).observe(this, androidx.lifecycle.Observer {
                if (it == "success") {
                    progress.dismiss()
                    Toast.makeText(this, "SuccessFully otp sent", Toast.LENGTH_SHORT).show()
                    try {
                        //Calendar c = Calendar.getInstance();
                        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
                        val currentDateandTime = sdf.format(Date())
                        val jsonObject = JSONObject()
                        jsonObject.put("name", intent.getStringExtra("name"))
                        jsonObject.put("number", intent.getStringExtra("number"))
                        jsonObject.put("OTP", otp)
                        jsonObject.put("Date", currentDateandTime)
                        Log.e("jsondata",jsonObject.toString())
                        JSONCaching(this@sendMessagePage).writeAppendToJSONFile(jsonObject)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    finish()
                    startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP))
                } else {
                    progress.dismiss()
                    Toast.makeText(this, "Sorry,Please try after some time", Toast.LENGTH_SHORT)
                        .show()
                }
            })

        }
    }
}