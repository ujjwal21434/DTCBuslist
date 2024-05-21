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

/**
 * A class representing a bus stop with a name, latitude, and longitude.
 */
class Stop(val name: String, val lat: Double, val lon: Double)

/**
 * A custom RecyclerView adapter for displaying a list of [Stop] objects.
 * 
 * @property context The context in which the adapter is being used.
 * @property stopList The list of stops to display.
 */
class StopAdapter(private val context: Context, private val stopList: List<Stop>) :
    RecyclerView.Adapter<StopAdapter.StopViewHolder>() {

    /**
     * A ViewHolder for the RecyclerView, which stores references to the views for each item.
     * 
     * @property itemView The root view of the item layout.
     * @property stopName The TextView displaying the name of the stop.
     */
    class StopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stopName: TextView = itemView.findViewById(R.id.stop_name)
    }

    /**
     * Creates a new ViewHolder for the RecyclerView.
     * 
     * @param parent The ViewGroup in which the new View will be created.
     * @param viewType The type of view to be created.
     * @return A new StopViewHolder instance.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.stop_item, parent, false)
        return StopViewHolder(view)
    }

    /**
     * Binds data to an existing ViewHolder.
     * 
     * @param holder The ViewHolder to bind the data to.
     * @param position The position of the item in the adapter's data set.
     */
    override fun onBindViewHolder(holder: StopViewHolder, position: Int) {
        holder.stopName.text = stopList[position].name // Set the stop name TextView to the name of the stop.
        holder.itemView.setOnClickListener { openGoogleMaps(stopList[position].lat, stopList[position].lon) }
        // Set a click listener on the item view that opens Google Maps to the stop's location.
    }

    /**
     * Returns the number of items in the adapter's data set.
     * 
     * @return The size of the stopList.
     */
    override fun getItemCount(): Int = stopList.size // Return the size of the stopList.
}

/**
 * A private function that opens Google Maps to the specified latitude and longitude.
 * 
 * @param latitude The latitude to open Google Maps to.
 * @param longitude The longitude to open Google Maps to.
 */
private fun openGoogleMaps(latitude: Double, longitude: Double) {
    // ... (same as original)
}

/**
 * A subclass of AppCompatActivity that displays a list of stops for a given route.
 */
class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val route = intent.getStringExtra("route") // Get the route extra from the intent.
        if (route == null) { // If the route extra is null, finish the activity.
            finish()
            return
        }

        val databaseHelper = DatabaseHelper(this) // Create a new DatabaseHelper instance.
        val db = databaseHelper.readableDatabase // Get a readable database instance.

        val cursor = db.rawQuery("SELECT stop_name, lat, long FROM bus_routes WHERE route = ?", arrayOf(route)) // Query the bus_routes table for stops on the given route.
        val stopList = mutableListOf<Stop>() // Create a new mutable list of Stop objects.

        while (cursor.moveToNext()) { // Iterate over the cursor.
            stopList.add(Stop(cursor.getString(0), cursor.getDouble(1), cursor.getDouble(2))) // Add a new Stop object to the list.
        }
        cursor.close() // Close the cursor.

        val recyclerView = findViewById<RecyclerView>(R.id.stop_recycler_view) // Find the RecyclerView in the layout.
        recyclerView.adapter = StopAdapter(this, stopList) // Set the adapter for the RecyclerView.
        recyclerView.layoutManager = LinearLayoutManager(this) // Set the layout manager for the RecyclerView.
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
        <!-- The TextView that displays the name of the stop. -->

</LinearLayout>
