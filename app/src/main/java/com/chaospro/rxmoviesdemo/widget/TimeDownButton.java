package com.chaospro.rxmoviesdemo.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by chaosboy on 2017/5/8.
 */

public class TimeDownButton extends AppCompatButton {
    private static final String TAG = TimeDownButton.class.getSimpleName();
    private int mLastX, mLastY;
    private static final int TOUCH_SLOP = 100;
    private boolean isMoved;

    //倒计时的时间
    private int mTimeDownSecond = 10;
    private int mCount;
    private int DETAY_TIME = 1;

    public CountDownTimer mCountDownTimer;
    private CountDownCompleteListener listener;


    private Runnable mClickDelayRunnable = new Runnable() {
        @Override
        public void run() {
            if (mCount == DETAY_TIME) {
                mCount = 0;
                performClick();
            } else {
                mCount++;
                postDelayed(mClickDelayRunnable, DETAY_TIME * 1000);
            }
        }
    };

    public void addCompleteListener(CountDownCompleteListener listener) {
        this.listener = listener;
    }


    public TimeDownButton(Context context) {
        super(context);
        initCountTime();
    }

    public TimeDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCountTime();
    }

    public TimeDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCountTime();
    }

    private void initCountTime() {

        mCountDownTimer = new CountDownTimer(mTimeDownSecond * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                changeStatus(millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                if (listener != null) {
                    listener.complete();
                }
                resetStatus("Resend");
            }
        };
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return true;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                isMoved = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isMoved)
                    break;
                if (Math.abs(mLastX - x) > TOUCH_SLOP || Math.abs(mLastY - y) > TOUCH_SLOP) {
                    isMoved = true;
                    removeCallbacks(mClickDelayRunnable);
                }
                break;
            case MotionEvent.ACTION_UP:
                removeCallbacks(mClickDelayRunnable);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private void changeStatus(long time) {
        setEnabled(false);
        setText(String.format("%d s", time));
    }

    private void resetStatus(String msg) {
        setEnabled(true);
        setText(msg);
    }

    private void resetStatus(@StringRes int msg) {
        setEnabled(true);
        setText(msg);
    }

    public interface CountDownCompleteListener {
        void complete();
    }
}
