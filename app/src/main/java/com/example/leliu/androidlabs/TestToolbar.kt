package com.example.leliu.androidlabs

import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
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


class TestToolbar : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var currentMessage="You select Item 1"
    lateinit var snackbarButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_toolbar)
        val lab8_toolbar = findViewById<Toolbar>(R.id.lab8_toolbar)
        setSupportActionBar(lab8_toolbar)


        //add nav to toolbar
        var drawer=findViewById<DrawerLayout>(R.id.drawer_layout)
        var toogle=ActionBarDrawerToggle(this,drawer,lab8_toolbar,R.string.open,R.string.close)
        drawer.addDrawerListener(toogle)
        toogle.syncState()
        var navView=findViewById<NavigationView>(R.id.navigation_view)
        navView.setNavigationItemSelectedListener(this)


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




    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.chat_item->{
                //system notification
                var mBuilder = NotificationCompat.Builder(this, "Channel_name")
                        .setSmallIcon(R.drawable.toolbaricon1).setAutoCancel(true)
                        .setContentTitle("Chat").setContentText("Chat with me!")
                var resultIntent = Intent(this, ChatWindowActivity::class.java)

                var resultPendingIntent = PendingIntent.getActivity( this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                mBuilder.setContentIntent(resultPendingIntent)

                var mNotificationId=1
                var mNotifyMgr=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                mNotifyMgr.notify(mNotificationId,mBuilder.build())
            }
            R.id.list_item->{
                //system notification
                var mBuilder = NotificationCompat.Builder(this, "Channel_name")
                        .setSmallIcon(R.drawable.toolbaricon2).setAutoCancel(true)
                        .setContentTitle("List").setContentText("Open List Items page!")
                var resultIntent = Intent(this, ListItemsActivity::class.java)

                var resultPendingIntent = PendingIntent.getActivity( this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                mBuilder.setContentIntent(resultPendingIntent)

                var mNotificationId=2
                var mNotifyMgr=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                mNotifyMgr.notify(mNotificationId,mBuilder.build())
            }
            R.id.contact_item->{
                //system notification
                var mBuilder = NotificationCompat.Builder(this, "Channel_name")
                        .setSmallIcon(R.drawable.toolbaricon2).setAutoCancel(true)
                        .setContentTitle("How to contact?").setContentText("Choose one!")
                //email
                var emailIntent = Intent(Intent.ACTION_SENDTO)
                var resultPendingIntent = PendingIntent.getActivity( this, 0, emailIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                mBuilder.addAction(R.drawable.toolbaricon3, "Send Email",resultPendingIntent)
                //sms
                var smsIntent = Intent(Intent.ACTION_SEND)
                var smsPendingIntent = PendingIntent.getActivity( this, 0, smsIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                mBuilder.addAction(R.drawable.toolbaricon2, "Send SMS",smsPendingIntent)
                //mBuilder.setContentIntent(resultPendingIntent)


                var mNotificationId=3
                var mNotifyMgr=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                mNotifyMgr.notify(mNotificationId,mBuilder.build())
            }
        }
        var drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
