package com.example.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var count: Int = 0
    private var score: Int = 0
    private var myRandom:Int = 0

    private fun getRandom(){
        myRandom = Random.nextInt(0, 20)
    }

    private fun start(){
        getRandom()
        count = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        score = getPoints()
        val enterNumber = findViewById<EditText>(R.id.enterNumber)
        val mainBtn = findViewById<Button>(R.id.main_btn)
        val restart = findViewById<Button>(R.id.restart)
        val suma = findViewById<Button>(R.id.suma)
        val countMy = findViewById<TextView>(R.id.count_my)
        val scoreMy = findViewById<TextView>(R.id.score_my)

        scoreMy.text = score.toString()
        countMy.text = count.toString()

        start()

        mainBtn.setOnClickListener{
            if (enterNumber.text.isEmpty()) {
                enterNumber.text.clear()
                Toast.makeText(this, "Wpisz liczbę", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (enterNumber.text.length > 2) {
                enterNumber.text.clear()
                Toast.makeText(this, "Niedozwolony zakres", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (count == 10) {
                start()
                countMy.text = count.toString()
                showAlert("Przegrałeś","Message")
                return@setOnClickListener
            }
            when {
                enterNumber.text.toString().toInt() !in (0..20) -> {
                    Toast.makeText(this, "Twoja liczba jest poza zakresem", Toast.LENGTH_LONG).show()
                }
                enterNumber.text.toString().toInt() < myRandom -> {
                    count += 1
                    countMy.text = count.toString()
                    Toast.makeText(this, "Twoja liczba jest mniejsza", Toast.LENGTH_LONG).show()
                }
                enterNumber.text.toString().toInt() > myRandom -> {
                    count += 1
                    countMy.text = count.toString()
                    Toast.makeText(this, "Twoja liczba jest większa", Toast.LENGTH_LONG).show()
                }
                else -> {
                        count += 1
                        countMy.text = count.toString()
                        showAlert("Wygrałeś za $count razem.", "Gratulacje")
                    when (count) {
                        1 -> score += 5
                        in (2..4) -> score += 3
                        in (5..6) -> score += 2
                        in (7..10) -> score += 1
                    }
                        setPoints(score)
                        scoreMy.text = score.toString()
                        start()
                        countMy.text = count.toString()
                }
            }
        }

        restart.setOnClickListener{
            start()
            countMy.text =count.toString()
        }

        suma.setOnClickListener{
            setPoints(0)
            score = 0
            scoreMy.text = score.toString()
        }
    }
    private fun showAlert(message : String, title : String) {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK"){ _, _ ->}
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun setPoints(score : Int) {
        val sharedScore = this.getSharedPreferences("com.example.game.shared",0)
        val edit = sharedScore.edit()
        edit.putInt("score", score)
        edit.apply()
    }

    private fun getPoints() : Int {
        val sharedScore = this.getSharedPreferences("com.example.game.shared",0)
        return sharedScore.getInt("score", 0)
    }
}
