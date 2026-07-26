package com.shivam_interactive.mathsgame

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
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

class subtractActivity : AppCompatActivity() {

    lateinit var score: TextView
    lateinit var live: TextView
    lateinit var time: TextView

    lateinit var error: TextView
    lateinit var display: TextView
    lateinit var answer: EditText

    lateinit var okbtn: Button
    lateinit var nextbtn: Button

    var correctanswer=0
    var lives=3;
    var scorecount=0;
    lateinit var timer: CountDownTimer
    private val starttimerinm:Long=60000
    var timeinm:Long=starttimerinm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gameactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        score=findViewById(R.id.score)
        live=findViewById(R.id.live)
        time=findViewById(R.id.time)

        error=findViewById(R.id.error)

        display=findViewById(R.id.display)
        answer=findViewById(R.id.answer)

        okbtn=findViewById(R.id.okbtn)
        nextbtn=findViewById(R.id.nextbtn)


        randomlogic()
        okbtn.setOnClickListener {
            val useranswer=answer.text.toString();
            if(useranswer=="")
            {
                Toast.makeText(this,"Answer the question or go to Next Question", Toast.LENGTH_LONG).show()
            }
            else{

                if(useranswer.toInt()==correctanswer)
                {
                    pausetimer()
                    scorecount=scorecount+1;
                    score.text="Score : ${scorecount.toString()}"
                    error.text="Correct answer"
                }
                else {
                    lives--
                    live.text = "Heart : ${lives.toString()}"
                    Toast.makeText(this,"You have only ${lives.toString()} Hearts left",Toast.LENGTH_LONG).show()
                    if(lives.toInt()==0)
                    {
                        showalertDialog()
                    }
                }
            }

        }
        nextbtn.setOnClickListener {
            pausetimer()
            resettimer()
            randomlogic()
            answer.setText("")
            error.setText("")
            if(live.text.toString()=="0")
            {
                showalertDialog()
            }
        }
    }


    fun randomlogic(){
        val num1= Random.nextInt(0,100)
        val num2= Random.nextInt(0,100)

        display.text="$num1 - $num2";
        correctanswer=num1-num2;
        timelogic()
    }
    fun showalertDialog(){
        var alertdialog= AlertDialog.Builder(this@subtractActivity)

        alertdialog.setTitle("Game Over")
            .setMessage("Score : ${scorecount.toString()}")
            .setCancelable(true)
            .setPositiveButton("OK", DialogInterface.OnClickListener{ dialoginterface, Result->
                var intant= Intent(this@subtractActivity, MainActivity::class.java)
                startActivity(intant)
                finish()
            })

        alertdialog.create().show()
    }

    fun timelogic(){
        timer =object :CountDownTimer(timeinm,1000) {
            override fun onFinish() {
                lives--
                live.text="Live : ${lives.toString()}"
                error.text="Time finish to continue game press next 1 Heart gone"


                pausetimer()
                updatetext()

            }

            override fun onTick(p0: Long) {
                timeinm=p0
                updatetext()
            }
        }.start()
    }
    fun updatetext()
    {
        var remaingtime:Int= (timeinm/1000).toInt()
        time.text= String.format(Locale.getDefault(),"%02d",remaingtime)
    }
    fun pausetimer(){
        timer.cancel()
    }
    fun resettimer(){
        timeinm=starttimerinm
        updatetext()
    }

}