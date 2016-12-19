package com.futureadymedia.alumni.widgets;

/**
 * Created by developer on 9/16/2016.
 */
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * A {@link ViewPager} that does not allow swipe gesture. Useful for multi-step
 * forms that require validation
 *
 * @author louielouie http://stackoverflow.com/a/9650884
 *
 */
public class SwipeableViewPager extends ViewPager
{

    private boolean enabled;

    public SwipeableViewPager(Context context)
    {
        super(context);
    }

    public SwipeableViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.enabled = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0)
    {
        // allow swiping to switch between pages
        if (this.enabled) {
            return super.onInterceptTouchEvent(arg0);
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // allow swiping to switch between pages
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}