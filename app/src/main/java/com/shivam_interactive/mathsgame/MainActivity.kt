package com.shivam_interactive.mathsgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var addition: Button
    private lateinit var subtraction: Button
    private lateinit var multiply: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        addition = findViewById(R.id.Addition)
        subtraction = findViewById(R.id.Subtraction)
        multiply = findViewById(R.id.Multiply)

        addition.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            startActivity(intent)
        }

        subtraction.setOnClickListener {
            val intent = Intent(this@MainActivity, SubtractActivity::class.java)
            startActivity(intent)
        }

        multiply.setOnClickListener {
            val intent = Intent(this@MainActivity, MultiplyActivity::class.java)
            startActivity(intent)
        }
    }
}
