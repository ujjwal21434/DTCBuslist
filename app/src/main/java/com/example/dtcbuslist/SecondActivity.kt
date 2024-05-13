package com.example.dtcbuslist

import DatabaseHelper
import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class SecondActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val listView = findViewById<ListView>(R.id.listView)
        val route = intent.getStringExtra("route")

        val databaseHelper = DatabaseHelper(this)
        val db = databaseHelper.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT stop_name FROM bus_routes WHERE route = ?", arrayOf(route))
        val stopNames = ArrayList<String>()
        while (cursor.moveToNext()) {
            stopNames.add(cursor.getString(0))
        }
        cursor.close()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stopNames)
        listView.adapter = adapter
    }
}
