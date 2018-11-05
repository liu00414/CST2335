package com.example.leliu.androidlabs


import android.os.Bundle
import android.app.Fragment
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MessageFragment : Fragment() {
    var parentDocument:Context?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var dataPassed=arguments //get your data back
        var string = dataPassed.getString("Message")
        var id = dataPassed.getLong("ID")
        var screen = inflater.inflate(R.layout.fragment_message, container, false)
        var textView=screen.findViewById<TextView>(R.id.fg_msg_id)
        textView.setText(string)
        return screen
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parentDocument=context //need parent for later removing fragment
    }

}
