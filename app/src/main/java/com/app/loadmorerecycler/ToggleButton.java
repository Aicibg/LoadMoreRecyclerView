package com.app.loadmorerecycler;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

public class ToggleButton extends ViewGroup {
    private Context mContext;

    private Scroller mScroller;
    private int mLastX;
    private int mTouchSlop;

    //开关是否打开
    private boolean isOpen;
    //是否是一次有效的开关操作
    private boolean isValidToggle;

    private ToggleListener mToggleListener;

    public ToggleButton(Context context) {
        this(context, null);
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        init();
    }

    private void init() {
        //滑动距离，表示手指要滑动多远的距离才开始移动控件
        mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        mScroller = new Scroller(mContext);
        ImageView slide = new ImageView(mContext);
        slide.setBackgroundResource(R.mipmap.slide);
        slide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    mScroller.startScroll(getScrollX(), 0, getMeasuredWidth() / 2, 0, 500);
                } else {
                    mScroller.startScroll(getScrollX(), 0, -getMeasuredWidth() / 2, 0, 500);
                }
                isOpen = !isOpen;
                isValidToggle = true;
                invalidate();
            }
        });
        setBackgroundResource(R.mipmap.background);
        addView(slide);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view = getChildAt(0);
        view.layout(0, 0, getMeasuredWidth() / 2, getMeasuredHeight());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = true;
        int x = (int) ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(x - mLastX) > mTouchSlop) {
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        mLastX = x;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        isValidToggle = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = mLastX - x;
                //边界检测判断，防止滑块越界（向左滑动到距离view左边缘小于view的宽度的4分之1时，子view直接滑动到view左边缘）
                if (deltaX + getScrollX() > 0) {
                    scrollTo(0, 0);
                    return true;
                } else if (deltaX + getScrollX() + getMeasuredWidth() / 2 < 0) {//向右滑动到超过view一半的距离之后（让子view直接滑动到view的右边缘并停止）
                    scrollTo(-getMeasuredWidth() / 2, 0);
                    return true;
                }
                scrollBy(deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                smoothScroll();
                break;
        }
        mLastX = x;
        return super.onTouchEvent(event);
    }

    private void smoothScroll() {
        int deltaX = 0;
        if (getScrollX() < -getMeasuredWidth() / 4) {
            deltaX = -getScrollX() - getMeasuredWidth() / 2;
            if (!isOpen) {
                isOpen = true;
                isValidToggle = true;
            }
        }

        if (getScrollX() >= -getMeasuredWidth() / 4) {
            deltaX = -getScrollX();
            if (isOpen) {
                isOpen = false;
                isValidToggle = true;
            }
        }
        mScroller.startScroll(getScrollX(), 0, deltaX, 0, 500);
        invalidate();//调用invalidate()方法会自动调用computeScroll()
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        } else {
            //滑动结束时
            if (isValidToggle) {
                if (mToggleListener != null) {
                    mToggleListener.onToggled(isOpen);
                    Log.e("isOpen", isOpen + "");
                }
            }
        }
    }

    public void setOnToggledListener(ToggleListener toggledListener) {
        mToggleListener = toggledListener;
    }
}
