package com.yiyuanliu.eleloadingview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yiyuan on 2016/9/9.
 */
public class EleLoadingView extends View {
    private static final String TAG = "EleLoadingView";

    private static final int DEFAULT_Icon_SIZE_DIP = 48;
    private static final int DEFAULT_SHADOW_COLOR = 0xffa0a0a0;
    private static final int DEFAULT_JUMP_HEIGHT_DIP = 48;

    private Drawable mIcon;
    private Drawable[] mIconList;

    private int mIconHeight;
    private int mIconWidth;
    private int mJumpHeight;
    private int mShadowColor;
    private float mShadowMax = 1f;
    private float mShadowMin = 0.4f;
    private boolean mRotate;

    private float percent;
    private boolean shouldChange;
    private int index;
    private ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            percent = (float) animation.getAnimatedValue();
            invalidate();
        }
    };
    private ValueAnimator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            Log.d(TAG, "Start");
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            Log.d(TAG, "End");
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            Log.d(TAG, "Cancel");
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            shouldChange = !shouldChange;
            if (!shouldChange || mIconList == null || mIconList.length == 0) {
                return;
            }

            index ++;
            index = index % mIconList.length;
            mIcon = mIconList[index];
            invalidate();

        }
    };
    private ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
    {
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setDuration(280);
        valueAnimator.addUpdateListener(updateListener);
        valueAnimator.addListener(animatorListener);
    }

    private RectF tempRectF = new RectF();
    private Paint tempPaint = new Paint();

    public EleLoadingView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public EleLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public EleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EleLoadingView, defStyleAttr, 0);

        int iconArrayRes = typedArray.getResourceId(R.styleable.EleLoadingView_eleIconList, 0);
        if (iconArrayRes != 0) {
            TypedArray iconArray = context.getResources().obtainTypedArray(iconArrayRes);
            List<Drawable> iconList = new ArrayList<>();
            int i = 0;
            while (true) {
                try {
                    Drawable icon = iconArray.getDrawable(i ++);
                    if (icon == null) {
                        break;
                    } else {
                        iconList.add(icon);
                    }
                } catch (Exception e) {
                    break;
                }
            }
            setIcon(iconList.toArray(new Drawable[iconList.size()]));
            iconArray.recycle();
        }

        if (mIcon == null) {
            Drawable icon = typedArray.getDrawable(R.styleable.EleLoadingView_eleIcon);
            setIcon(icon);
        }

        int iconHeight = typedArray.getDimensionPixelSize(R.styleable.EleLoadingView_eleIconHeight,
                dp2px(context, DEFAULT_Icon_SIZE_DIP));
        setIconHeight(iconHeight);

        int iconWidth = typedArray.getDimensionPixelOffset(R.styleable.EleLoadingView_eleIconWidth,
                dp2px(context, DEFAULT_Icon_SIZE_DIP));
        setIconWidth(iconWidth);

        int jumpHeight = typedArray.getDimensionPixelOffset(R.styleable.EleLoadingView_eleJumpHeight,
                dp2px(context, DEFAULT_JUMP_HEIGHT_DIP));
        setJumpHeight(jumpHeight);

        int shadowColor = typedArray.getColor(R.styleable.EleLoadingView_eleShadowColor,
                DEFAULT_SHADOW_COLOR);
        setShadowColor(shadowColor);

        float shadowMax = typedArray.getFloat(R.styleable.EleLoadingView_eleShadowMax, mShadowMax);
        setShadowMax(shadowMax);

        float shadowMin = typedArray.getFloat(R.styleable.EleLoadingView_eleShadowMin, mShadowMin);
        setShadowMin(shadowMin);

        boolean rotate = typedArray.getBoolean(R.styleable.EleLoadingView_eleRotate, false);
        setRotate(rotate);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0, height = 0;
        switch (MeasureSpec.getMode(widthMeasureSpec)) {
            case MeasureSpec.AT_MOST:
                int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
                width = Math.min(maxWidth, mIconWidth + getPaddingLeft() + getPaddingRight());
                break;
            case MeasureSpec.EXACTLY:
                width = MeasureSpec.getSize(widthMeasureSpec);
                break;
            case MeasureSpec.UNSPECIFIED:
                width = mIconWidth + getPaddingLeft() + getPaddingRight();
                break;
        }

        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.AT_MOST:
                int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
                height = Math.min(maxHeight, mIconWidth / 2 + mIconHeight + mJumpHeight + getPaddingTop() + getPaddingBottom());
                break;
            case MeasureSpec.EXACTLY:
                height = MeasureSpec.getSize(heightMeasureSpec);
                break;
            case MeasureSpec.UNSPECIFIED:
                height = mIconWidth / 2 + mIconHeight + mJumpHeight + getPaddingTop() + getPaddingBottom();
                break;
        }
        setMeasuredDimension(width, height);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mIcon == null) {
            return;
        }

        int desiredWidth = mIconWidth;
        int desiredHeight = mIconHeight + mJumpHeight + mIconHeight / 2;

        int xOffset = (getWidth() - getPaddingRight() - getPaddingLeft() - desiredWidth) / 2;
        int yOffset = (getHeight() - getPaddingTop() - getPaddingBottom() - desiredHeight) / 2;

        canvas.translate(xOffset, yOffset);

        int shadowCenterX = mIconWidth / 2;
        int shadowCenterY = mJumpHeight + mIconHeight;
        int shadowRa = (int) (mIconWidth * ((mShadowMax - mShadowMin) * percent + mShadowMin) / 2);
        int shadowRb = shadowRa / 2;
        tempRectF.set(shadowCenterX - shadowRa, shadowCenterY - shadowRb,
                shadowCenterX + shadowRa, shadowCenterY + shadowRb);
        tempPaint.setColor(mShadowColor);
        canvas.drawOval(tempRectF, tempPaint);

        int iconLeft = 0;
        int iconTop = (int) (mJumpHeight * percent);
        int rectLeft = iconLeft,
            rectTop = iconTop,
            rectRight = iconLeft + mIconWidth,
            rectBottom = iconTop + mIconHeight;
        mIcon.setBounds(rectLeft, rectTop, rectRight, rectBottom);

        if (mRotate) {
            float degree = 0;
            if (shouldChange) {
                degree = -percent * 180;
            } else {
                degree = percent * 180;
            }
            canvas.rotate(degree, (float) (rectLeft  +rectRight) / 2, (float) (rectTop  +rectBottom) / 2);
        }
        mIcon.draw(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        valueAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        valueAnimator.end();
    }

    public void setIconHeight(int iconHeight) {
        mIconHeight = iconHeight;
        requestLayout();
    }

    public void setIconWidth(int iconWidth) {
        mIconWidth = iconWidth;
        requestLayout();
    }

    public void setIconHeightDp(int iconHeightDp) {
        setIconHeight(dp2px(getContext(), iconHeightDp));
    }

    public void setIconWidthDp(int iconWidthDp) {
        setIconWidth(dp2px(getContext(), iconWidthDp));
    }

    public void setIcon(Drawable... icons) {
        if (icons == null || icons.length == 0) {
            mIconList = null;
            mIcon = null;
        } else {
            mIconList = icons;
            mIcon = mIconList[0];
        }
        invalidate();
    }

    public void setJumpHeight(int jumpHeight) {
        mJumpHeight = jumpHeight;
        requestLayout();
    }

    public void setJumpHeightDp(int jumpHeightDp) {
        setJumpHeight(dp2px(getContext(), jumpHeightDp));
    }

    public void setShadowColor(int shadowColor) {
        this.mShadowColor = shadowColor;
        invalidate();
    }

    public void setDuration(long duration) {
        valueAnimator.setDuration(duration);
    }

    public void setShadowMax(float max) {
        mShadowMax = max;
        invalidate();
    }

    public void setShadowMin(float min) {
        mShadowMin = min;
        invalidate();
    }

    public void setRotate(boolean rotate) {
        mRotate = rotate;
        invalidate();
    }

    private static int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }


}
