package com.example.dtcbuslist

import DatabaseHelper
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView
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

        val cursor = db.rawQuery("SELECT stop_name, lat, long FROM bus_routes WHERE route = ?", arrayOf(route))
        val stopNames = ArrayList<String>()
        val latitudes = ArrayList<Double>()
        val longitudes = ArrayList<Double>()

        while (cursor.moveToNext()) {
            stopNames.add(cursor.getString(0))
            latitudes.add(cursor.getDouble(1))
            longitudes.add(cursor.getDouble(2))
        }
        cursor.close()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stopNames)
        listView.adapter = adapter


        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val latitude = latitudes[position]
            val longitude = longitudes[position]
            openGoogleMaps(latitude, longitude)
        }
    }

    private fun openGoogleMaps(latitude: Double, longitude: Double) {
        try {
            val uri = Uri.parse("https://www.google.com/maps/place/$latitude,$longitude")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(mapIntent)
        } catch (e: ActivityNotFoundException) {
            val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            mapIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(mapIntent)
        }
    }
}
