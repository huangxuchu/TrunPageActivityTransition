package com.example.learnactivitytransitionanimation.model2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnactivitytransitionanimation.model2.adapter.ModeTwoAdapter
import com.example.learnactivitytransitionanimation.databinding.ActivityMode2StartBinding


class Mode2StartActivity : AppCompatActivity() {
    companion object {
        fun open(activity: Activity) {
            activity.startActivity(Intent(activity, Mode2StartActivity::class.java))
        }
    }

    private lateinit var binding: ActivityMode2StartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMode2StartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            openEndActivity()
        }
        binding.rvDates.layoutManager = LinearLayoutManager(this)
        binding.rvDates.adapter = ModeTwoAdapter()
    }

    private fun openEndActivity() {
        val i = Intent(this, Mode2EndActivity::class.java)
        val transitionActivityOptions =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair(binding.fab, "comment"))
        startActivity(i, transitionActivityOptions.toBundle())
    }

}