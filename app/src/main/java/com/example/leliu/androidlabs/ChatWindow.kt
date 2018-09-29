package com.example.leliu.androidlabs

import android.app.Activity
import android.os.Bundle
import android.widget.*
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater






class ChatWindow : Activity() {
    val ACTIVITY_NAME="ChatWindow"

    var chatList=ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        var listView=findViewById<ListView>(R.id.chatView)
        var chatInputText=findViewById<EditText>(R.id.editText5)
        var sendButton=findViewById<Button>(R.id.sendButton)
        Log.i(ACTIVITY_NAME,"In onCreate()")
        var messageAdapter = ChatAdapter( this );
        listView.setAdapter(messageAdapter);

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_window)
        sendButton.setOnClickListener({
            var chatMsg = chatInputText.getText().toString()
            chatList.add(chatMsg)
            messageAdapter.notifyDataSetChanged();
            chatInputText.setText("");
        })


    }

    inner class ChatAdapter(ctx : Context):ArrayAdapter<String>(ctx,0){
        override fun getCount():Int{
            return chatList.size
        }
        override fun getItem(position:Int):String{
            return chatList.get(position)
        }
        override fun getView(position : Int, convertView: View, parent : ViewGroup) : View{
            var inflater = LayoutInflater.from(parent.getContext())
            var result : View
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null)
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null)

            val message = result.findViewById<TextView>(R.id.message_text)
            message.setText(getItem(position)) // get the string at position
            return result

        }
         fun getItemId(position:Long):Long{
            return position
        }
    }

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
