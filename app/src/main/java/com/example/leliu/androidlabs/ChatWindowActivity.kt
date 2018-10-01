package com.example.leliu.androidlabs
//cd Library/Android/sdk/platform-tools/
//./adb shell
//cd /data/data/com.example.leliu.androidlabs
//cd shared_prefs
//cat SavedData.xml
//rm SavedData.xml



import android.app.Activity
import android.os.Bundle
import android.widget.*
import android.content.Context
import android.content.Intent
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

        var listView=findViewById<ListView>(R.id.chatView)
        var chatInputText=findViewById<EditText>(R.id.editText5)
        var sendButton=findViewById<Button>(R.id.sendButton)
        Log.i(ACTIVITY_NAME,"In onCreate()")
        var messageAdapter = ChatAdapter( this )



        sendButton?.setOnClickListener(View.OnClickListener{
            Log.i(ACTIVITY_NAME,"CLICK SEND")
            var chatMsg= (chatInputText.getText()).toString()
            chatList.add(chatMsg)
            messageAdapter.notifyDataSetChanged()
//            //chatInputText.setText("")
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


}
