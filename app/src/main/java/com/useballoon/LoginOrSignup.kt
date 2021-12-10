package com.useballoon

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

class LoginOrSignup : AppCompatActivity() {
    private lateinit var header: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_or_signup)
        initView()
    }

    fun initView(){

        header = findViewById(R.id.signup_header)
        val headerSpan: Spannable = SpannableString("Hire Evangelists on Balloon to\n do or say anything to popularize\n your music")
        headerSpan.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    this,
                    R.color.balloon_primary
                )
            ), 41, 51, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        header.setText(headerSpan)



    }
}