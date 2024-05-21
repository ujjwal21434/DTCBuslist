package com.example.dtcbuslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import androidx.appbar.app.MaterialToolbar

/**
 * The main activity of the DTC Bus List app.
 * Displays a toolbar and two buttons that allow the user to navigate to different activities.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Called when the activity is first created. Initializes the activity and sets up click listeners
     * for the two buttons.
     *
     * @param savedInstanceState The saved instance state of the activity, if available.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Inflates the layout for the activity

        setSupportActionBar(findViewById(R.id.topAppBar)) // Sets the toolbar as the app bar

        // Sets up a click listener for the first button that starts the FirstActivity
        findViewById<Button>(R.id.button1).setOnClickListener {
            startActivity(Intent(this, FirstActivity::class.java))
        }

        // Sets up a click listener for the second button that starts the LocationActivity
        findViewById<Button>(R.id.button2).setOnClickListener {
            startActivity(Intent(this, LocationActivity::class.java))
        }
    }
}
