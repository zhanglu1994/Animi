package com.nfrc.animi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

/**
 * Created by zhangl on 2018/10/26.
 */

public class LoadingView extends LinearLayout {

    private ShapeView shape_view;
    private View mShadowView;

    private int mTranslationDistance;


    private final  long ANIMATOR_DURATION = 350;

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTranslationDistance = dip2px(80);
        initLayout();

    }

    private void initLayout() {
        //实例化View    添加LoadingView


        // 不传空，表示把布局记载在this中
        inflate(getContext(),R.layout.ui_loading_view,this);

        mShadowView = findViewById(R.id.view);
        shape_view = findViewById(R.id.shape_view);


        startFailAnimator();

    }

    private void startFailAnimator() {


        //下落
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(shape_view,"translationY",0,mTranslationDistance);
        translationAnimator.setDuration(ANIMATOR_DURATION);
        translationAnimator.setInterpolator(new DecelerateInterpolator());
//        translationAnimator.start();


        //缩小
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mShadowView,"scaleX",1f,0.3f);
        scaleAnimator.setDuration(ANIMATOR_DURATION);
        scaleAnimator.setInterpolator(new DecelerateInterpolator());
//        scaleAnimator.start();

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationAnimator,scaleAnimator);
        animatorSet.start();


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
     
                startUpAnimator();
            }
        });

    }

    private void startUpAnimator() {
        //下落
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(shape_view,"translationY",mTranslationDistance,0);
        translationAnimator.setDuration(ANIMATOR_DURATION);
//        translationAnimator.start();


        //缩小
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mShadowView,"scaleX",0.3f,1f);
        scaleAnimator.setDuration(ANIMATOR_DURATION);
//        scaleAnimator.start();

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationAnimator,scaleAnimator);
        animatorSet.start();


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                shape_view.exchange();
                startFailAnimator();

            }
        });


    }

    private int dip2px(float dp) {


        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }


}
