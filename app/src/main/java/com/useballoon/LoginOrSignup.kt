package com.useballoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class LoginOrSignup : AppCompatActivity() {
    private lateinit var header: TextView
    private lateinit var login: MaterialButton
    private lateinit var signup: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_or_signup)
        initView()
    }

    fun initView(){
        login = findViewById(R.id.login_or_signup_login);
        signup = findViewById(R.id.login_or_signup_signup)
        header = findViewById(R.id.signup_header)
        val headerSpan: Spannable = SpannableString("Hire Evangelists on Balloon to\ndo or say anything to popularize\nyour music")
        headerSpan.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    this,
                    R.color.balloon_primary
                )
            ), 41, 49, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        header.setText(headerSpan)

        login.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        signup.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

    }
}