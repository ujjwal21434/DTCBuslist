package com.example.dtcbuslist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

/**
 * This is the first activity of the DTC Bus List app. It allows the user to enter a route number
 * and then proceeds to the second activity with the entered route number.
 */
class FirstActivity : AppCompatActivity() {

    /**
     * Called when the activity is first created. This is where we initialize our views and set up
     * the functionality of the button.
     *
     * @param savedInstanceState The saved instance state, if any.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        /**
         * The EditText view where the user enters the route number.
         */
        val editText = findViewById<EditText>(R.id.editText)

        /**
         * The Button view that the user clicks to proceed to the second activity with the entered
         * route number.
         */
        val button = findViewById<Button>(R.id.button)

        /**
         * Sets up a click listener for the button. When the button is clicked, the route number
         * is retrieved from the EditText view and the second activity is started with the route
         * number as an extra. If the EditText view is empty, an error message is displayed.
         */
        button.setOnClickListener {
            val route = editText.text.toString().trim()

            // Check if the EditText view is empty
            if (route.isNotEmpty()) {
                // Start the second activity with the route number as an extra
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("route", route)
                startActivity(intent)
            } else {
                // Show error message to user
                editText.error = "Please enter a route number"
            }
        }
    }
}
