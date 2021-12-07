package com.useballoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.useballoon.R;
import android.view.View

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView


@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    private lateinit var balloonLogo: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initView()
    }

     fun initView() {
         val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
         balloonLogo = findViewById(R.id.splash_screen_balloon_logo)
         balloonLogo.startAnimation(animation)

         window.setFlags(
             WindowManager.LayoutParams.FLAG_FULLSCREEN,
             WindowManager.LayoutParams.FLAG_FULLSCREEN
         )
         Handler().postDelayed({
             val intent = Intent(this, Welcome::class.java)
             startActivity(intent)
             finish()
         }, 3000) // 3000 is the delayed time in milliseconds.
     }
}
