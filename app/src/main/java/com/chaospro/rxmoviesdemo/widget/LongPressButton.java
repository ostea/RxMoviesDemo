package com.chaospro.rxmoviesdemo.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by chaosboy on 2017/5/8.
 */

public class LongPressButton extends AppCompatButton {


    private int mLastMoveX, mLastMoveY;
    //是否移动
    private boolean isMoved;
    //移动的slop
    private static final int TOUCH_SLOP = 100;
    private int count;
    //长按多少秒进入倒计时
    private int longPressSeconds = 3;
    //倒计时秒数
    private int countDownSeconds = 60;
    public CountDownTimer timer;


    private Runnable mLongPressRunnable = new Runnable() {
        @Override
        public void run() {
            if (count == longPressSeconds - 1) {
                countDownTime(getCountDownSeconds());
                count = 0;
                performLongClick();
                return;
            } else {
                setText(getLongPressSeconds() - 1 - count + " 秒");
                count++;
                postDelayed(mLongPressRunnable, 1000);
            }
        }
    };


    public LongPressButton(Context context) {
        super(context);
        initCountDownTimer();
    }

    public LongPressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCountDownTimer();
    }

    public LongPressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCountDownTimer();
    }

    private void initCountDownTimer() {
        timer = new CountDownTimer(countDownSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setText(millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                complete();
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
                mLastMoveX = x;
                mLastMoveY = y;
                isMoved = false;
                startLongPress(longPressSeconds);
                postDelayed(mLongPressRunnable, 1000);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isMoved) {
                    break;
                }
                if (Math.abs(mLastMoveX - x) > TOUCH_SLOP || Math.abs(mLastMoveY - y) > TOUCH_SLOP) {
                    isMoved = true;
                    removeCallbacks(mLongPressRunnable);
                    reset();
                }
                break;
            case MotionEvent.ACTION_UP:
                removeCallbacks(mLongPressRunnable);
                reset();
                break;
            default:
                break;
        }
        return true;
    }

    //重整旗鼓
    private void reset() {
        setEnabled(true);
        setText("发送");
        count = 0;
    }

    private void complete() {
        setEnabled(true);
        setText("再次发送");
    }

    //开始长按
    private void startLongPress(int s) {
        setEnabled(true);
        setText(String.format("%d 秒", s));
        count = 0;
    }


    public int getLongPressSeconds() {
        return longPressSeconds;
    }

    public void setLongPressSeconds(int longPressSeconds) {
        this.longPressSeconds = longPressSeconds;
    }

    public int getCountDownSeconds() {
        return countDownSeconds;
    }

    public void setCountDownSeconds(int countDownSeconds) {
        this.countDownSeconds = countDownSeconds;
    }

    /**
     * 倒计时
     */
    private void countDownTime(int secondsNumMsg) {
        setEnabled(false);
        setText(String.format("%d 秒", secondsNumMsg));
    }


}
