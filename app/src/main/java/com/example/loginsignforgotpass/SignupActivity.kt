package com.example.loginsignforgotpass

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        db = DatabaseHelper(this)

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val securityAnswerInput = findViewById<EditText>(R.id.securityAnswerInput)

        findViewById<Button>(R.id.signupButton).setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val securityAnswerText = securityAnswerInput.text.toString().trim()

            // Validate that the security answer is a number
            val securityAnswer = securityAnswerText.toIntOrNull()
            if (securityAnswer == null) {
                Toast.makeText(this, "Security answer must be a number.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Optional: Validate length if required (uncomment if needed)
            // if (securityAnswerText.length != 10) {
            //     Toast.makeText(this, "Security answer must be a 10-digit number.", Toast.LENGTH_SHORT).show()
            //     return@setOnClickListener
            // }

            // Attempt to insert user into database
            if (db.insertUser(username, password, securityAnswer)) { // Pass the Integer directly
                Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show()
                // Optionally navigate to login or other screen
                // startActivity(Intent(this, LoginActivity::class.java)) // Uncomment to navigate
                finish() // Close SignupActivity
            } else {
                Toast.makeText(this, "Signup failed. Username may already exist.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
