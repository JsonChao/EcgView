package com.chao.json.ecgview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * @author chao.qu
 * @date 2018/4/24
 */

public class EcgView extends View {

    private Paint mPaint;
    private Path mPath;
    private int scrollValue;
    private Random random;
    private int tmp;
    private int randomWidthOne;
    private int randomWidthTwo;
    private int randomWidthThree;
    private int randomWidthFour;
    private int randomHeightOne;
    private int randomHeightTwo;
    private int randomHeightThree;
    private int height;
    private boolean isStart;

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
        int width = getWidth();
        height = getHeight();
        tmp = width / 5;
        if (!isStart) {
            return;
        }
        mPath.reset();
        mPath.moveTo(width, height / 2);
        int waveformTotal = 10;
        for (int i = 0; i < waveformTotal; i++) {
            mPath.lineTo(width + i * tmp + randomWidthOne(), 100);
            mPath.lineTo(width + i * tmp + randomWidthTwo(), randomHeightOne(height));
            mPath.lineTo(width + i * tmp + randomWidthThree(), randomHeightTwo(height));
            mPath.lineTo(width + i * tmp + randomWidthFour(), randomHeightThree(height));
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
        postInvalidateDelayed(10);
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
        randomWidthOne = random.nextInt(tmp / 30 - 1) + tmp / 30;
        randomWidthTwo = random.nextInt(2 * tmp / 15 - 1) + tmp / 15;
        randomWidthThree = random.nextInt(tmp / 3 - 1) + tmp / 5;
        randomWidthFour = random.nextInt(7 * tmp / 15 - 1) + 8 * tmp / 15;
        randomHeightOne = random.nextInt(height / 3 - 1) + height / 3;
        randomHeightTwo = random.nextInt(height / 3 - 1) + height / 3;
        randomHeightThree = random.nextInt(height / 3 - 1) + height / 3;
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

    private int randomWidthFour() {
        if (randomWidthFour == 0) {
            randomWidthFour = random.nextInt(7 * tmp / 15 - 1) + 8 * tmp / 15;
        }
        return randomWidthFour;
    }

    private int randomWidthThree() {
        if (randomWidthThree == 0) {
            randomWidthThree = random.nextInt(tmp / 3 - 1) + tmp / 5;
        }
        return randomWidthThree;
    }

    private int randomWidthTwo() {
        if (randomWidthTwo == 0) {
            randomWidthTwo = random.nextInt(2 * tmp / 15 - 1) + tmp / 15;
        }
        return randomWidthTwo;
    }

    private int randomWidthOne() {
        if (randomWidthOne == 0) {
            randomWidthOne = random.nextInt(tmp / 30 - 1) + tmp / 30;
        }
        return randomWidthOne;
    }

    private float randomHeightOne(int height) {
        if (randomHeightOne == 0) {
            randomHeightOne = random.nextInt(height / 3 - 1) + height / 3;
        }
        return randomHeightOne;
    }

    private float randomHeightTwo(int height) {
        if (randomHeightTwo == 0) {
            randomHeightTwo = random.nextInt(height / 3 - 1) + height / 3;
        }
        return randomHeightTwo;
    }

    private float randomHeightThree(int height) {
        if (randomHeightThree == 0) {
            randomHeightThree = random.nextInt(height / 3 - 1) + height / 3;
        }
        return randomHeightThree;
    }
}
