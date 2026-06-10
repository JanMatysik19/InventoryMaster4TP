package com.example.inventorymaster.Modules.Boxes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymaster.R;

import java.util.ArrayList;
import java.util.List;

public class BoxRecyclerAdapter extends RecyclerView.Adapter<BoxRecyclerAdapter.ViewHolder>{
    private final List<Box> data;
    private IItemClick itemClickEvent;
    private int pageCounter;

    public BoxRecyclerAdapter() {
        this.data = new ArrayList<>();
        this.pageCounter = 0;
    }

    public void appendPageData(List<Box> newData) {
        if(newData.isEmpty()) return;

        final var start = data.size();
        final var itemCount = newData.size();
        data.addAll(newData);
        pageCounter++;

        System.out.println("Strona " + pageCounter + ", nowe: " + newData.size());
        notifyItemRangeInserted(start, itemCount);
    }

    public void clearData() {
        final var start = 0;
        final var itemCount = data.size();
        data.clear();
        pageCounter = 0;
        notifyItemRangeRemoved(start, itemCount);
    }

    public int getPageToPull() {
        return pageCounter + 1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_universal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final var box = data.get(position);
        holder.setUniversalLabelText(box.getCode());
        holder.setClickHandler(v -> itemClickEvent.onItemClick(box, v));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView universalLabelTv;
        private final CardView universalCv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            universalCv = itemView.findViewById(R.id.universalCv);
            universalLabelTv = itemView.findViewById(R.id.universalLabelTv);
        }

        public void setUniversalLabelText(String text) {
            universalLabelTv.setText(text);
        }

        public void setClickHandler(View.OnClickListener listener) {
            universalCv.setOnClickListener(listener);
        }
    }

    public void setItemClickHandler(IItemClick handler) {
        itemClickEvent = handler;
    }

    public interface IItemClick {
        void onItemClick(Box box, View v);
    }
}
