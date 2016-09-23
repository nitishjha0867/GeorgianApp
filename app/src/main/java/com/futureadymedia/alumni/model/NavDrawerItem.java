package com.futureadymedia.alumni.model;

/**
 * Created by developer on 9/12/2016.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    public int icons;


    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcons(int icons) {
        this.icons = icons;
    }

    public int getIcons() {
        return icons;
    }
}
