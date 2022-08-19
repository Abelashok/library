package com.example.library

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.concurrent.ThreadLocalRandom

class DataParser(type: String?, coordinates:MutableList<String>) {

    var dataFormatType: String? = type
    var coordinates: MutableList<String> = coordinates


    init{

        val messageToPublish: String = if (dataFormatType == "json") {
            coordinatesToSendjson()
        } else {
            coordinatesToSendcsv()
        }
        Log.d("abel12", messageToPublish)

    }

    fun coordinatesToSendjson(): String {
        var result = ""
        val jsonarr = JSONArray()
        coordinates.forEach {
            if (it.startsWith("v") && !it.startsWith("vt") && !it.startsWith("vn")) {
                val split: List<String> = it.split(" ")

                var data2 = split[1]
                var data3 = split[2]
                var data4 = split[3]

                val jsonObject = JSONObject()
                val xPos = data2
                val yPos = data3
                val zPos = data4
                try {
                    jsonObject.put("Shape", "Sphere")
                    jsonObject.put("x_pos", xPos)
                    jsonObject.put("y_pos", yPos)
                    jsonObject.put("z_pos", zPos)
                    jsonObject.put("rgba", "FF0000FF")
                    jsonObject.put("sensor", "Sensor")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                jsonarr.put(jsonObject)
            }

        }
        result = jsonarr.toString()
        return result
    }

    fun coordinatesToSendcsv(): String {

        var  strData1: String = ""
        coordinates.forEach {
            if (it.startsWith("v") && !it.startsWith("vt") && !it.startsWith("vn")) {
                val split: List<String> = it.split(" ")

                var data2 = split[1]
                var data3 = split[2]
                var data4 = split[3]

                if (strData1.equals("")) {
                    strData1 = "$data2,$data3,$data4"
                } else {
                    strData1 = "$strData1,$data2,$data3,$data4"
                }

            }

        }
        Log.d("abel1",strData1)

        return strData1
    }


}