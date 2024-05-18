package com.example.cargame

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class splashScreen : AppCompatActivity() {

    //vavalriables
    private val SPLASH_TIME: Long = 3000

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_splash_screen);

        val animation1 = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        val animation2 = AnimationUtils.loadAnimation(this, R.anim.top_animation)

        val logo = findViewById(R.id.logo) as ImageView
        val textmiddle = findViewById(R.id.textmiddle) as TextView
        val textbottom = findViewById(R.id.textbottom) as TextView

        logo.startAnimation(animation2)
        textmiddle.startAnimation(animation1)
        textbottom.startAnimation(animation1)




        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }, SPLASH_TIME)


    }
}
