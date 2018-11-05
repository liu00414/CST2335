package com.example.leliu.androidlabs
//cd Library/Android/sdk/platform-tools/
//./adb shell
//cd /data/data/com.example.leliu.androidlabs
//cd shared_prefs
//cat SavedData.xml
//rm SavedData.xml



import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import android.widget.*
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

class ChatWindowActivity : Activity() {
    val ACTIVITY_NAME="ChatWindow"

    var chatList=ArrayList<String>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_window2)

        var fragmentLocation = findViewById<FrameLayout>(R.id.fragment_location)
        var iAmTablet = fragmentLocation!=null //very important




        val dbHelper=ChatDatabaseHelper()
        val db= dbHelper.writableDatabase
        val results= db.query(TABLE_NAME, arrayOf("_id",KEY_MESSAGES), null,null,null,null,null,null)
        val numberRows=results.getCount()

        results.moveToFirst()//point to first row ro read
        val idIndex= results.getColumnIndex("_id")
        val msgIndex=results.getColumnIndex(KEY_MESSAGES)
        while (!results.isAfterLast()){
            var thisId = results.getInt(idIndex)
            var thisMsg=results.getString(msgIndex)
            Log.i(ACTIVITY_NAME,"SQL MESSAGE:" + results.getString(msgIndex))
            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + results.getColumnCount())
            var columnIndex=results.getColumnCount()-1
            while (columnIndex>=0){
                Log.i(ACTIVITY_NAME, "Column Name: "+results.getColumnName(columnIndex))
                columnIndex -= 1
            }



            chatList.add(thisMsg)

            results.moveToNext()//look at next row in the table


        }



        var listView=findViewById<ListView>(R.id.chatView)
        listView.setOnItemClickListener{parent,view,position,id->
            var string=chatList.get(position) //get it from message list
            var dataToPass=Bundle()
            dataToPass.putString("Message",string)
            dataToPass.putLong("ID",id)

            if(iAmTablet){
                //tablet running

                var newFragment=MessageFragment()
                newFragment.arguments=dataToPass//bundle goes to fragment
                var transition=getFragmentManager().beginTransaction()//how to load fragment
                transition.replace(R.id.fragment_location,newFragment)//where to load and what to load
                transition.commit()//make it run

            }else{
                //phone running
            }
        }


        var chatInputText=findViewById<EditText>(R.id.editText5)
        var sendButton=findViewById<Button>(R.id.sendButton)
        Log.i(ACTIVITY_NAME,"In onCreate()")
        var messageAdapter = ChatAdapter( this )



        sendButton?.setOnClickListener(View.OnClickListener{
            Log.i(ACTIVITY_NAME,"CLICK SEND")
            var chatMsg= (chatInputText.getText()).toString()
            chatList.add(chatMsg)

            //write to database
            val newRow=ContentValues()
            newRow.put(KEY_MESSAGES,chatMsg)

            db.insert(TABLE_NAME,"",newRow)




            messageAdapter.notifyDataSetChanged()
            chatInputText.setText("")
        })
//
        listView.setAdapter(messageAdapter);
    }

    inner class ChatAdapter(val ctx : Context):ArrayAdapter<String>(ctx,0){
        override fun getCount():Int{
            return chatList.size
        }
        override fun getView(position : Int, convertView: View?, parent : ViewGroup) : View?{
            var inflater = LayoutInflater.from(parent.getContext())
            var result=convertView

            if(position%2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null)
            } else
            {result = inflater.inflate(R.layout.chat_row_outgoing, null)}

            val message = result.findViewById<TextView>(R.id.message_text)
            message.setText(getItem(position)) // get the string at position
            return result

        }
        override fun getItem(position:Int):String{
            return chatList.get(position)
        }
        override fun getItemId(position:Int):Long{
            return 0
        }
    }

    val VERSION_NUM=3
    val DATABASE_NAME = "Message_db"
    val TABLE_NAME="Messages"
    val KEY_MESSAGES="Messages"
    inner class ChatDatabaseHelper : SQLiteOpenHelper( this@ChatWindowActivity, DATABASE_NAME, null, VERSION_NUM){
        override fun onCreate(db: SQLiteDatabase) {
            Log.i("ChatDatabaseHelper", "Calling onCreate")
            db.execSQL("CREATE TABLE "+TABLE_NAME +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_MESSAGES + " TEXT)") //create table

        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + "newVersion=" + newVersion)
                db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME )//delete table
                //create new one
                onCreate(db)
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




}
