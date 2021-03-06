package com.example.learnactivitytransitionanimation.model2.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import java.lang.reflect.Method;

/**
 * Created by chenzhiyong on 16/6/7.
 */
public class ShareElemReturnRevealTransition extends Transition {
    private static final String TAG = "ShareElemReturnRevealTransition";

    private static final String PROPNAME_BACKGROUND = "custom_reveal:change_radius:radius";

    private boolean hasAnim = false;

    private View animView;

    private Rect startRect;
    private Rect endRect;


    public ShareElemReturnRevealTransition(View animView) {
        this.animView = animView;
        startRect = new Rect();
        endRect = new Rect();
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        float widthSquared = view.getWidth() * view.getWidth();
        float heightSquared = view.getHeight() * view.getHeight();
        int radius = (int) Math.sqrt(widthSquared + heightSquared) / 2;

        transitionValues.values.put(PROPNAME_BACKGROUND, radius);
        transitionValues.view.getGlobalVisibleRect(startRect);

    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.view.getLocalVisibleRect(endRect);

        transitionValues.values.put(PROPNAME_BACKGROUND, transitionValues.view.getWidth() / 2);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {

        if (null == startValues || null == endValues) {
            return null;
        }

        final View view = endValues.view;
        int startRadius = (int) startValues.values.get(PROPNAME_BACKGROUND);
        int endRadius = (int) endValues.values.get(PROPNAME_BACKGROUND);

        // ??????????????????????????????,  View ?????????????????? ??? ?????????????????? ShareElem ????????????
        // ???????????? ??????????????? View ?????? ?????? ???????????????????????????
        // ????????????
        relfectInvoke(view,
                startRect.left,
                startRect.top,
                startRect.right,
                startRect.bottom
        );

        Animator reveal = createAnimator(view, startRadius, endRadius);

        // ?????????????????? ????????????????????? View ????????????  ???????????????????????????????????????
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setClipBounds(new Rect(0, 0, 1, 1));
                view.setVisibility(View.GONE);
            }
        });
        return reveal;

    }

    private Animator createAnimator(View view, float startRadius, float endRadius) {
        int centerX = view.getWidth() / 2;
        int centerY = view.getHeight() / 2;

        Animator reveal = ViewAnimationUtils.createCircularReveal(view, centerX, centerY,
                startRadius, endRadius);
        return new ShareElemEnterRevealTransition.NoPauseAnimator(reveal);
    }

    // setLeftTopRightBottom ??????????????????, ????????????????????? View ????????????????????? ??? ChangeBounds ???????????????
    private void relfectInvoke(View view, int left, int top, int right, int bottom) {

        Class clazz = view.getClass();
        try {
            Method m1 = clazz.getMethod("setLeftTopRightBottom", new Class[]{int.class, int.class, int.class, int.class});
            m1.invoke(view, left, top, right, bottom);
        } catch (Exception e) {
            e.printStackTrace();

            // 5.0??????  ?????? setLeftTopRightBottom ????????????  ?????????????????? ,??? ?????? 5.0 ???????????????????????????????
            view.setLeft(left);
            view.setRight(right);
            view.setTop(top);
            view.setBottom(bottom);
        }

    }

}
