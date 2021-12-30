package com.useballoon

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.ImageView
import android.widget.TextView

@Suppress("DEPRECATION")
class MissionCreationSuccess : AppCompatActivity() {
    lateinit var welldoneText: TextView
    lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission_creation_success)
        initView()
    }

    private fun initView(){
        welldoneText = findViewById(R.id.mission_success_well_done)
        backButton = findViewById(R.id.mission_success_back)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val userFirstname = preferences.getString(getString(R.string.saved_user_firstname), "")

        val builder = StringBuilder()
        builder.append("Well-done ")
            .append(userFirstname)
            .append(", ")
            .append("your mission has been sent out for a review, and as soon as it is approved, it will be immediately sent out to Balloon Evangelists who can then apply to execute your mission.")
        welldoneText.text = builder.toString()

        backButton.setOnClickListener {
            finish()
        }
    }
}