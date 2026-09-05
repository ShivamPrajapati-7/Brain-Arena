package com.shivam_interactive.mathsgame

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale
import kotlin.random.Random

class MultiplyActivity : AppCompatActivity() {

    private lateinit var score: TextView
    private lateinit var live: TextView
    private lateinit var time: TextView
    private lateinit var display: TextView
    private lateinit var answer: EditText

    private lateinit var okbtn: Button

    private var correctanswer = 0
    private var lives = 3
    private var scorecount = 0

    private lateinit var timer: CountDownTimer
    private val starttimerinm: Long = 20000
    private var timeinm: Long = starttimerinm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gameactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        score = findViewById(R.id.score)
        live = findViewById(R.id.live)
        time = findViewById(R.id.time)

        display = findViewById(R.id.display)
        answer = findViewById(R.id.answer)

        okbtn = findViewById(R.id.okbtn)

        randomlogic()

        okbtn.setOnClickListener {
            val userText = answer.text.toString().trim()
            if (userText.isEmpty()) {
                Toast.makeText(this, "Please answer the question", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userAnswerInt = userText.toIntOrNull()
            if (userAnswerInt == null) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (userAnswerInt == correctanswer) {
                pausetimer()
                scorecount++
                score.text = scorecount.toString()
                Toast.makeText(this@MultiplyActivity, "Correct Answer!", Toast.LENGTH_SHORT).show()
                resettimer()
                randomlogic()
                answer.setText("")
            } else {
                lives--
                live.text = lives.toString()
                Toast.makeText(this, "Wrong Answer! $lives Hearts left", Toast.LENGTH_SHORT).show()
                answer.setText("")
                if (lives <= 0) {
                    pausetimer()
                    showalertDialog()
                }
            }
        }
    }

    private fun randomlogic() {
        val num1 = Random.nextInt(0, 100)
        val num2 = Random.nextInt(0, 10)

        display.text = "$num1 x $num2"
        correctanswer = num1 * num2
        timelogic()
    }

    private fun showalertDialog() {
        val alertdialog = AlertDialog.Builder(this@MultiplyActivity)
        alertdialog.setTitle("Game Over")
            .setMessage("Final Score: $scorecount")
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ ->
                finish()
            }
        alertdialog.create().show()
    }

    private fun timesupalertDialog() {
        val alertdialog = AlertDialog.Builder(this@MultiplyActivity)
        alertdialog.setTitle("Time's Up")
            .setMessage("Hearts Left: $lives")
            .setCancelable(false)
            .setPositiveButton("Retry") { _, _ ->
                if (lives <= 0) {
                    showalertDialog()
                } else {
                    resettimer()
                    randomlogic()
                    answer.setText("")
                }
            }
        alertdialog.create().show()
    }

    private fun timelogic() {
        if (::timer.isInitialized) {
            timer.cancel()
        }
        timer = object : CountDownTimer(timeinm, 1000) {
            override fun onFinish() {
                lives--
                live.text = lives.toString()
                updatetext()
                if (lives <= 0) {
                    showalertDialog()
                } else {
                    timesupalertDialog()
                }
            }

            override fun onTick(p0: Long) {
                timeinm = p0
                updatetext()
            }
        }.start()
    }

    private fun updatetext() {
        val remainingtime: Int = (timeinm / 1000).toInt()
        time.text = String.format(Locale.getDefault(), "%02d", remainingtime)
    }

    private fun pausetimer() {
        if (::timer.isInitialized) {
            timer.cancel()
        }
    }

    private fun resettimer() {
        timeinm = starttimerinm
        updatetext()
    }

    override fun onDestroy() {
        super.onDestroy()
        pausetimer()
    }
}
