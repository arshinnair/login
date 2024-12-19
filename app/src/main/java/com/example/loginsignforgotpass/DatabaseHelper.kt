package com.example.loginsignforgotpass

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "UserDatabase.db", null, 1) {

    // Create the users table
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT, securityAnswer INTEGER)")
    }

    // Upgrade the database
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    // Insert a new user into the database
    fun insertUser(username: String, password: String, securityAnswer: Int): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("username", username)
            put("password", password)
            put("securityAnswer", securityAnswer)
        }
        val result = db.insert("users", null, contentValues)
        db.close()  // Close database connection after insert
        return result != -1L
    }

    // Check if a user exists with the given username and password
    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", arrayOf(username, password))
        val exists = cursor.count > 0
        cursor.close()  // Always close the cursor
        return exists
    }

    // Retrieve the security answer of a specific user by username
    fun getUserSecurityAnswer(username: String): Int? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT securityAnswer FROM users WHERE username = ?", arrayOf(username))
        val securityAnswer = if (cursor.moveToFirst()) cursor.getInt(0) else null
        cursor.close()  // Always close the cursor
        return securityAnswer
    }

    // Update the password of a user identified by username
    fun updateUserPassword(username: String, newPassword: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("password", newPassword)
        }
        val result = db.update("users", contentValues, "username = ?", arrayOf(username))
        db.close()  // Close database connection after update
        return result > 0
    }
}
