package com.futureadymedia.alumni.listeners;

import java.util.List;

/**
 * Created by developer on 9/16/2016.
 */
public interface FragmentChangeListener {

    abstract void onFragmentChange(int position);
    abstract void onFragmentChange(int position, List<String> stringList);
    //abstract void onFragmentChange(List<DrawableModel> list, int position);
    //abstract void onFragmentChange(DrawableModel model, int position);
}