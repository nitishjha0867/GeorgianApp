package com.futureadymedia.alumni.widgets;

/**
 * Created by developer on 9/16/2016.
 */
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * A {@link ViewPager} that does not allow swipe gesture. Useful for multi-step
 * forms that require validation
 *
 * @author louielouie http://stackoverflow.com/a/9650884
 *
 */
public class NonSwipeableViewPager extends ViewPager
{

    public NonSwipeableViewPager(Context context)
    {
        super(context);
    }

    public NonSwipeableViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0)
    {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // Never allow swiping to switch between pages
        return false;
    }
}