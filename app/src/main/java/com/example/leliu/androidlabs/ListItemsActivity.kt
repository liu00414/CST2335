package com.example.leliu.androidlabs

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list_items.*
import android.widget.CompoundButton



class ListItemsActivity : Activity() {
    val ACTIVITY_NAME="ListItemActivity"
    val REQUEST_IMAGE_CAPTURE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_items)
        Log.i(ACTIVITY_NAME,"In onCreate()")

        var imageButton = findViewById<ImageButton>(R.id.imageButton)
        imageButton?.setOnClickListener(View.OnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }

        })

        var switchButton = findViewById<Switch>(R.id.switch2)
        switchButton.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                val text = "Switch is On"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(this , text, duration)
                        toast.show()
            } else {
                val text = "Switch is Off" //
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(this , text, duration)
                        toast.show()
            }
        }

    }

    //This gets called after onCreate()

    override fun onStart()
    {
        super.onStart()
        Log.i(ACTIVITY_NAME, "In onStart()")
    }


    //This gets called after onStart()

    override fun onResume()
    {
        super.onResume()
        Log.i(ACTIVITY_NAME, "In OnResume()")

    }

    override fun onPause() {
        super.onPause()
        Log.i(ACTIVITY_NAME,"In onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(ACTIVITY_NAME,"In onStop()")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i(ACTIVITY_NAME,"In onDestroy()")
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data.extras.get("data") as Bitmap
            imageButton.setImageBitmap(imageBitmap)
        }
    }
}
