package com.example.dtcbuslist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val route = editText.text.toString().trim()
            if (route.isNotEmpty()) {
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
