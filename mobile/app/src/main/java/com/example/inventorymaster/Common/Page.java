package com.example.inventorymaster.Common;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public abstract class Page {
    private final LayoutInflater layoutInflater;
//    private final FrameLayout frameView;
    private final View pageView;

    protected Page(LayoutInflater layoutInflater, FrameLayout frameView, int layoutId) {
        this.layoutInflater = layoutInflater;
        pageView = layoutInflater.inflate(layoutId, frameView, false);
    }

    public void notifyOfNavigationSelection() { }

    public void notifyOfActivityResume() { }

    public View getPageView() {
        return pageView;
    }
}
