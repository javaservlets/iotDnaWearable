package com.forgerock.iotdna

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp

import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.FirebaseDatabase
//import jdk.nashorn.internal.objects.NativeDate.getTime
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import com.forgerock.iotdna.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var editText: EditText = findViewById(R.id.usr) as EditText

        fab.setOnClickListener { view ->
            val usr = (editText.text.toString()) /* value can b updated via UI ie USER <> 'DEMO' */

            // DO NOT TEST ON EMULATOR!!! SINCE IT WONT REACH OUT TO FIREBASE

            FirebaseApp.initializeApp(this);
            val database = FirebaseDatabase.getInstance()// Write a message to the database
            val topic_name = database.getReference(usr) // remember this is really just firebaseurl + "/" + (usr).json

            topic_name.setValue(Build.SERIAL + " ^ " + getTime())   // update the file
            Snackbar.make(view, "iotDna challenge sent " + usr + "@" + Build.SERIAL, Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
    }

    private fun getTime(): String? { // we'll write a timestamp along with the hex value read
        var date: Date? = null
        var formattedDate: String? = null
        try {
            val stamp = Timestamp(System.currentTimeMillis())
            date = Date(stamp.time)
            val sdf = SimpleDateFormat("MM/dd/yyyy h:mm:ss a")
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            formattedDate = sdf.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return formattedDate
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

}
