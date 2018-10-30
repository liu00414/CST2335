package com.example.leliu.androidlabs

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.HttpURLConnection
import java.net.URL

class WeatherForecast : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        var progressBar=findViewById<ProgressBar>(R.id.progressBar)
        var maxTemp=findViewById<TextView>(R.id.maxTemp)
        var minTemp=findViewById<TextView>(R.id.minTemp)
        var currentTemp=findViewById<TextView>(R.id.currentTemp)
        var windSpeed=findViewById<TextView>(R.id.windSpeed)

        var img=findViewById<ImageView>(R.id.currentWeather)
        progressBar.visibility= View.VISIBLE

        var myQuery = ForecastQuery()
        myQuery.execute() //runs the thread
    }

    inner class ForecastQuery : AsyncTask<String, Integer, String>()
    {
        override fun doInBackground(vararg params: String?): String {
            val url = URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric")
            var connection=url.openConnection() as HttpURLConnection // goes to the server
            var response = connection.inputStream

            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = false
            val xpp = factory.newPullParser()
            xpp.setInput(response, "UTF-8") // read xml from server

            while(xpp.eventType!=XmlPullParser.END_DOCUMENT)
            {
                when(xpp.eventType){
                    XmlPullParser.START_TAG->{
                        if(xpp.name=="speed"){
                            var speed=xpp.getAttributeValue(null,"value")
                            var name=xpp.getAttributeValue(null,"name")
                        }
                    }
                    XmlPullParser.TEXT->{}
                }

            }
            xpp.next() // goes to next xml element
//            return ()?.run {
//                readTimeout = 10000
//                connectTimeout = 15000
//                requestMethod = "GET"
//                doInput = true
//                // Starts the query
//                connect()
//                inputStream
//            }
            return "Done"
        }
    }
}
