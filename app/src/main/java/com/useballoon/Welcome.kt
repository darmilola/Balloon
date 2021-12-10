package com.useballoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.button.MaterialButton
import android.text.Spannable


import androidx.core.content.ContextCompat

import android.text.style.ForegroundColorSpan

import android.text.SpannableString
import android.widget.TextView


@Suppress("DEPRECATION")
class Welcome : AppCompatActivity() {
    private lateinit var getStartedLayout: LinearLayout
    private lateinit var marketingLayout: LinearLayout
    private lateinit var goOn: MaterialButton
    private lateinit var header: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        initView()
    }

    fun initView() {


        header = findViewById(R.id.welcome_header);

        val headerSpan: Spannable = SpannableString("Imagine everyone on the\ninternet doing or saying things\nto popularize you music...")
        headerSpan.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            ), 59, 69, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        header.setText(headerSpan)

        getStartedLayout = findViewById(R.id.welcome_get_started_layout)
        marketingLayout = findViewById(R.id.welcome_marketing_layout);
        header = findViewById(R.id.welcome_header)
        goOn = findViewById(R.id.welcome_go_on)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fast_fadein)
        val slideFromLeft = AnimationUtils.loadAnimation(this, R.anim.fast_fadein)

        if (marketingLayout.getVisibility() === View.INVISIBLE) {
            marketingLayout.setVisibility(View.VISIBLE)
            marketingLayout.startAnimation(fadeIn)
        }



        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                if (getStartedLayout.getVisibility() === View.INVISIBLE) {
                    getStartedLayout.setVisibility(View.VISIBLE)
                    getStartedLayout.startAnimation(slideUp)
                }
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })

        goOn.setOnClickListener {
            val intent = Intent(this, SecondWelcome::class.java)
            startActivity(intent)
        }


    }
}