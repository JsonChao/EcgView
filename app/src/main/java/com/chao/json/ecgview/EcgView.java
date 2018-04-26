package com.chao.json.ecgview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Random;

/**
 * @author chao.qu
 * @date 2018/4/24
 */

public class EcgView extends View {

    public static final int STYLE_COMMON = 1;
    public static final int STYLE_SPLASH = 0;

    private Paint mPaint;
    private Path mPath;
    private int scrollValue;
    private Random random;
    private int tmp;
    private int randomWidthOne;
    private int randomWidthTwo;
    private int randomWidthThree;
    private int randomHeightOne;
    private int randomHeightTwo;
    private int randomHeightThree;
    private int randomHeightFour;
    private int width;
    private int height;
    private boolean isStart;
    private int ecgStyle;
    private int waveNum = 10;
    private long inValidateInterval = 10;

    public EcgView(Context context) {
        super(context);
        init();
    }

    public EcgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EcgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        if (ecgStyle == STYLE_COMMON) {
            tmp = width / 5;
        } else if (ecgStyle == STYLE_SPLASH){
            tmp = width / 10;
        }
        if (!isStart) {
            return;
        }
        if (ecgStyle == STYLE_COMMON) {
            commonStyle(canvas);
        } else if (ecgStyle == STYLE_SPLASH){
            splashStyle(canvas);
        }
    }

    /**
     * Splash style ecg view
     *
     * @param canvas Canvas
     */
    private void splashStyle(Canvas canvas) {
        mPath.reset();
        mPath.moveTo(0, height / 2);
        int waveTotalNum = 10;
        if (waveNum == waveTotalNum) {
            waveNum = 0;
        }
        waveNum++;
        for (int i = 0; i < waveNum; i++) {
            if (i <= 2 || i >= 7) {
                if (i == 9) {
                    mPath.lineTo(i * tmp + tmp, height / 2);
                } else {
                    mPath.lineTo(i * tmp + randomWidthThree(), height / 2);
                }
            } else {
                if (i % 2 == 1) {
                    mPath.lineTo(i * tmp + randomWidthOne(), randomHeightOne(height));
                    mPath.lineTo(i * tmp + randomWidthTwo(), randomHeightTwo(height));
                } else {
                    mPath.lineTo(i * tmp + randomWidthOne(), randomHeightThree(height));
                    mPath.lineTo(i * tmp + randomWidthTwo(), randomHeightFour(height));
                }
            }
        }
        canvas.drawPath(mPath, mPaint);
        postInvalidateDelayed(100);
    }

    /**
     * Common style ecg view
     *
     * @param canvas Canvas
     */
    @Deprecated
    private void commonStyle(Canvas canvas) {
        mPath.reset();
        mPath.moveTo(width, height / 2);
        int waveformTotal = 10;
        for (int i = 0; i < waveformTotal; i++) {
            if (i % 2 == 1) {
                mPath.lineTo(i * tmp + randomWidthOne(), randomHeightOne(height));
                mPath.lineTo(i * tmp + randomWidthTwo(), randomHeightTwo(height));
            } else {
                mPath.lineTo(i * tmp + randomWidthOne(), randomHeightThree(height));
                mPath.lineTo(i * tmp + randomWidthTwo(), randomHeightFour(height));
            }
        }
        canvas.drawPath(mPath, mPaint);
        int ecgWidth = 2 * width;
        if (scrollValue < ecgWidth) {
            scrollValue++;
            scrollBy(1, 0);
        } else {
            scrollValue = width;
            scrollBy(-1 * width, 0);
        }
        postInvalidateDelayed(inValidateInterval);
    }

    @IntDef(flag = true, value = {STYLE_COMMON, STYLE_SPLASH})
    @Retention(RetentionPolicy.SOURCE)
    private @interface EcgStyle{
    }

    public void setEcgStyle(@EcgStyle int ecgStyle) {
        this.ecgStyle = ecgStyle;
        invalidate();
    }

    public @EcgStyle int getEcgStyle() {
        return ecgStyle;
    }

    public long getInValidateInterval() {
        return inValidateInterval * 10;
    }

    /**
     * Set Invalidate Period
     *
     * @param inValidateInterval Invalidate Period / unit == ms
     */
    public void setInValidateInterval(long inValidateInterval) {
        this.inValidateInterval = inValidateInterval / 10;
    }

    /**
     * Start Ecg View
     */
    public void startEcgView() {
        isStart = true;
        scrollBy(-scrollValue, 0);
        scrollValue = 0;
        invalidate();
    }

    /**
     * Stop Ecg View
     */
    public void stopEcgView() {
        isStart = false;
        invalidate();
    }

    /**
     * Chang ecg shape
     */
    public void setRandomNumber() {
        randomWidthOne = random.nextInt(tmp / 3 - 1) + tmp / 4;
        randomWidthTwo = random.nextInt(tmp / 4) + 2 * tmp / 3;
        randomHeightOne = random.nextInt(height / 4 - 1);
        randomHeightTwo = random.nextInt(height / 4 - 1) + 3 * height / 4;
        randomHeightThree = random.nextInt(height / 4 - 1) + height / 4;
        randomHeightFour = random.nextInt(height / 4 - 1) + height / 2;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPath = new Path();
        random = new Random();
    }

    private int randomWidthOne() {
        if (randomWidthOne == 0) {
            randomWidthOne = random.nextInt(tmp / 3 - 1) + tmp / 4;
        }
        return randomWidthOne;
    }

    private int randomWidthTwo() {
        if (randomWidthTwo == 0) {
            randomWidthTwo = random.nextInt(tmp / 4) + 2 * tmp / 3;
        }
        return randomWidthTwo;
    }

    private int randomWidthThree() {
        if (randomWidthThree == 0) {
            randomWidthThree = random.nextInt(tmp / 3) + 2 * tmp / 3;
        }
        return randomWidthThree;
    }

    private float randomHeightOne(int height) {
        if (randomHeightOne == 0) {
            randomHeightOne = random.nextInt(height / 4 - 1);
        }
        return randomHeightOne;
    }

    private float randomHeightTwo(int height) {
        if (randomHeightTwo == 0) {
            randomHeightTwo = random.nextInt(height / 4 - 1) + 3 * height / 4;
        }
        return randomHeightTwo;
    }

    private float randomHeightThree(int height) {
        if (randomHeightThree == 0) {
            randomHeightThree = random.nextInt(height / 4 - 1) + height / 4;
        }
        return randomHeightThree;
    }

    private float randomHeightFour(int height) {
        if (randomHeightFour == 0) {
            randomHeightFour = random.nextInt(height / 4 - 1) + height / 2;
        }
        return randomHeightFour;
    }
}
