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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

@Suppress("DEPRECATION")
class SecondWelcome : AppCompatActivity() {
    private lateinit var line1: TextView
    private lateinit var line2: TextView
    private lateinit var getStartedButton: MaterialButton
    private lateinit var getStarted: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_welcome)
        initView()
    }

    fun initView(){
        line1 = findViewById(R.id.second_welcome_header_2)
        line2 = findViewById(R.id.second_welcome_header_1)
        getStarted = findViewById(R.id.second_welcome_get_started_layout)
        getStartedButton = findViewById(R.id.welcome_getstarted_button)


        val headerSpan: Spannable = SpannableString("We strongly believe that you\nare talented and deserve to\nblow. And that is why we have\nlaunched Balloon.")
        headerSpan.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            ), 46, 53, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        headerSpan.setSpan(
            UnderlineSpan(),
            46, 53, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        headerSpan.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            ), 96, 104, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        line2.setText(headerSpan)


        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        val fadeIn1 = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val fadeOut1 = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        val fadeIn2 = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        if (line1.getVisibility() === View.GONE) {
            line1.setVisibility(View.VISIBLE)
            line1.startAnimation(fadeIn1)
        }

        fadeIn1.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                line1.startAnimation(fadeOut1)
                line1.visibility = View.GONE



            }
            override fun onAnimationRepeat(animation: Animation) {}
        })

        fadeOut1.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                if (line2.getVisibility() === View.GONE) {
                    line2.setVisibility(View.VISIBLE)
                    line2.startAnimation(fadeIn2)
                }


            }
            override fun onAnimationRepeat(animation: Animation) {}
        })

        fadeIn2.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {

                if (getStarted.getVisibility() === View.INVISIBLE) {
                    getStarted.setVisibility(View.VISIBLE)
                    getStarted.startAnimation(slideUp)
                }

            }
            override fun onAnimationRepeat(animation: Animation) {}
        })

        getStartedButton.setOnClickListener {
            val intent = Intent(this, LoginOrSignup::class.java)
            startActivity(intent)
        }
    }
}