package com.cis.project3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.cis.project3.MainActivity.Companion.lat
import com.cis.project3.MainActivity.Companion.lon
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class SecondViewModel : ViewModel() {
    private val API: String = "b56d7cad9b2c9bedf6182692ed365853"
    private var temp: MutableLiveData<String> = MutableLiveData()
    private var date: MutableLiveData<String> = MutableLiveData()
    private var desc: MutableLiveData<String> = MutableLiveData()
    private var icon: MutableLiveData<String> = MutableLiveData()
    private var list: MutableLiveData<ArrayList<Days>> = MutableLiveData()
    private var listItems: ArrayList<Days> = ArrayList()

    fun getList(): MutableLiveData<ArrayList<Days>> {
        return list
    }

    fun getTemp():MutableLiveData<String>{
        return temp
    }
    fun getDate():MutableLiveData<String>{
        return date
    }
    fun getDesc():MutableLiveData<String>{
        return desc
    }
    fun getIcon():MutableLiveData<String>{
        return icon
    }
    fun currentWeather(queue: RequestQueue, city:String) {
        val url =
            "https://api.openweathermap.org/data/2.5/weather?q=$city&units=imperial&appid=$API"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // create JSONObject
                val obj = JSONObject(response)
                var coord = obj.getJSONObject("coord")
                lon = coord.getDouble("lon")
                lat = coord.getDouble("lat")
                //addItems(queue, lon, lat)
                val main = obj.getJSONObject("main") //conected to the main object to retrieve
                var t = main.getDouble("temp") //get doube value
                temp.setValue("%.0f°F".format(t))
                val toDate = obj.getLong("dt") //retrieves date, long variable
                date.setValue(
                    SimpleDateFormat("EEE, MMMM dd hh:mm a", Locale.ENGLISH).format(
                        Date(
                            toDate * 1000
                        )
                    )
                )
                val weather = obj.getJSONArray("weather").getJSONObject(0) //first in the array is 0
                desc.setValue(weather.getString("main")) //description
                val iconUrl =
                    "https://openweathermap.org/img/w/${weather.getString("icon")}.png"; //this will change the object
                icon.setValue(iconUrl)

            },
            Response.ErrorListener { temp.value = "0°F" }) //if error


        fun addItems(queue: RequestQueue, lon:Double, lat:Double) {
                listItems.clear()
                val url ="https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&exclude=minutely,hourly,alert&units=imperial&appid=$API"
                val stringRequest =
                    StringRequest(
                        Request.Method.GET, url, Response.Listener<String> { response: String? ->
                            // create JSONObject
                            val obj = JSONObject(response)
                            var wlist= obj.getJSONArray("daily")
                            for(i in 0..7) {
                                // get current weather information of a city
                                var temp = wlist.getJSONObject(i).getJSONObject("temp")
                                var daytemp= ("%.0f° F".format(temp.getDouble("day")))
                                val toDate = wlist.getJSONObject(i).getLong("dt")
                                var date=(SimpleDateFormat("EEE, MMMM dd", Locale.ENGLISH).format(Date(toDate * 1000)))
                                var icon= wlist.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon")
                                var iconURL = "https://api.openweathermap.org/img/w/$icon.png"
                                listItems.add(Days(daytemp,date,iconURL))
                            }
                            list.setValue(listItems)
                        },
                        Response.ErrorListener {  "0° F" })
                queue.add(stringRequest)
            }



// Add the request to the RequestQueue.
        queue.add(stringRequest)

    }

}