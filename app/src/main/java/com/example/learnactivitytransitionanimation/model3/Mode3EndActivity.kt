package com.example.learnactivitytransitionanimation.model3

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.Transition
import android.transition.TransitionSet
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.learnactivitytransitionanimation.R
import com.example.learnactivitytransitionanimation.databinding.ActivityMode3EndBinding
import com.example.learnactivitytransitionanimation.model3.transition.TurnPageTransition

class Mode3EndActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMode3EndBinding
    private var isTransitionStarting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMode3EndBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imgUrl = intent.getStringExtra("imgUrl")
        binding.ivCover.load(imgUrl)
        createTransition()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && isTransitionStarting) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun createTransition() {
        window.sharedElementEnterTransition = createTransition(false)
            //.addListener(transitionListener)
        window.sharedElementReturnTransition = createTransition(true)
    }

    private fun createTransition(isClose: Boolean): Transition {
        val transitionSet = TransitionSet()

        val changeBounds = ChangeBounds()
        changeBounds.targetIds.add(R.id.tv_reader)

        val changeImageTransform = ChangeImageTransform()
        changeImageTransform.targetIds.add(R.id.iv_cover)

        val turnPageTransition = TurnPageTransition(isClose)
        turnPageTransition.targetIds.add(R.id.iv_cover)

        transitionSet.addTransition(changeBounds)
        transitionSet.addTransition(changeImageTransform)
        transitionSet.addTransition(turnPageTransition)
        transitionSet.duration = TRANSITION_DURATION
        return transitionSet
    }

    private val transitionListener = object :
        Transition.TransitionListener {
        override fun onTransitionStart(transition: Transition?) {
            isTransitionStarting = true;
        }

        override fun onTransitionEnd(transition: Transition?) {
            isTransitionStarting = false;
        }

        override fun onTransitionCancel(transition: Transition?) {
            isTransitionStarting = false
        }

        override fun onTransitionPause(transition: Transition?) {
            isTransitionStarting = false
        }

        override fun onTransitionResume(transition: Transition?) {
            isTransitionStarting = true
        }
    }

    companion object {
        const val TRANSITION_DURATION = 1000L;
    }

}