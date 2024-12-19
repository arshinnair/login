package com.example.loginsignforgotpass

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        db = DatabaseHelper(this)

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val securityAnswerInput = findViewById<EditText>(R.id.securityAnswerInput)
        val newPasswordInput = findViewById<EditText>(R.id.newPasswordInput)

        findViewById<Button>(R.id.resetPasswordButton).setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val securityAnswerText = securityAnswerInput.text.toString().trim()
            val newPassword = newPasswordInput.text.toString().trim()

            // Check for empty fields
            if (username.isEmpty() || securityAnswerText.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "All fields must be filled.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convert securityAnswerText to Int and check if it's a valid number
            val securityAnswer = securityAnswerText.toIntOrNull()
            if (securityAnswer == null) {
                Toast.makeText(this, "Security answer must be a valid number.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Retrieve stored security answer from the database
            val storedAnswer = db.getUserSecurityAnswer(username)
            if (storedAnswer != null) {
                if (storedAnswer == securityAnswer) {
                    if (db.updateUserPassword(username, newPassword)) {
                        Toast.makeText(this, "Password reset successful!", Toast.LENGTH_SHORT).show()
                        // Optionally navigate to the login screen
                        finish() // Close ForgotPasswordActivity
                    } else {
                        Toast.makeText(this, "Failed to reset password.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Incorrect security answer.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Username not found.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
