package com.example.learnactivitytransitionanimation.model1

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.learnactivitytransitionanimation.R

class Mode1StartActivity : AppCompatActivity() {
    companion object {
        fun open(activity: Activity) {
            activity.startActivity(Intent(activity, Mode1StartActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode1_strat)
    }

    fun openSecondAct(view: View) {
        openEndActivity()
    }

    private fun openEndActivity() {
        val intent = Intent(
            this@Mode1StartActivity, Mode1EndActivity::class.java
        )
        val pairOne = Pair.create<View, String>(findViewById(R.id.tv_1), "tn_open_view")
        val pairTwo = Pair.create<View, String>(findViewById(R.id.tv_2), "tn_open_view_2")
        startActivity(
            intent,
            ActivityOptions.makeSceneTransitionAnimation(
                this,
                pairOne, pairTwo
            ).toBundle()
        )
    }
}