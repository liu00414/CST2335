package com.example.leliu.androidlabs

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

class LoginActivity : Activity() {
    val ACTIVITY_NAME ="LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.i(ACTIVITY_NAME,"In onCreate()")
        var loginButton = findViewById<Button>(R.id.loginButton)
        val emailAddress = findViewById<EditText>(R.id.emailAddress)
        val prefs = getSharedPreferences("SavedData", Context.MODE_PRIVATE)
        val userString = prefs.getString("UserInput", "email@domain.com")
        Log.e("In MainActivity", "string found is:" + userString)
        emailAddress.setText(userString)
        loginButton.setOnClickListener(View.OnClickListener {
            val newActivity = Intent( this, LoginActivity::class.java)
            val typedString = emailAddress.getText().toString()
            val prefs = prefs.edit()
            prefs.putString("UserInput", typedString)
            prefs.commit()
            var intent = Intent(this, StartActivity::class.java)
            startActivity(intent)

        })
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
}
