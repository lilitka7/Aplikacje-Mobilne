package com.example.game

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.game.sql.DBHelper

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val dbHelper = DBHelper(this)

        val usernameField = findViewById<EditText>(R.id.username)
        val passwordField = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login)
        val registerButton = findViewById<Button>(R.id.register)
        val scoreboard_button = findViewById<Button>(R.id.scoreboard_button2)

        loginButton.setOnClickListener {
            Thread() {
                run {
                    Thread.sleep(100)
                }
                runOnUiThread() {
                    val nick = usernameField.text.toString()
                    val pass = passwordField.text.toString()

                    val regex = Regex("^\\s*$")
                    if(regex matches nick || regex matches pass) {
                        Toast.makeText(applicationContext, "Ups", Toast.LENGTH_SHORT).show()
                        return@runOnUiThread
                    }

                    if(!dbHelper.userExists(nick, pass)) {
                        Toast.makeText(applicationContext, "Nie zgadza się!", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("user", nick)
                        startActivity(intent)
                        this.onPause()
                    }
                }
            }.start()
        }

        registerButton.setOnClickListener {
            Thread() {
                run {
                    Thread.sleep(100)
                }
                runOnUiThread() {
                    val nick = usernameField.text.toString()
                    val pass = passwordField.text.toString()
                    val regex = Regex("^\\s*$")
                    if(regex matches nick || regex matches pass) {
                        Toast.makeText(applicationContext, "Hmmmm.", Toast.LENGTH_SHORT).show()
                        return@runOnUiThread
                    }
                    if(pass.length < 6) {
                        Toast.makeText(applicationContext, "Krótkie hasła są niedozwolone!", Toast.LENGTH_SHORT).show()
                        return@runOnUiThread
                    }
                    val result = dbHelper.addUser(nick, pass)

                    if(result < 1L) {
                        Toast.makeText(applicationContext, "!", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("user", nick)
                        startActivity(intent)
                        this.onPause()
                    }
                }
            }.start()
        }

        scoreboard_button.setOnClickListener {
            Thread() {
                run {
                    Thread.sleep(100)
                }
                runOnUiThread() {
                    val intent = Intent(this, ScoreboardActivity::class.java)
                    startActivity(intent)
                    this.onPause()
                }
            }.start()
        }
    }
}