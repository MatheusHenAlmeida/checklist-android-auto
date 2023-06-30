package br.com.mha.checklistauto.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import br.com.mha.checklistauto.databinding.ActivityMainBinding

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupAddListButtonAction()
        setContentView(binding.root)
    }

    private fun setupAddListButtonAction() {
        binding.btAddList.setOnClickListener {
            // TODO: Show dialog to insert a new list
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}