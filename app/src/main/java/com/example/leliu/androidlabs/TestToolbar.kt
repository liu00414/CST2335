package com.example.leliu.androidlabs

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class TestToolbar : AppCompatActivity() {
    var currentMessage="You select Item 1"
    lateinit var snackbarButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_toolbar)
        val lab8_toolbar = findViewById<Toolbar>(R.id.lab8_toolbar)
        setSupportActionBar(lab8_toolbar)
        snackbarButton = findViewById<Button>(R.id.startSnackBar)
        snackbarButton.setOnClickListener{
            Snackbar.make(snackbarButton,"I'm a snackbar", Snackbar.LENGTH_LONG).setAction("Undo",{e->Toast.makeText(this@TestToolbar,"Undone",Toast.LENGTH_LONG).show()}).show()

        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when(item.itemId){
            R.id.action_one->{Snackbar.make(snackbarButton,currentMessage, Snackbar.LENGTH_LONG).show()}
            R.id.action_two->{
                var builder = AlertDialog.Builder(this)

                builder.setTitle("Do you want to go back?").setPositiveButton(R.string.ok,{dialog,id->
                    finish()

                }).setNegativeButton(R.string.cancel, {dialog,id->

                }).create().show()

            }
            R.id.action_three->{
                var dialogStuff=layoutInflater.inflate(R.layout.dialog_stuff,null)
                var editText=dialogStuff.findViewById<EditText>(R.id.newMessageField)
                var builder = AlertDialog.Builder(this)

                builder.setTitle("Question").setView(dialogStuff).setPositiveButton(R.string.ok,{dialog,id->
                    currentMessage=editText.text.toString()

                }).setNegativeButton(R.string.cancel, {dialog,id->

                }).create().show()

            }
            R.id.action_four->{

                Toast.makeText(this,"Version 1.0, by Le Liu", Toast.LENGTH_LONG).show()
            }

        }
        return true
    }
}
