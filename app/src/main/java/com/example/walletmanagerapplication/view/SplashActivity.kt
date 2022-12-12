package com.example.walletmanagerapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.databinding.ActivitySplashBinding
import java.util.Timer
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplashBinding
    private var lefToCenter:Animation?=null
    private var topToDown:Animation?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animation()
        startNextActivity()


    }

    private fun animation(){
        lefToCenter=AnimationUtils.loadAnimation(this, R.anim.lefttocenter)
        topToDown=AnimationUtils.loadAnimation(this, R.anim.toptodown)

        binding.imageSplash.startAnimation(topToDown)
        binding.textSplash.startAnimation(lefToCenter)
    }

    private fun intent(view:View){
        binding.textSplash.setOnClickListener {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
    }
    private fun startNextActivity() {
        if (!isDestroyed) {
            val intent=Intent(this, MainActivity::class.java)

            //task after timer
            val tmtask= timerTask {
                if (!isDestroyed) {
                    startActivity(intent)
                    finish()
                }
            }
            //initalize timer
            val timer=Timer()
            timer.schedule(tmtask,2850)

        }
    }
}