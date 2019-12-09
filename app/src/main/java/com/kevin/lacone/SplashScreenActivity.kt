package com.kevin.lacone

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var iv: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar!!.hide()

        iv = findViewById(R.id.logo)

        val fade_in = AlphaAnimation(0f, 1f)
        fade_in.interpolator = DecelerateInterpolator()
        fade_in.duration = 2000

        val fade_out = AlphaAnimation(1f, 0f)
        fade_out.interpolator = AccelerateInterpolator()
        fade_out.startOffset = 2000
        fade_out.duration = 2000

        val anim = AnimationSet(false)
        anim.addAnimation(fade_in)
        anim.addAnimation(fade_out)
        iv.setAnimation(anim)

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)


    }

}
