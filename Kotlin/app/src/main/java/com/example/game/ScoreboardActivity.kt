package com.example.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.game.sql.DBHelper

class ScoreboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_board)

        val returnButton = findViewById<Button>(R.id.return_button)
        val scoreboard = findViewById<TextView>(R.id.scoreboard)

        val dbHelper = DBHelper(this)
        val list = dbHelper.getList()
        var displayText = ""

        for(i in 0 until list.size) {
            val index = i + 1
            displayText += "$index. " + list[i] + "\n"
        }

        scoreboard.text = displayText

        returnButton.setOnClickListener {
            finish()
        }
    }
}