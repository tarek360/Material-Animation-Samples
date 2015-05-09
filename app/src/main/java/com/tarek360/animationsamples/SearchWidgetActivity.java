package com.tarek360.animationsamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;

public class SearchWidgetActivity extends AppCompatActivity {

    private static final String TAG = SearchWidgetActivity.class.getSimpleName();

    private static final int SHORT_ANIMATION_DURATION = 180;

    private boolean isAnimate;

    private int mOvalWidth;
    private int mTargetExpandWidth;

    private ImageView mIvCloseBarTop;
    private ImageView mIvCloseBarBottom;
    private RelativeLayout mSearchLayout;
    private EditText mEtSearch;
    private ImageView mIvIconBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_widget);
        initViews();
    }

    private void initViews() {
        mSearchLayout = (RelativeLayout) findViewById(R.id.layoutSearch);
        mEtSearch = (EditText) findViewById(R.id.etSearch);
        mIvIconBar = (ImageView) findViewById(R.id.ivIconBar);
        mIvCloseBarTop = (ImageView) findViewById(R.id.ivCloseBarTop);
        mIvCloseBarBottom = (ImageView) findViewById(R.id.ivCloseBarBottom);

        int padding = mSearchLayout.getPaddingLeft() * 2;
        mOvalWidth = (int) getResources().getDimension(R.dimen.search_oval_width);
        mTargetExpandWidth = getResources().getDisplayMetrics().widthPixels - mOvalWidth - padding;
    }


    public void onClick(View v) {
        if (mIvCloseBarTop.getVisibility() == View.GONE) {
            showWidget();
        } else {
            hideWidget();
        }
    }

    @Override
    public void onBackPressed() {
        if (mIvCloseBarTop.getVisibility() == View.VISIBLE){
            hideWidget();
        }
        else{
            super.onBackPressed();
        }
    }



    public void showWidget() {

        Log.i(TAG,"showWidget");

        if (isAnimate){
            return;
        }
        isAnimate = true;

        // Translate Icon Bar
        mIvIconBar.animate()
                .translationX(-mIvIconBar.getLayoutParams().height)
                .translationY(-mIvIconBar.getLayoutParams().height)
                .setStartDelay(0)
                .setDuration(SHORT_ANIMATION_DURATION)
                .setListener(null)
                .start();


        // Animate Search EditText width
        ValueAnimator animator = ValueAnimator.ofInt(mOvalWidth, mTargetExpandWidth);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mEtSearch.getLayoutParams().width =  (int) animation.getAnimatedValue();
                mEtSearch.requestLayout();
            }
        });
        animator.setStartDelay(SHORT_ANIMATION_DURATION);
        animator.setDuration(SHORT_ANIMATION_DURATION);
        animator.start();

        // Prepare Close Bar Top before starting animation
        mIvCloseBarTop.setTranslationX(mIvCloseBarTop.getLayoutParams().height);
        mIvCloseBarTop.setTranslationY(-mIvCloseBarTop.getLayoutParams().height);
        mIvCloseBarTop.setVisibility(View.VISIBLE);
        mIvCloseBarTop.setAlpha(0f);
        // Animate Close Bar Top
        mIvCloseBarTop.animate()
                .alpha(1f)
                .translationX(0)
                .translationY(0)
                .setStartDelay(SHORT_ANIMATION_DURATION * 2)
                .setDuration(SHORT_ANIMATION_DURATION)
                .setInterpolator(new OvershootInterpolator())
                .start();

        // Prepare Close Bar Bottom before starting animation
        mIvCloseBarBottom.setTranslationX(mIvCloseBarBottom.getLayoutParams().height);
        mIvCloseBarBottom.setTranslationY(mIvCloseBarBottom.getLayoutParams().height);
        mIvCloseBarBottom.setVisibility(View.VISIBLE);
        mIvCloseBarBottom.setAlpha(0f);
        // Animate Close Bar Bottom
        mIvCloseBarBottom.animate()
                .alpha(1f)
                .translationX(0)
                .translationY(0)
                .setStartDelay(SHORT_ANIMATION_DURATION * 3)
                .setDuration(SHORT_ANIMATION_DURATION)
                .setInterpolator(new OvershootInterpolator())
                .start();

        // Animate Search EditText alpha
        mEtSearch.animate()
                .alpha(1f)
                .setStartDelay(SHORT_ANIMATION_DURATION * 2)
                .setDuration(SHORT_ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isAnimate = false;
                    }
                })
                .start();
    }


    public void hideWidget() {

        Log.i(TAG,"hideWidget");

        if (isAnimate) return;
        isAnimate = true;

        mIvCloseBarTop.animate()
                .alpha(0f)
                .translationX(mIvCloseBarTop.getLayoutParams().height)
                .translationY(-mIvCloseBarTop.getLayoutParams().height)
                .setDuration(SHORT_ANIMATION_DURATION)
                .setInterpolator(new AnticipateInterpolator())
                .setListener(null)
                .start();

        mIvCloseBarBottom.animate()
                .alpha(0f)
                .translationX(mIvCloseBarBottom.getLayoutParams().height)
                .translationY(mIvCloseBarBottom.getLayoutParams().height)
                .setStartDelay(SHORT_ANIMATION_DURATION)
                .setDuration(SHORT_ANIMATION_DURATION).setInterpolator(new AnticipateInterpolator())
                .start();

        mEtSearch.animate()
                .alpha(0f)
                .setStartDelay(SHORT_ANIMATION_DURATION)
                .setDuration(SHORT_ANIMATION_DURATION)
                .setListener(null)
                .start();

        ValueAnimator animator = ValueAnimator.ofInt(mTargetExpandWidth, mOvalWidth);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mEtSearch.getLayoutParams().width = (int) animation.getAnimatedValue();
                mEtSearch.requestLayout();
            }
        });
        animator.setStartDelay(SHORT_ANIMATION_DURATION * 3);
        animator.setDuration(SHORT_ANIMATION_DURATION);
        animator.start();

        mIvIconBar.animate()
                .translationX(0)
                .translationY(0)
                .setStartDelay(SHORT_ANIMATION_DURATION * 4)
                .setDuration(SHORT_ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        hideKeyboard();
                        mIvCloseBarTop.setVisibility(View.GONE);
                        mIvCloseBarBottom.setVisibility(View.GONE);
                        isAnimate = false;
                    }
                })
                .start();
    }

    private boolean hideKeyboard() {

        Log.i(TAG,"hideKeyboard");

        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
