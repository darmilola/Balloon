package com.useballoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.button.MaterialButton

@Suppress("DEPRECATION")
class SecondWelcome : AppCompatActivity() {
    private lateinit var line1: TextView
    private lateinit var line2: TextView
    private lateinit var line3: TextView
    private lateinit var getStarted: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_welcome)
        initView()
    }

    fun initView(){
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        line1 = findViewById(R.id.welcome_second_line1)
        line2 = findViewById(R.id.welcome_second_line2)
        line3 = findViewById(R.id.welcome_second_line3)
        getStarted = findViewById(R.id.welcome_second_getstarted_button)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        val slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down)
        val fadeIn1 = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val fadeOut1 = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        val fadeIn2 = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val fadeOut2 = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        val fadeIn3 = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val fadeOut3 = AnimationUtils.loadAnimation(this, R.anim.fade_out)

        if (line1.getVisibility() === View.GONE) {
            line1.setVisibility(View.VISIBLE)
            line1.startAnimation(fadeIn1)
        }

        fadeIn1.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                line1.startAnimation(fadeOut1)
                line1.visibility = View.GONE

                if (line3.getVisibility() === View.GONE) {
                    line3.setVisibility(View.VISIBLE)
                    line3.startAnimation(fadeIn3)
                }

            }
            override fun onAnimationRepeat(animation: Animation) {}
        })

        fadeIn3.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                line3.startAnimation(fadeOut3)
                line3.visibility = View.GONE


            }
            override fun onAnimationRepeat(animation: Animation) {}
        })

        fadeOut3.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {

                if (line2.getVisibility() === View.GONE) {
                    line2.setVisibility(View.VISIBLE)
                    line2.startAnimation(fadeIn2)
                }
                if (getStarted.getVisibility() === View.GONE) {
                    getStarted.setVisibility(View.VISIBLE)
                    getStarted.startAnimation(slideUp)
                }

            }
            override fun onAnimationRepeat(animation: Animation) {}
        })
    }
}