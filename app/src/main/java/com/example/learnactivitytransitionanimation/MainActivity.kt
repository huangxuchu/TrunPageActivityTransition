package com.example.learnactivitytransitionanimation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.learnactivitytransitionanimation.databinding.ActivityMainBinding
import com.example.learnactivitytransitionanimation.model1.Mode1StartActivity
import com.example.learnactivitytransitionanimation.model2.Mode2StartActivity
import com.example.learnactivitytransitionanimation.model3.Mode3StartActivity

/**
 * @author Huangxuchu
 * @date 2021/9/9
 * @Description
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun openModeOne(view: android.view.View) {
        Mode1StartActivity.open(this)
    }

    fun openModeTwo(view: android.view.View) {
        Mode2StartActivity.open(this)
    }

    fun openModeThree(view: android.view.View) {
        Mode3StartActivity.open(this)
    }
}