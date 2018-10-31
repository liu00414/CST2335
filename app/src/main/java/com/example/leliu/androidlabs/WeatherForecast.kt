package com.example.leliu.androidlabs

import android.app.Activity
import android.content.Context
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
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.net.MalformedURLException


class WeatherForecast : Activity() {

    var progressBar:ProgressBar?=null
    var maxTemp:TextView?=null
    var minTemp:TextView?=null
    var currentTemp:TextView?=null
    lateinit var windSpeed:TextView
    lateinit var image:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        progressBar=findViewById<ProgressBar>(R.id.progressBar)
        maxTemp=findViewById<TextView>(R.id.maxTemp)
        minTemp=findViewById<TextView>(R.id.minTemp)
        currentTemp=findViewById<TextView>(R.id.currentTemp)
        windSpeed=findViewById<TextView>(R.id.windSpeed)

        image=findViewById<ImageView>(R.id.currentWeather)
        progressBar?.visibility= View.VISIBLE

        var myQuery = ForecastQuery()
        myQuery.execute() //runs the thread
    }

    inner class ForecastQuery : AsyncTask<String, Integer, String>()
    {
        var speed:String? = null
        var current:String?=null
        var min:String?=null
        var max:String?=null
        var icon:String?=null
        lateinit var iconUrl:String
        var progress=0
        lateinit var imgBitmap:Bitmap
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
                            speed=xpp.getAttributeValue(null,"value")
                            progress+=20

                        }
                        else if(xpp.name.equals("weather")){
                            icon = xpp.getAttributeValue(null, "icon")
                            if(fileExistance("$icon.png")){
                                var fis: FileInputStream? = null
                                try {    fis = openFileInput("$icon.png")   }
                                catch (e: FileNotFoundException) {    e.printStackTrace()  }
                                imgBitmap = BitmapFactory.decodeStream(fis)

                            }else {

                                iconUrl = "http://api.openweathermap.org/img/w/$icon.png"
                                imgBitmap = getImage(iconUrl)!!
                                val outputStream = openFileOutput("$icon.png", Context.MODE_PRIVATE);
                                imgBitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                outputStream.flush()
                                outputStream.close()
                            }


                            progress+=20
                        }
                        else if (xpp.name.equals("temperature")){
                            current = xpp.getAttributeValue(null,"value")
                            min = xpp.getAttributeValue(null,"min")
                            max = xpp.getAttributeValue(null,"max")
                            progress+=60
                        }
                        publishProgress()//causes android to call onProgressUpdate
                    }
                    XmlPullParser.TEXT->{}
                }
                xpp.next() // goes to next xml element
            }

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

        override fun onPostExecute(result: String?) {
            image.setImageBitmap(imgBitmap)
            progressBar?.visibility= View.INVISIBLE
        }
        override fun onProgressUpdate(vararg values: Integer?) {//update GUI
            //set text field
            maxTemp?.setText("Max Temp = $max")
            minTemp?.setText("Min Temp = $min")
            currentTemp?.setText("Current Temp = $current")
            windSpeed.text="Wind Speed = $speed"
            progressBar?.setProgress(progress)
        }

        fun getImage(url: URL): Bitmap? {
            var connection: HttpURLConnection? = null
            try {
                connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val responseCode = connection.responseCode
                return if (responseCode == 200) {
                    BitmapFactory.decodeStream(connection.inputStream)
                } else
                    return null
            } catch (e: Exception) {
                return null
            } finally {
                connection?.disconnect()
            }
        }

        fun getImage(urlString: String): Bitmap? {
            try {
                val url = URL(urlString)
                return getImage(url)
            } catch (e: MalformedURLException) {
                return null
            }

        }
        fun fileExistance(fname : String):Boolean{
            val file = getBaseContext().getFileStreamPath(fname)
            return file.exists()   }

    }
}
