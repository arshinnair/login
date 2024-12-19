package com.example.loginsignforgotpass

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

    class LoginActivity : AppCompatActivity() {
        lateinit var db: DatabaseHelper

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)

            db = DatabaseHelper(this)

            val usernameInput = findViewById<EditText>(R.id.usernameInput)
            val passwordInput = findViewById<EditText>(R.id.passwordInput)

            findViewById<Button>(R.id.loginButton).setOnClickListener {
                val username = usernameInput.text.toString()
                val password = passwordInput.text.toString()

                if (db.checkUser(username, password)) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Invalid credentials.", Toast.LENGTH_SHORT).show()
                }
            }

            // Set up the button click listener
            findViewById<Button>(R.id.signupButton).setOnClickListener {
                startActivity(Intent(this, SignupActivity::class.java))

                findViewById<Button>(R.id.forgotPasswordButton).setOnClickListener {
                    startActivity(Intent(this, ForgotPasswordActivity::class.java))
                }
            }
        }
    }