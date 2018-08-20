/*
 * Copyright(c) 2018 Habo Shen <ayst.shen@foxmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ayst.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by shenhaibo on 2018/5/11.
 */

public class ThreeColorIndicator extends View {
    // First range color
    private int mFirstRangeColor = 0xffff0000;

    // Second range color
    private int mSecondRangeColor = 0xfffffc00;

    // Three range color
    private int mThreeRangeColor = 0xff29fd2f;

    // Progress bar height
    private float mProgressBarHeight = 20;

    // First Range Indicator text
    private String mFirstRangeText = "First";

    // Second Range Indicator text
    private String mSecondRangeText = "Second";

    // Three Range Indicator text
    private String mThreeRangeText = "Three";

    // Indicator text
    private String mIndicatorText = "Text";

    // Indicator text color
    private int mIndicatorTextColor = 0xffffffff;

    // Indicator text size
    private float mIndicatorTextSize = 30.0f;

    // Indicator text width
    private float mIndicatorTextWidth = 80.0f;

    // Indicator text height
    private float mIndicatorTextHeight = 35.0f;

    // Current value
    private int mValue = 80;

    // Min value
    private int mMin = 0;

    // Max value
    private int mMax = 100;

    // This is a percentage, starting with the minimum value, showing first color within this percentage
    private int mFirstRange = 30;

    // This is a percentage, starting with the first range, showing second color within this percentage
    private int mSecondRange = 20;

    // Progress radius
    private float mProgressRadius = 3.0f;

    // Indicator icon id
    private int mIndicatorDrawableId;

    private int mIndicatorIconWidth = 20;
    private int mIndicatorIconHeight = 20;

    private Paint mPaint;
    private Paint mIndicatorTextPaint;

    public ThreeColorIndicator(Context context) {
        this(context, null);
    }

    public ThreeColorIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThreeColorIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ThreeColorIndicator, 0, 0);

        mFirstRangeColor = a.getColor(R.styleable.ThreeColorIndicator_tci_firstColor, mFirstRangeColor);
        mSecondRangeColor = a.getColor(R.styleable.ThreeColorIndicator_tci_secondColor, mSecondRangeColor);
        mThreeRangeColor = a.getColor(R.styleable.ThreeColorIndicator_tci_threeColor, mThreeRangeColor);
        mMax = a.getInteger(R.styleable.ThreeColorIndicator_tci_max, mMax);
        mMin = a.getInteger(R.styleable.ThreeColorIndicator_tci_min, mMin);
        mProgressBarHeight = a.getDimension(R.styleable.ThreeColorIndicator_tci_progressHeight, mProgressBarHeight);
        mValue = a.getInteger(R.styleable.ThreeColorIndicator_tci_value, mValue);
        mFirstRange = a.getInteger(R.styleable.ThreeColorIndicator_tci_firstRange, mFirstRange);
        mSecondRange = a.getInteger(R.styleable.ThreeColorIndicator_tci_secondRange, mSecondRange);
        mProgressRadius = a.getDimension(R.styleable.ThreeColorIndicator_tci_progressRadius, mProgressRadius);
        mFirstRangeText = a.getString(R.styleable.ThreeColorIndicator_tci_firstText);
        mSecondRangeText = a.getString(R.styleable.ThreeColorIndicator_tci_secondText);
        mThreeRangeText = a.getString(R.styleable.ThreeColorIndicator_tci_threeText);
        mIndicatorText = a.getString(R.styleable.ThreeColorIndicator_tci_indicatorText);
        mIndicatorTextColor = a.getColor(R.styleable.ThreeColorIndicator_tci_indicatorTextColor, mIndicatorTextColor);
        mIndicatorTextSize = a.getDimension(R.styleable.ThreeColorIndicator_tci_indicatorTextSize, mIndicatorTextSize);
        mIndicatorTextWidth = a.getDimension(R.styleable.ThreeColorIndicator_tci_indicatorTextWidth, mIndicatorTextWidth);
        mIndicatorTextHeight = a.getDimension(R.styleable.ThreeColorIndicator_tci_indicatorTextHeight, mIndicatorTextHeight);
        mIndicatorDrawableId = a.getResourceId(R.styleable.ThreeColorIndicator_tci_indicatorDrawable, R.mipmap.ic_three_color_indicator);
        a.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint();

        mIndicatorTextPaint = new Paint();
        mIndicatorTextPaint.setColor(mIndicatorTextColor);
        mIndicatorTextPaint.setTextSize(mIndicatorTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.reset();
        int range = mMax - mMin;

        // Drawing three range progress
        mPaint.setColor(mThreeRangeColor);
        RectF rect = new RectF();
        rect.top = mIndicatorTextHeight + dp2px(5);
        rect.left = mIndicatorTextWidth / 2;
        rect.right = getWidth() - mIndicatorTextWidth / 2;
        rect.bottom = rect.top + mProgressBarHeight;
        canvas.drawRoundRect(rect, mProgressRadius, mProgressRadius, mPaint);

        // Drawing second range progress
        float colorBarWidth = getWidth() - mIndicatorTextWidth;
        mPaint.setColor(mSecondRangeColor);
        rect.right = (float) colorBarWidth * (mFirstRange + mSecondRange) / 100 + mIndicatorTextWidth / 2;
        canvas.drawRoundRect(rect, mProgressRadius, mProgressRadius, mPaint);

        // Drawing three range text
        mIndicatorTextPaint.setColor(mThreeRangeColor);
        canvas.drawText(mThreeRangeText, rect.right, mIndicatorTextHeight,
                mIndicatorTextPaint);

        // Drawing first range progress
        mPaint.setColor(mFirstRangeColor);
        rect.right = (float) colorBarWidth * mFirstRange / 100 + mIndicatorTextWidth / 2;
        canvas.drawRoundRect(rect, mProgressRadius, mProgressRadius, mPaint);

        // Drawing second range text
        mIndicatorTextPaint.setColor(mSecondRangeColor);
        canvas.drawText(mSecondRangeText, rect.right, mIndicatorTextHeight,
                mIndicatorTextPaint);

        // Drawing first range text
        mIndicatorTextPaint.setColor(mFirstRangeColor);
        canvas.drawText(mFirstRangeText, rect.left, mIndicatorTextHeight,
                mIndicatorTextPaint);

        // Drawing Indicator icon
        int valuePer = (mValue-mMin)*100/range;
        if (valuePer < mFirstRange) {
            mPaint.setColorFilter(new PorterDuffColorFilter(mFirstRangeColor, PorterDuff.Mode.SRC_IN));
        } else if (valuePer < mFirstRange + mSecondRange) {
            mPaint.setColorFilter(new PorterDuffColorFilter(mSecondRangeColor, PorterDuff.Mode.SRC_IN));
        } else {
            mPaint.setColorFilter(new PorterDuffColorFilter(mThreeRangeColor, PorterDuff.Mode.SRC_IN));
        }
        float IndicatorCenter = (float) colorBarWidth * valuePer / 100 + mIndicatorTextWidth / 2;
        float textWidth = mIndicatorTextPaint.measureText(mIndicatorText);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mIndicatorDrawableId);
        canvas.drawBitmap(bitmap, IndicatorCenter - mIndicatorIconWidth / 2,
                rect.bottom, mPaint);
        bitmap.recycle();

        // Drawing Indicator text
        mIndicatorTextPaint.setColor(mIndicatorTextColor);
        canvas.drawText(mIndicatorText, IndicatorCenter - textWidth / 2,
                rect.bottom + mIndicatorIconHeight + mIndicatorTextPaint.getTextSize(),
                mIndicatorTextPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mIndicatorDrawableId);
        mIndicatorIconWidth = bitmap.getWidth();
        mIndicatorIconHeight = bitmap.getHeight();
        bitmap.recycle();

        int width = widthMeasureSpec;
        int height = (int) (mProgressBarHeight + mIndicatorIconHeight + mIndicatorTextHeight *2 + dp2px(10));

        setMeasuredDimension(measureSpecHandler(widthMeasureSpec, width), measureSpecHandler(heightMeasureSpec, height));
    }

    private int measureSpecHandler(int measureSpec, int defaultSize) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }

    public void update(int progress, String IndicatorText) {
        setValue(progress);
        setIndicatorText(IndicatorText);
        invalidate();
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        this.mValue = value > mMax ? mMax : (value < mMin ? mMin : value);
    }

    public int getFirstRange() {
        return mFirstRange;
    }

    public void setFirstRange(int firstRange) {
        this.mFirstRange = firstRange;
        invalidate();
    }

    public int getSecondRange() {
        return mSecondRange;
    }

    public void setSecondRange(int secondRange) {
        this.mSecondRange = secondRange;
        invalidate();
    }

    public int getFirstRangeColor() {
        return mFirstRangeColor;
    }

    public void setFirstRangeColor(int mFirstRangeColor) {
        this.mFirstRangeColor = mFirstRangeColor;
    }

    public int getSecondRangeColor() {
        return mSecondRangeColor;
    }

    public void setSecondRangeColor(int mSecondRangeColor) {
        this.mSecondRangeColor = mSecondRangeColor;
    }

    public int getThreeRangeColor() {
        return mThreeRangeColor;
    }

    public void setThreeRangeColor(int mThreeRangeColor) {
        this.mThreeRangeColor = mThreeRangeColor;
    }

    public float getProgressBarHeight() {
        return mProgressBarHeight;
    }

    public void setProgressBarHeight(float mProgressBarHeight) {
        this.mProgressBarHeight = mProgressBarHeight;
    }

    public String getIndicatorText() {
        return mIndicatorText;
    }

    public void setIndicatorText(String mIndicatorText) {
        this.mIndicatorText = mIndicatorText;
    }

    public int getIndicatorTextColor() {
        return mIndicatorTextColor;
    }

    public void setIndicatorTextColor(int mIndicatorTextColor) {
        this.mIndicatorTextColor = mIndicatorTextColor;
    }

    public float getIndicatorTextSize() {
        return mIndicatorTextSize;
    }

    public void setIndicatorTextSize(float mIndicatorTextSize) {
        this.mIndicatorTextSize = mIndicatorTextSize;
    }

    public float getIndicatorTextWidth() {
        return mIndicatorTextWidth;
    }

    public void setIndicatorTextWidth(float mIndicatorTextWidth) {
        this.mIndicatorTextWidth = mIndicatorTextWidth;
    }

    public float getIndicatorTextHeight() {
        return mIndicatorTextHeight;
    }

    public void setIndicatorTextHeight(float mIndicatorTextHeight) {
        this.mIndicatorTextHeight = mIndicatorTextHeight;
    }

    public int getMinProgress() {
        return mMin;
    }

    public void setMinProgress(int mMinProgress) {
        this.mMin = mMinProgress;
    }

    public int getMaxProgress() {
        return mMax;
    }

    public void setMaxProgress(int mMaxProgress) {
        this.mMax = mMaxProgress;
    }

    public float getProgressRadius() {
        return mProgressRadius;
    }

    public void setProgressRadius(float mProgressRadius) {
        this.mProgressRadius = mProgressRadius;
    }

    public int getIndicatorDrawableId() {
        return mIndicatorDrawableId;
    }

    public void setIndicatorDrawableId(int mIndicatorDrawableId) {
        this.mIndicatorDrawableId = mIndicatorDrawableId;
    }

    public int getIndicatorIconWidth() {
        return mIndicatorIconWidth;
    }

    public void setIndicatorIconWidth(int mIndicatorIconWidth) {
        this.mIndicatorIconWidth = mIndicatorIconWidth;
    }

    public int getIndicatorIconHeight() {
        return mIndicatorIconHeight;
    }

    public void setIndicatorIconHeight(int mIndicatorIconHeight) {
        this.mIndicatorIconHeight = mIndicatorIconHeight;
    }
}
