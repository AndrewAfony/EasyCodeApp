package com.andrewafony.easycodeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.andrewafony.easycodeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = (application as App).viewModel

        binding.apply {
            progressBar.isVisible = false

            buttonGetAdvice.setOnClickListener {
                buttonGetAdvice.isEnabled = false
                progressBar.visibility = View.VISIBLE
                viewModel.getAdvice()
            }

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                viewModel.chooseFavorites(isChecked)
            }

        }

        viewModel.init(object : DataCallback{
            override fun provideText(text: String) = runOnUiThread {
                binding.apply {
                    buttonGetAdvice.isEnabled = true
                    progressBar.visibility = View.INVISIBLE
                    advice.text = text
                }
            }

            override fun provideIconRes(id: Int) = runOnUiThread {
                binding.addFavorite.setImageResource(id)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
    }
}