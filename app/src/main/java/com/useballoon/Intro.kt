package com.useballoon
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class Intro : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        showGif()

    }

    fun showGif() {
        val imageView = findViewById<ImageView>(R.id.welcome_waves)
        Glide.with(this).load(R.drawable.wavegif).into(imageView)
    }


}