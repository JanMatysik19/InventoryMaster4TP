package com.example.inventorymaster.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerHelper {
    public static RecyclerView.OnScrollListener pullNeedCheckHandler(int before, Runnable need) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                final var layout = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(layout == null) return;

                final var items = layout.getItemCount();
                final var lastVisibleItem = layout.findLastVisibleItemPosition();
                if(lastVisibleItem >= items - before) need.run();
            }
        };
    }
}
