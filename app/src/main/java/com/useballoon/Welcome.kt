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
import com.google.android.material.button.MaterialButton


@Suppress("DEPRECATION")
class Welcome : AppCompatActivity() {
    private lateinit var getStartedLayout: LinearLayout
    private lateinit var marketingLayout: LinearLayout
    private lateinit var goOn: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        initView()
    }

    fun initView() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        getStartedLayout = findViewById(R.id.welcome_get_started_layout)
        marketingLayout = findViewById(R.id.welcome_marketing_layout);
        goOn = findViewById(R.id.welcome_go_on)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        val slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down)

        if (marketingLayout.getVisibility() === View.INVISIBLE) {
            marketingLayout.setVisibility(View.VISIBLE)
            marketingLayout.startAnimation(slideDown)
        }

        slideDown.setAnimationListener(object : Animation.AnimationListener {
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