package com.example.learnactivitytransitionanimation.model3.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Huangxuchu
 * @date 2021/9/8
 * @Description 参数：https://developer.android.google.cn/training/transitions/custom-transitions
 */
public class TurnPageTransition extends Transition {
    private static final String TAG = "TurnPageTransition";

    private static final String PROPNAME_ROTATION = "com.example.learnactivitytransition:rotation:rotationY";
    private static final String PROPNAME_RECT = "com.example.learnactivitytransition:rotation:rect";

    private final boolean isClose;

    public TurnPageTransition(boolean isClose) {
        this.isClose = isClose;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        Rect startRect = new Rect();
        View view = transitionValues.view;
        startRect.left = view.getLeft();
        startRect.top = view.getTop();
        startRect.right = view.getRight();
        startRect.bottom = view.getBottom();
        Log.i(TAG, "captureStartValues view rect=" + startRect + " x=" + view.getX() + " y=" + view.getY());
        transitionValues.values.put(PROPNAME_ROTATION, isClose ? -180 : 0);
        transitionValues.values.put(PROPNAME_RECT, startRect);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        Rect endRect = new Rect();
        View view = transitionValues.view;
        endRect.left = view.getLeft();
        endRect.top = view.getTop();
        endRect.right = view.getRight();
        endRect.bottom = view.getBottom();
        Log.i(TAG, "captureEndValues view rect=" + endRect + " x=" + view.getX() + " y=" + view.getY());
        if (isClose) {
            int width = view.getMeasuredWidth();
            endRect.left -= width;
            endRect.right -= width;
            Log.i(TAG, "captureEndValues 偏移后的数值 view rect=" + endRect + " x=" + view.getX() + " y=" + view.getY());
        }

        transitionValues.values.put(PROPNAME_ROTATION, isClose ? 0 : -180);
        transitionValues.values.put(PROPNAME_RECT, endRect);

    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        Log.i(TAG, "createAnimator startValues=" + startValues + " endValues=" + endValues + " sceneRoot=" + sceneRoot);
        if (null == startValues || null == endValues) {
            return null;
        }

        final View startView = startValues.view;
        final View endView = endValues.view;

        List<Integer> targetIds = getTargetIds();
        Animator animator = null;
        if (targetIds.contains(startView.getId())) {
            final Rect startRect = (Rect) startValues.values.get(PROPNAME_RECT);
            final Rect endRect = (Rect) endValues.values.get(PROPNAME_RECT);
            final Integer start = (Integer) startValues.values.get(PROPNAME_ROTATION);
            final Integer end = (Integer) endValues.values.get(PROPNAME_ROTATION);
            animator = create(startView, start, end, startRect, endRect);
        }
        Log.i(TAG, "startView" + " left=" + startView.getLeft() + " top=" + startView.getTop() + " right=" + startView.getRight() + " bottom=" + startView.getBottom());
        Log.i(TAG, "endView" + " left=" + endView.getLeft() + " top=" + endView.getTop() + " right=" + endView.getRight() + " bottom=" + endView.getBottom());
        return animator;
    }

    public AnimatorSet create(View view,
                              Integer startRotationY, Integer endRotationY,
                              Rect startRect, Rect endRect
    ) {
        float pivotX = view.getPivotX();
        float pivotY = view.getPivotY();
        long distance = getDuration();
        float cameraDistance = view.getContext().getResources().getDisplayMetrics().density * 3000;
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        Log.w(TAG, "create"
                + " isClose=" + isClose
                + " startRotationY=" + startRotationY
                + " endRotationY=" + endRotationY
                + " cameraDistance=" + cameraDistance
                + "\n width=" + width
                + " height=" + height
                + "\n defaultPivotX=" + pivotX
                + " defaultPivotY=" + pivotY);
        view.setCameraDistance(cameraDistance);
        view.setPivotX(0);
        if (isClose) view.setPivotY(0.45f * height);
        else view.setPivotY(0.2f * height);

        ValueAnimator rotationAnimator = ObjectAnimator.ofFloat(view, "rotationY", startRotationY, endRotationY);
        ValueAnimator leftAnimator = ObjectAnimator.ofInt(view, "left", startRect.left, endRect.left);
        ValueAnimator topAnimator = ObjectAnimator.ofInt(view, "top", startRect.top, endRect.top);
        ValueAnimator rightAnimator = ObjectAnimator.ofInt(view, "right", startRect.right, endRect.right);
        ValueAnimator bottomAnimator = ObjectAnimator.ofInt(view, "bottom", startRect.bottom, endRect.bottom);

        // 观察视图的位置的变化
        //rotationAnimator.addUpdateListener(animation -> printViewLocation(view));

        AnimatorSet set = new AnimatorSet();
        set.playTogether(rotationAnimator,
                leftAnimator,
                topAnimator,
                rightAnimator,
                bottomAnimator);
        set.setDuration(distance);
        return set;
    }

    private void printViewLocation(View view) {
        Log.d(TAG, " pivotX=" + view.getPivotX() + " pivotY=" + view.getPivotY());
        int left = view.getLeft();
        int top = view.getTop();
        int right = view.getRight();
        int bottom = view.getBottom();
        Log.d(TAG, " left=" + left
                + " top=" + top
                + " right=" + right
                + " bottom=" + bottom);
    }
}
