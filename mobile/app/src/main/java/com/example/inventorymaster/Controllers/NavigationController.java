package com.example.inventorymaster.Controllers;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.inventorymaster.R;

public class NavigationController {
    private final View navView;
    private final LayoutInflater inflater;
    private final FrameLayout frameView;
    

    public NavigationController(View navView, FrameLayout frameView, LayoutInflater inflater) {
        this.navView = navView;
        this.inflater = inflater;
        this.frameView = frameView;
        setupListeners();
    }

    private void setupListeners() {
        for (Page page : Page.values()) {
            navView.findViewById(page.cardId).setOnClickListener(v -> navigateTo(page));
        }
    }

    public void navigateTo(Page page) {
        final var view = inflater.inflate(page.layout(), frameView, false);
        frameView.removeAllViews();
        frameView.addView(view);

        updateNavigationUI(page);
    }

    private void updateNavigationUI(Page selectedPage) {
        Context context = navView.getContext();

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnBackground, typedValue, true);
        int colorOnBackground = typedValue.data;

        int colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary);
        int colorGray = ContextCompat.getColor(context, R.color.gray);

        for (Page page : Page.values()) {
            ImageView iv = navView.findViewById(page.iconId);
            TextView tv = navView.findViewById(page.textId);

            if (page == selectedPage) {
                iv.setColorFilter(colorPrimary);
                tv.setTextColor(colorOnBackground);
            } else {
                iv.setColorFilter(colorGray);
                tv.setTextColor(colorGray);
            }
        }
    }

    public enum Page {
        START(R.layout.page_start, R.id.menuStartCv, R.id.menuStartIv, R.id.menuStartTv),
        STRUCTURE(R.layout.page_structure, R.id.menuStructureCv, R.id.menuStructureIv, R.id.menuStructureTv),
        ITEMS(R.layout.page_items, R.id.menuItemsCv, R.id.menuItemsIv, R.id.menuItemsTv),
        CATEGORIES(R.layout.page_categories, R.id.menuCategoriesCv, R.id.menuCategoriesIv, R.id.menuCategoriesTv);

        private final int layoutR;
        private final int cardId;
        private final int iconId;
        private final int textId;

        Page(int layoutR, int cardId, int iconId, int textId) {
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
