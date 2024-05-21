package com.example.dtcbuslist

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Stop(val name: String, val lat: Double, val lon: Double)

class StopAdapter(private val context: Context, private val stopList: List<Stop>) :
    RecyclerView.Adapter<StopAdapter.StopViewHolder>() {

    class StopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stopName: TextView = itemView.findViewById(R.id.stop_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.stop_item, parent, false)
        return StopViewHolder(view)
    }

    override fun onBindViewHolder(holder: StopViewHolder, position: Int) {
        holder.stopName.text = stopList[position].name
        holder.itemView.setOnClickListener { openGoogleMaps(stopList[position].lat, stopList[position].lon) }
    }

    override fun getItemCount(): Int = stopList.size
}

private fun openGoogleMaps(latitude: Double, longitude: Double) {
    // ... (same as original)
}

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val route = intent.getStringExtra("route")
        if (route == null) {
            finish()
            return
        }

        val databaseHelper = DatabaseHelper(this)
        val db = databaseHelper.readableDatabase

        val cursor = db.rawQuery("SELECT stop_name, lat, long FROM bus_routes WHERE route = ?", arrayOf(route))
        val stopList = mutableListOf<Stop>()

        while (cursor.moveToNext()) {
            stopList.add(Stop(cursor.getString(0), cursor.getDouble(1), cursor.getDouble(2)))
        }
        cursor.close()

        val recyclerView = findViewById<RecyclerView>(R.id.stop_recycler_view)
        recyclerView.adapter = StopAdapter(this, stopList)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // ... (same as original)
}


<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/stop_name"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="18sp"
        android:textStyle="bold" />

</LinearLayout>
