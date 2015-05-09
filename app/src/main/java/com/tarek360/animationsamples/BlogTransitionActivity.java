package com.tarek360.animationsamples;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tarek360.animationsamples.adapters.BlogRecyclerAdapter;
import com.tarek360.animationsamples.models.Blog;

import java.util.ArrayList;


public class BlogTransitionActivity extends AppCompatActivity {

    private static final String TAG = BlogTransitionActivity.class.getSimpleName();

    private ArrayList<Blog> mBlogList;

    private int mShortAnimationDuration = 200;
    private int mDelayAnimationDuration = 40;
    private int mLikeButtonAnimationDuration = 120;

    private RecyclerView mRecyclerView;
    private ImageView mImageView;
    private TextView titleTextView, subTitleTextView;
    private ImageButton btnLike;
    private View tintView;
    private View mBackgroundView, mContainer;
    private ScrollView mContentLayout;
    
    private boolean isOpen;
    private int position;
    private Rect startBounds;
    private Rect finalBounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_transition);

        initViews();

        // Setup layout manager for mBlogList and column count
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // Control orientation of the mBlogList
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        // Attach layout manager
        mRecyclerView.setLayoutManager(layoutManager);

        // Bind adapter to recycler
        mBlogList = new ArrayList<>();


        // Some of colors
        int[] color = {0xFFC2185B, 0xFFB3E5FC, 0xFFFFEB3B, 0xFFF8BBD0, 0xFFFF5252,
                0xFFE91E63, 0xFF448AFF, 0xFF00796B, 0xFFE91E63, 0xFFFF5252, 0xFFF8BBD0, 0xFF0288D1,};
        // Some of images
        int[] drawable = {R.drawable.apple_46, R.drawable.apple_style, R.drawable.steve_jobs,
                R.drawable.apple_46, R.drawable.apple_style, R.drawable.steve_jobs, R.drawable.apple_46,
                R.drawable.apple_style, R.drawable.steve_jobs, R.drawable.apple_46, R.drawable.apple_style,
                R.drawable.steve_jobs};

        //
        for (int i = 0; i < color.length; i++) {
            Blog blog = new Blog();
            blog.setImageRes(drawable[i]);
            blog.setBackGroundColor(color[i]);
            blog.setTitle("Label " + i);
            blog.setSubTitle("This is subTitle with number " + i);
            mBlogList.add(blog);
        }

        // Setting adapter
        final BlogRecyclerAdapter adapter = new BlogRecyclerAdapter(mBlogList);
        mRecyclerView.setAdapter(adapter);

        // Listen to the item touching
        mRecyclerView
                .addOnItemTouchListener(new RecyclerItemClickListener(
                        this,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View itemView, int position) {
                                if (!isOpen) {
                                    BlogTransitionActivity.this.position = position;
                                    openBlogInDetails(itemView);
                                }

                            }
                        }));
    }


    private void openBlogInDetails(View itemView) {

        // Now is open
        this.isOpen = true;

        //set Scroll View to the top
        mContentLayout.setScrollY(0);

        // Setting blog data to its views
        mImageView.setImageResource(mBlogList.get(position).getImageRes());
        tintView.setBackgroundColor(mBlogList.get(position).getBackGroundColor());
        titleTextView.setText(mBlogList.get(position).getTitle());
        subTitleTextView.setText(mBlogList.get(position).getSubTitle());

        // Init Rect
        startBounds = new Rect();
        finalBounds = new Rect();

        // Setting the bound to startRect "startBounds"
        startBounds.left = itemView.getLeft();
        startBounds.right = itemView.getRight();
        startBounds.top = itemView.getTop();
        startBounds.bottom = itemView.getBottom();

        // Setting the bound to endRect "finalBounds"
        finalBounds.left = mContainer.getLeft();
        finalBounds.right = mContainer.getRight();
        finalBounds.top = mContainer.getTop();
        finalBounds.bottom = mContainer.getBottom();

        // Calculate scaling factor
        float startScaleX = (float) startBounds.width() / finalBounds.width();
        float startScaleY = (float) startBounds.height() / finalBounds.height();



        // Prepare the views before starting animation

        mContentLayout.setVisibility(View.VISIBLE);
        mBackgroundView.setVisibility(View.VISIBLE);
        mImageView.setVisibility(View.VISIBLE);

        mBackgroundView.setPivotX(0);
        mBackgroundView.setPivotY(0);
        mBackgroundView.setX(startBounds.left);
        mBackgroundView.setY(startBounds.top);
        mBackgroundView.setScaleX(startScaleX);
        mBackgroundView.setScaleY(startScaleY);

        btnLike.setScaleX(0.0f);
        btnLike.setScaleY(0.0f);

        // backgroundView Color Animator
        ObjectAnimator backgroundViewColor = ObjectAnimator.ofObject(
                mBackgroundView, "backgroundColor", new ArgbEvaluator(),mBlogList.get(position).getBackGroundColor(), Color.WHITE);

        // backgroundView X point Animator
        ObjectAnimator backgroundViewX = ObjectAnimator
                .ofFloat(mBackgroundView, View.X, finalBounds.left);

        // backgroundView Y point Animator
        ObjectAnimator backgroundViewY = ObjectAnimator
                .ofFloat(mBackgroundView, View.Y, finalBounds.top);

        // backgroundView width scaling Animator
        ObjectAnimator backgroundViewScaleX = ObjectAnimator
                .ofFloat(mBackgroundView, View.SCALE_X, 1f);

        // backgroundView height scaling Animator
        ObjectAnimator backgroundViewScaleY = ObjectAnimator
                .ofFloat(mBackgroundView, View.SCALE_Y, 1f);

        // Set of animators to play all of animators together.
        AnimatorSet backgroundViewAnimatorSet  = new AnimatorSet();
        backgroundViewAnimatorSet.setInterpolator(new AccelerateInterpolator());
        backgroundViewAnimatorSet.setDuration(mShortAnimationDuration);
        backgroundViewAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }
        });
        backgroundViewAnimatorSet.playTogether(backgroundViewColor, backgroundViewX,
                backgroundViewY, backgroundViewScaleX, backgroundViewScaleY);

        //Start animation
        backgroundViewAnimatorSet.start();


        // contentLayout Alpha Animator
        ObjectAnimator mContentLayoutAlpha = ObjectAnimator.ofFloat(mContentLayout, View.ALPHA, 1f);

        // imageView Alpha Animator
        ObjectAnimator mImageViewAlpha = ObjectAnimator.ofFloat(mImageView, View.ALPHA, 1f);

        // Set of animators to play all of animators together.
        AnimatorSet mContentLayoutAnimatorSet  = new AnimatorSet();
        mContentLayoutAnimatorSet.setInterpolator(new AccelerateInterpolator());
        mContentLayoutAnimatorSet.setStartDelay(mDelayAnimationDuration);
        mContentLayoutAnimatorSet.setDuration(mShortAnimationDuration);
        mContentLayoutAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // Animate LikeButton after contentLayout completely shown
                animateLikeButton();
            }
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }
        });
        mContentLayoutAnimatorSet.playTogether(mContentLayoutAlpha, mImageViewAlpha);

        //Start animation
        mContentLayoutAnimatorSet.start();

    }

    private void animateLikeButton() {
        btnLike.animate().setDuration(mLikeButtonAnimationDuration).scaleX(1f).scaleY(1f);
    }


    private void closeBlogDetails() {

        // Now is closed
        this.isOpen = false;

        // Calculate scaling factor
        float startScaleX = (float) startBounds.width() / finalBounds.width();
        float startScaleY = (float) startBounds.height() / finalBounds.height();

        // contentLayout Alpha Animator
        ObjectAnimator contentLayoutAlpha = ObjectAnimator.ofFloat(mContentLayout, View.ALPHA, 0f);

        // imageView Alpha Animator
        ObjectAnimator imageViewAlpha = ObjectAnimator.ofFloat(mImageView, View.ALPHA, 0f);

        // Set of animators to play all of animators together.
        AnimatorSet mContentLayoutAnimatorSet  = new AnimatorSet();
        mContentLayoutAnimatorSet.setInterpolator(new AccelerateInterpolator());
        mContentLayoutAnimatorSet.setStartDelay(0);
        mContentLayoutAnimatorSet.setDuration(mShortAnimationDuration);
        mContentLayoutAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mContentLayout.setVisibility(View.GONE);
                mImageView.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mContentLayout.setVisibility(View.GONE);
                mImageView.setVisibility(View.GONE);
            }
        });
        mContentLayoutAnimatorSet.playTogether(contentLayoutAlpha, imageViewAlpha);

        //Start animation
        mContentLayoutAnimatorSet.start();


        // backgroundView Color Animator
        ObjectAnimator backgroundViewColor = ObjectAnimator.ofObject(
                mBackgroundView, "backgroundColor", new ArgbEvaluator(),
                Color.WHITE, mBlogList.get(position).getBackGroundColor());

        // backgroundView X point Animator
        ObjectAnimator backgroundViewX = ObjectAnimator
                .ofFloat(mBackgroundView, View.X, startBounds.left);

        // backgroundView Y point Animator
        ObjectAnimator backgroundViewY = ObjectAnimator
                .ofFloat(mBackgroundView, View.Y, startBounds.top);

        // backgroundView width scaling Animator
        ObjectAnimator backgroundViewScaleX = ObjectAnimator
                .ofFloat(mBackgroundView, View.SCALE_X, startScaleX);

        // backgroundView height scaling Animator
        ObjectAnimator backgroundViewScaleY = ObjectAnimator
                .ofFloat(mBackgroundView, View.SCALE_Y, startScaleY);

        // Set of animators to play all of animators together.
        AnimatorSet backgroundViewAnimatorSet = new AnimatorSet();
        backgroundViewAnimatorSet.setInterpolator(new AccelerateInterpolator());
        backgroundViewAnimatorSet.setStartDelay(mDelayAnimationDuration);
        backgroundViewAnimatorSet.setDuration(mShortAnimationDuration);
        backgroundViewAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mBackgroundView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mBackgroundView.setVisibility(View.GONE);
            }
        });
        backgroundViewAnimatorSet.playTogether(backgroundViewColor, backgroundViewX, backgroundViewY,
                backgroundViewScaleX, backgroundViewScaleY);

        //Start animation
        backgroundViewAnimatorSet.start();

    }



    @Override
    public void onBackPressed() {

        if (this.isOpen) {
            closeBlogDetails();
        } else {
            super.onBackPressed();
        }

    }

    private void initViews() {
        mContainer = findViewById(R.id.container);
        mBackgroundView = findViewById(R.id.backgroundView);
        mContentLayout  = (ScrollView) findViewById(R.id.contentLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tintView = findViewById(R.id.tintViewFront);
        btnLike = (ImageButton) findViewById(R.id.btnLike);
        mImageView = (ImageView) findViewById(R.id.imageView);
        titleTextView = (TextView) findViewById(R.id.title);
        subTitleTextView = (TextView) findViewById(R.id.subTitle);
    }


}
