package com.example.learnactivitytransitionanimation.model2

import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionSet
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnactivitytransitionanimation.R
import com.example.learnactivitytransitionanimation.model2.adapter.ModeTwoAdapter
import com.example.learnactivitytransitionanimation.databinding.ActivityMode2EndBinding
import com.example.learnactivitytransitionanimation.model2.transition.*

class Mode2EndActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMode2EndBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMode2EndBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val rvDates = binding.rvDates
        val ivBack = binding.ivBack
        rvDates.layoutManager = LinearLayoutManager(this)
        rvDates.adapter = ModeTwoAdapter()
        ivBack.setOnClickListener { finish() }

        transition()
    }

    private fun transition() {
        window.enterTransition =
            CommentEnterTransition(
                this,
                binding.tvTitle,
                binding.groupSendContent
            )

        // 圆形变大效果
        val buildShareElemEnterSet: TransitionSet = buildShareElemEnterSet()
        window.sharedElementEnterTransition = buildShareElemEnterSet

        val buildShareElemReturnSet: TransitionSet = buildShareElemReturnSet()
        window.sharedElementReturnTransition = buildShareElemReturnSet
    }

    private fun buildShareElemEnterSet(): TransitionSet {
        val targetId: Int = R.id.comment_box
        val transitionSet = TransitionSet()
        val changePos: Transition =
            ChangePosition()
        changePos.duration = 300
        changePos.addTarget(targetId)
        transitionSet.addTransition(changePos)
        val revealTransition: Transition =
            ShareElemEnterRevealTransition(
                binding.commentBox
            )
        transitionSet.addTransition(revealTransition)
        revealTransition.addTarget(targetId)
        revealTransition.interpolator = FastOutSlowInInterpolator()
        revealTransition.duration = 300
        val changeColor =
            ChangeColor(
                resources.getColor(R.color.black_85_alpha),
                resources.getColor(R.color.white)
            )
        changeColor.addTarget(targetId)
        changeColor.setDuration(350)
        transitionSet.addTransition(changeColor)
        transitionSet.duration = 900
        return transitionSet
    }

    private fun buildShareElemReturnSet(): TransitionSet {
        val transitionSet = TransitionSet()
        val changePos: Transition =
            ShareElemReturnChangePosition()
        changePos.addTarget(R.id.comment_box)
        transitionSet.addTransition(changePos)
        val changeColor =
            ChangeColor(
                resources.getColor(R.color.white),
                resources.getColor(R.color.black_85_alpha)
            )
        changeColor.addTarget(R.id.comment_box)
        transitionSet.addTransition(changeColor)
        val revealTransition: Transition =
            ShareElemReturnRevealTransition(
                binding.commentBox
            )
        revealTransition.addTarget(R.id.comment_box)
        transitionSet.addTransition(revealTransition)
        transitionSet.duration = 900
        return transitionSet
    }
}