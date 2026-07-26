package com.shivam_interactive.mathsgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    lateinit var addition: Button
    lateinit var subtraction: Button
    lateinit var division: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addition=findViewById(R.id.Addition)
        subtraction=findViewById(R.id.Subtraction)
        division=findViewById(R.id.Division)


        addition.setOnClickListener {

            var intent= Intent(this@MainActivity, gameactivity::class.java)
            startActivity(intent)

        }
        subtraction.setOnClickListener {

            var intent= Intent(this@MainActivity, subtractActivity::class.java)
            startActivity(intent)

        }
        division.setOnClickListener {

            var intent= Intent(this@MainActivity, multiply::class.java)
            startActivity(intent)

        }
    }
}