package com.tarek360.animationsamples;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Property;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by Tarek360 on 3/4/2015.
 */

public class PlayPauseDrawable extends Drawable {

    private boolean mAnimationMode;
    private long mAnimationDuration;
    private static final Interpolator ANIMATION_INTERPOLATOR = new DecelerateInterpolator();
    private static final String TAG = PlayPauseDrawable.class.getSimpleName();

    private Paint mLinePaint;
    private Paint mBackgroundPaint;

    private float[] mPoints = new float[12];
    private final RectF mBounds = new RectF();


    private ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();

    private float mRotation;
    private float mStrokeWidth;
    private int mPlayColor;
    private int mPauseColor;
    private int shiftY;

    public PlayPauseDrawable() {
        this(14, 0XFFE91E63, 0XFFffffff, 300);
    }

    public PlayPauseDrawable(int strokeWidth, int playColor, int pauseColor, long animationDuration) {
        mStrokeWidth = strokeWidth;
        mPlayColor = playColor;
        mPauseColor = pauseColor;
        mAnimationDuration = animationDuration;
        initPaints();
    }

    private void initPaints() {
        mLinePaint = new Paint(ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(mPauseColor);
        mLinePaint.setStrokeWidth(mStrokeWidth);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);

        mBackgroundPaint = new Paint(ANTI_ALIAS_FLAG);
        mLinePaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(mPlayColor);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        int padding = bounds.centerX()/2;
        shiftY = bounds.centerY()/8;
        mBounds.left = bounds.left + padding;
        mBounds.right = bounds.right - padding;
        mBounds.top = bounds.top + padding;
        mBounds.bottom = bounds.bottom - padding;

        setupPauseMode();
    }

    private void setupPauseMode(){

        mPoints[0] =  mBounds.left + shiftY;
        mPoints[1] =  mBounds.top;

        mPoints[2] =  mBounds.left + shiftY;
        mPoints[3] =  mBounds.bottom;

        mPoints[4] =  mBounds.right - shiftY;
        mPoints[5] =  mBounds.top;

        mPoints[6] =  mBounds.right - shiftY;
        mPoints[7] =  mBounds.bottom;

        mPoints[8] =  mBounds.left + shiftY;
        mPoints[9] =  mBounds.centerY();

        mPoints[10] = mBounds.left + shiftY;
        mPoints[11] = mBounds.centerY();
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawCircle(mBounds.centerX(), mBounds.centerY(), mBounds.centerX(), mBackgroundPaint);

        canvas.save();
        canvas.rotate(180 * mRotation, (x(0) + x(1))/2, (y(0) + y(1))/2);
        canvas.drawLine(x(0), y(0), x(1), y(1), mLinePaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(180 * mRotation, (x(2) + x(3)) / 2, (y(2) + y(3)) / 2);
        canvas.drawLine(x(2), y(2), x(3), y(3), mLinePaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(180 * mRotation, (x(4) + x(5)) / 2, (y(4) + y(5)) / 2);
        canvas.drawLine(x(4), y(4), x(5), y(5), mLinePaint);

    }

    public void toggle() {
        if(mAnimationMode) {
            animatePause();
        } else {
            animatePlay();
        }
        mAnimationMode = !mAnimationMode;
    }



    public void animatePause() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(this, mPropertyPointAX, mBounds.left + shiftY),
                ObjectAnimator.ofFloat(this, mPropertyPointAY, mBounds.top),

                ObjectAnimator.ofFloat(this, mPropertyPointBX, mBounds.left + shiftY),
                ObjectAnimator.ofFloat(this, mPropertyPointBY, mBounds.bottom),

                ObjectAnimator.ofFloat(this, mPropertyPointCX, mBounds.right - shiftY),
                ObjectAnimator.ofFloat(this, mPropertyPointCY, mBounds.top),

                ObjectAnimator.ofFloat(this, mPropertyPointDX, mBounds.right - shiftY),
                ObjectAnimator.ofFloat(this, mPropertyPointDY, mBounds.bottom),

                ObjectAnimator.ofFloat(this, mPropertyPointEX, mBounds.left + shiftY),
                ObjectAnimator.ofFloat(this, mPropertyPointEY, mBounds.centerY()),

                ObjectAnimator.ofFloat(this, mPropertyPointFX, mBounds.left + shiftY),
                ObjectAnimator.ofFloat(this, mPropertyPointFY, mBounds.centerY()),

                ObjectAnimator.ofFloat(this, mRotationProperty, 0f, 1f),
                ObjectAnimator.ofObject(this, mLineColorProperty, mArgbEvaluator, mPauseColor),
                ObjectAnimator.ofObject(this, mBackgroundColorProperty, mArgbEvaluator, mPlayColor),

                ObjectAnimator.ofFloat(this, mStrokeWidthProperty, mStrokeWidth)


        );
        set.setDuration(mAnimationDuration);
        set.setInterpolator(ANIMATION_INTERPOLATOR);
        set.start();
    }


    public void animatePlay() {
        AnimatorSet set = new AnimatorSet();


        set.playTogether(
                ObjectAnimator.ofFloat(this, mPropertyPointAX, mBounds.left + shiftY),
                ObjectAnimator.ofFloat(this, mPropertyPointAY, mBounds.top),

                ObjectAnimator.ofFloat(this, mPropertyPointBX, mBounds.right + shiftY),
                ObjectAnimator.ofFloat(this, mPropertyPointBY, mBounds.centerY()),

                ObjectAnimator.ofFloat(this, mPropertyPointCX, mBounds.right + shiftY),
                ObjectAnimator.ofFloat(this, mPropertyPointCY, mBounds.centerY()),

                ObjectAnimator.ofFloat(this, mPropertyPointDX, mBounds.left + shiftY),
                ObjectAnimator.ofFloat(this, mPropertyPointDY, mBounds.bottom),


                ObjectAnimator.ofFloat(this, mPropertyPointEX, mBounds.left + shiftY),
                ObjectAnimator.ofFloat(this, mPropertyPointEY, mBounds.bottom),

                ObjectAnimator.ofFloat(this, mPropertyPointFX, mBounds.left + shiftY),
                ObjectAnimator.ofFloat(this, mPropertyPointFY, mBounds.top),

                ObjectAnimator.ofFloat(this, mRotationProperty, 0f, 1f),
                ObjectAnimator.ofObject(this, mLineColorProperty, mArgbEvaluator, mPlayColor),
                ObjectAnimator.ofObject(this, mBackgroundColorProperty, mArgbEvaluator, mPauseColor),

                ObjectAnimator.ofFloat(this, mStrokeWidthProperty, mStrokeWidth * 5/7)


        );
        set.setDuration(mAnimationDuration);
        set.setInterpolator(ANIMATION_INTERPOLATOR);
        set.start();
    }


    private float x(int pointIndex) {
        return mPoints[xPosition(pointIndex)];
    }

    private float y(int pointIndex) {
        return mPoints[yPosition(pointIndex)];
    }

    private int xPosition(int pointIndex) {
        return pointIndex*2;
    }

    private int yPosition(int pointIndex) {
        return xPosition(pointIndex) + 1;
    }



    @Override
    public void setAlpha(int alpha) {}

    @Override
    public void setColorFilter(ColorFilter cf) {}

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    private Property<PlayPauseDrawable, Integer> mBackgroundColorProperty = new Property<PlayPauseDrawable, Integer>(Integer.class, "bg_color") {
        @Override
        public Integer get(PlayPauseDrawable object) {
            return object.mBackgroundPaint.getColor();
        }

        @Override
        public void set(PlayPauseDrawable object, Integer value) {
            object.mBackgroundPaint.setColor(value);
        }
    };

    private Property<PlayPauseDrawable, Integer> mLineColorProperty = new Property<PlayPauseDrawable, Integer>(Integer.class, "line_color") {
        @Override
        public Integer get(PlayPauseDrawable object) {
            return object.mLinePaint.getColor();
        }

        @Override
        public void set(PlayPauseDrawable object, Integer value) {
            object.mLinePaint.setColor(value);
        }
    };

    private Property<PlayPauseDrawable, Float> mRotationProperty = new Property<PlayPauseDrawable, Float>(Float.class, "rotation") {
        @Override
        public Float get(PlayPauseDrawable object) {
            return object.mRotation;
        }

        @Override
        public void set(PlayPauseDrawable object, Float value) {
            object.mRotation = value;
        }
    };

    private PointProperty mPropertyPointAX = new XPointProperty(0);
    private PointProperty mPropertyPointAY = new YPointProperty(0);
    private PointProperty mPropertyPointBX = new XPointProperty(1);
    private PointProperty mPropertyPointBY = new YPointProperty(1);
    private PointProperty mPropertyPointCX = new XPointProperty(2);
    private PointProperty mPropertyPointCY = new YPointProperty(2);
    private PointProperty mPropertyPointDX = new XPointProperty(3);
    private PointProperty mPropertyPointDY = new YPointProperty(3);
    private PointProperty mPropertyPointEX = new XPointProperty(4);
    private PointProperty mPropertyPointEY = new YPointProperty(4);
    private PointProperty mPropertyPointFX = new XPointProperty(5);
    private PointProperty mPropertyPointFY = new YPointProperty(5);

    private abstract class PointProperty extends Property<PlayPauseDrawable, Float> {

        protected int mPointIndex;

        private PointProperty(int pointIndex) {
            super(Float.class, "point_" + pointIndex);
            mPointIndex = pointIndex;
        }
    }

    private StrokeWidthProperty mStrokeWidthProperty = new StrokeWidthProperty();
    private class StrokeWidthProperty extends Property<PlayPauseDrawable, Float> {

        public StrokeWidthProperty() {
            super(Float.class, "StrokeWidth");
        }

        @Override
        public Float get(PlayPauseDrawable object) {
            return object.mStrokeWidth;
        }

        @Override
        public void set(PlayPauseDrawable object, Float value) {
            object.mLinePaint.setStrokeWidth(value);
            invalidateSelf();
        }
    }

    private class XPointProperty extends PointProperty {

        private XPointProperty(int pointIndex) {
            super(pointIndex);
        }

        @Override
        public Float get(PlayPauseDrawable object) {
            return object.x(mPointIndex);
        }

        @Override
        public void set(PlayPauseDrawable object, Float value) {
            object.mPoints[object.xPosition(mPointIndex)] = value;
            invalidateSelf();
        }
    }

    private class YPointProperty extends PointProperty {

        private YPointProperty(int pointIndex) {
            super(pointIndex);
        }

        @Override
        public Float get(PlayPauseDrawable object) {
            return object.y(mPointIndex);
        }

        @Override
        public void set(PlayPauseDrawable object, Float value) {
            object.mPoints[object.yPosition(mPointIndex)] = value;
            invalidateSelf();
        }
    }

}
