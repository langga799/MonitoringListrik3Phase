package com.example.monitoringlistrik3phase.splasscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.monitoringlistrik3phase.R
import com.example.monitoringlistrik3phase.TesActivity
import com.example.monitoringlistrik3phase.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        CoroutineScope(Dispatchers.IO).launch {
            delay(DELAY_SCREEN)
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finishAffinity()
        }
    }

    companion object {
        private const val DELAY_SCREEN = 1000L
    }
}