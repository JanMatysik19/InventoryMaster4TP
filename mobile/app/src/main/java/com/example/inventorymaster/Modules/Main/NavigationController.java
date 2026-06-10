package com.example.inventorymaster.Modules.Main;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.inventorymaster.Common.Page;
import com.example.inventorymaster.R;

import java.util.Map;

public class NavigationController {
    private final View navView;
    private final Map<NavigationPage, Page> pages;
    private final FrameLayout frameView;
    

    public NavigationController(View navView, FrameLayout frameView, Map<NavigationPage, Page> pages) {
        this.navView = navView;
        this.frameView = frameView;
        this.pages = pages;
        setupListeners();
    }

    private void setupListeners() {
        for (NavigationPage navigationPage : NavigationPage.values()) {
            navView.findViewById(navigationPage.cardId).setOnClickListener(v -> navigateTo(navigationPage));
        }
    }

    public void setOnScannerClickListener(View.OnClickListener listener) {
        navView.findViewById(R.id.menuBarcodeCv).setOnClickListener(listener);
    }

    public void navigateTo(NavigationPage navigationPage) {
        Page page;
        try {
            page = pages.get(navigationPage);
        } catch (Exception ignore) {
            return;
        }
        if(page == null) return;

//        final var view = inflater.inflate(navigationPage.layout(), frameView, false);
        final var view = page.getPageView();
        page.notifyOfNavigationSelection();
        frameView.removeAllViews();
        frameView.addView(view);

        updateNavigationUI(navigationPage);
    }

    private void updateNavigationUI(NavigationPage selectedNavigationPage) {
        Context context = navView.getContext();

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnBackground, typedValue, true);
        int colorOnBackground = typedValue.data;

        int colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary);
        int colorGray = ContextCompat.getColor(context, R.color.gray);

        for (NavigationPage navigationPage : NavigationPage.values()) {
            ImageView iv = navView.findViewById(navigationPage.iconId);
            TextView tv = navView.findViewById(navigationPage.textId);

            if (navigationPage == selectedNavigationPage) {
                iv.setColorFilter(colorPrimary);
                tv.setTextColor(colorOnBackground);
            } else {
                iv.setColorFilter(colorGray);
                tv.setTextColor(colorGray);
            }
        }
    }

    public enum NavigationPage {
        START(R.layout.page_start, R.id.menuStartCv, R.id.menuStartIv, R.id.menuStartTv),
        STRUCTURE(R.layout.page_structure, R.id.menuStructureCv, R.id.menuStructureIv, R.id.menuStructureTv),
        ITEMS(R.layout.page_items, R.id.menuItemsCv, R.id.menuItemsIv, R.id.menuItemsTv);

        private final int layoutR;
        private final int cardId;
        private final int iconId;
        private final int textId;

        NavigationPage(int layoutR, int cardId, int iconId, int textId) {
            this.layoutR = layoutR;
            this.cardId = cardId;
            this.iconId = iconId;
            this.textId = textId;
        }

        public int layout() {
            return layoutR;
        }
    }
}
