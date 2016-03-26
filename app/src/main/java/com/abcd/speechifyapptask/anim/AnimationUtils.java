package com.abcd.speechifyapptask.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnticipateInterpolator;

public class AnimationUtils {

    public static void animate(RecyclerView.ViewHolder holder, Boolean goesDown){

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.3F, 0.5F, 0.8F, 1.0F);
//        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.5F, 1.0F);
//        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown==true?300:-300, 0);
        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(holder.itemView, "translationX", -50, 0);
        animatorSet.setDuration(700);
        animatorSet.playTogether(animatorTranslateX, animatorScaleX /*, animatorTranslateY, animatorScaleY*/);
        animatorSet.setInterpolator(new AnticipateInterpolator());
        animatorSet.start();
    }

    public static void animateToolbar(View containerToolbar){

    }
}
