package com.futureadymedia.alumni.fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.adapter.MyFragmentPagerAdapter;
import com.futureadymedia.alumni.adapter.MyPagerAdapter;
import com.futureadymedia.alumni.widgets.NonSwipeableViewPager;
import com.futureadymedia.alumni.widgets.SwipeableViewPager;

/**
 * Created by developer on 11/7/2016.
 */
public class FragmentSplash extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    Context context;
    View view;
    private SwipeableViewPager SwipeableViewPager;
    private MyFragmentPagerAdapter adapterViewPager;
    private int loadingPos = 0;
    public static ImageView ivArrow, ivActive1, ivActive2, ivActive3, ivActive4, ivActive5;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = LayoutInflater.from(context).inflate(R.layout.fragment_splash_pager, null);

        findId();
        setListener();
        setFont();

        SwipeableViewPager.addOnPageChangeListener(this);
        SwipeableViewPager.setOffscreenPageLimit(5);
        adapterViewPager = new MyFragmentPagerAdapter(getChildFragmentManager());
        SwipeableViewPager.setAdapter(adapterViewPager);
        SwipeableViewPager.setCurrentItem(0);
        SwipeableViewPager.setPagingEnabled(true);


       // ActiveTab(0);
        return view;
    }

    @Override
    public void findId() {
        SwipeableViewPager = (SwipeableViewPager) view.findViewById(R.id.pager);
        ivArrow = (ImageView) view.findViewById(R.id.ivArrow);
        ivActive1 = (ImageView) view.findViewById(R.id.ivActive1);
        ivActive2 = (ImageView) view.findViewById(R.id.ivActive2);
        ivActive3 = (ImageView) view.findViewById(R.id.ivActive3);
        ivActive4 = (ImageView) view.findViewById(R.id.ivActive4);
        ivActive5 = (ImageView) view.findViewById(R.id.ivActive5);
    }

    @Override
    public void setListener() {
        ivArrow.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivArrow:
                if(loadingPos<4)
                {
                    SwipeableViewPager.setCurrentItem((loadingPos+1), true);
                }
                else
                {
                    SwipeableViewPager.setCurrentItem((0), true);
                }

            break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.e("page selected", ""+position);
        loadingPos = position;
        ivActive1.setImageResource(R.drawable.nav_dot_grey);
        ivActive2.setImageResource(R.drawable.nav_dot_grey);
        ivActive3.setImageResource(R.drawable.nav_dot_grey);
        ivActive4.setImageResource(R.drawable.nav_dot_grey);
        ivActive5.setImageResource(R.drawable.nav_dot_grey);
        ivArrow.setImageResource(R.drawable.arrow_2_blue);
        switch (position)
        {
            case 0:
                ivArrow.setImageResource(R.drawable.arrow_2);
                ivActive1.setImageResource(R.drawable.nav_dot_white);
                break;

            case 1:
                ivActive2.setImageResource(R.drawable.nav_dot_blue);
                break;

            case 2:
                ivActive3.setImageResource(R.drawable.nav_dot_blue);
                break;

            case 3:
                ivActive4.setImageResource(R.drawable.nav_dot_blue);
                break;

            case 4:
                ivActive5.setImageResource(R.drawable.nav_dot_blue);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
