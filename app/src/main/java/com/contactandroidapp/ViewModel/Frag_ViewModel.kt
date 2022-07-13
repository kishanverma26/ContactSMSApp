package com.contactandroidapp.ViewModel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.contactandroidapp.Model.ContactListModel
import com.contactandroidapp.Network.RetrofitBuilder.lastFmService
import com.contactandroidapp.ViewUtils.JSONCaching
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class Frag_ViewModel :ViewModel()
{

    //fetch json string from json file in assets folder and list them
    fun readContacts(context: Context,list:ArrayList<ContactListModel>):ArrayList<ContactListModel>
    {
        var  inputStream: InputStream = context.assets.open("staticContacts.json")
        var json = inputStream.bufferedReader().use{it.readText()}

        val obj= JSONObject(json)
        for(i in 0 until obj.getJSONArray("contacts").length())
        {
            val jsonObject=obj.getJSONArray("contacts").getJSONObject(i)
            list.add(ContactListModel(jsonObject.getString("id"),
                    jsonObject.getString("firstName"),
                    jsonObject.getString("lastName"),
                    jsonObject.getString("mobileno")))
        }
        return list
    }

    /*reads data from cache file and adds to the list*/
    var messagelist = ArrayList<String>()
    fun refresh(activity:Activity):ArrayList<String>{
        messagelist.clear()
        //fetch cached data and list them into listView
        val olderMessage: JSONArray = JSONCaching(activity!!).readJSONFromFile()
        Log.e("older message", olderMessage.toString())
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss")

        for (i in olderMessage.length() - 1 downTo 0) {
            var jo: JSONObject? = null
            try {
                jo = olderMessage.getJSONObject(i)
                Log.e("jsondata",jo.toString())
                val date = dateFormat.parse(jo.getString("Date"))
                val nameText = jo.getString("name")
                val number = jo.getString("number")
                val otpText = jo.getString("OTP")
                val dateText = SimpleDateFormat("dd MMM,yyyy hh:mm:ss").format(date)

                messagelist.add( "Name: $nameText\nPhone No: $number\nOTP: $otpText\nDate: $dateText")

            } catch (e: JSONException) {
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return messagelist
    }

    //generates random number
    fun generateRandomNumber():String
    {
        //use random class instead
        val rand = Random()
        val OTP = Integer.toString(rand.nextInt(1000000 - 100000) + 100000)
        return OTP
    }

    /*send otp to number selected using retrofit and live data used to check response*/
    lateinit var apiData : MutableLiveData<String>
    fun getapiData(phoneno:String?,otp:String):LiveData<String>
    {
        apiData = MutableLiveData()
        senndOTP(phoneno,otp)
        return apiData
    }
    fun senndOTP(phoneno:String?,otp:String){

        Log.e("data",phoneno+"-"+otp)
        val call :Call<JSONObject> = lastFmService?.getUsers("+91${phoneno}",otp)!!
        call.enqueue(object : Callback<JSONObject>{
            override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                if (response.isSuccessful){
                    Log.e("responsesucess",response.code().toString())
                    if(response.code()==200) {
                        apiData.value = "success"
                    }
                    else {
                        apiData.value = "fail"
                    }
                }
            }

            override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                Log.e("responsefail","",t)
                apiData.value="fail"
            }
        })
    }
}