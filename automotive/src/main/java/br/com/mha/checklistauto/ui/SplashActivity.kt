package br.com.mha.checklistauto.ui

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import br.com.mha.checklistauto.R

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            MainActivity.start(this)
            finish()
        }, DELAY_TO_NAVIGATE_TO_MAIN_SCREEN)
    }

    companion object {
        private const val DELAY_TO_NAVIGATE_TO_MAIN_SCREEN = 2000L
    }
}