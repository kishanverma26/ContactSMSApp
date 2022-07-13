package com.contactandroidapp.ViewUtils

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*

class JSONCaching(var context: Context) {

    /*checks for file in cache exists or not*/
    fun fileExists(
        context: Context,
        filename: String?
    ): Boolean {
        val file = context.getFileStreamPath(filename)
        return !(file == null || !file.exists())
    }

    /*Reads File from cache*/
    fun readJSONFromFile(): JSONArray {
        var jarray = JSONArray()
        val data: String
        if(fileExists(context, cacherFileName)) {
            try {

                val inputStream: InputStream? = context.openFileInput(cacherFileName)

                if (inputStream != null) {
                    val isr = InputStreamReader(inputStream)
                    val br = BufferedReader(isr)
                    var receivedString: String? = ""
                    val stringBuilder = StringBuilder()
                    while (br.readLine().also { receivedString = it } != null) {
                        stringBuilder.append(receivedString)
                    }
                    inputStream.close()
                    data = stringBuilder.toString()
                    Log.d("readData", data)
                    jarray = JSONArray(data)
                    //return jarray;
                } else {
                    Log.e("Error", "No data or file found")
                }

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Log.e("login activity", "File not found: $e")
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("login activity", "Can not read file: $e")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        else
        {
            Log.e("login activity222", "File not found:")
        }
        return jarray
    }

    /*writes data in file in cache*/
    fun writeAppendToJSONFile(jo: JSONObject?) {
        val jarray = readJSONFromFile()
        jarray.put(jo)
        val data = jarray.toString()
        try {
            val osw = OutputStreamWriter(
                context.openFileOutput(
                    cacherFileName,
                    Context.MODE_PRIVATE
                )
            )
            osw.write(data)
            osw.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: $e")
        }
        Log.e("cacherfilecontent", data)
    }

    companion object {
        var cacherFileName = "sentMessageText.txt"
    }

}