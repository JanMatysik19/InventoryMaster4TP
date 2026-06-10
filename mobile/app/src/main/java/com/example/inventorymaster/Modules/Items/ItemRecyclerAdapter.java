package com.example.inventorymaster.Modules.Items;

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

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder>{
    private final List<Item> data;
    private IItemClickEvent itemClickEvent;
    private int pageCounter;

    public ItemRecyclerAdapter() {
        this.data = new ArrayList<>();
        this.pageCounter = 0;
    }

    public synchronized void appendPageData(List<Item> newData) {
        if(newData.isEmpty()) return;

        final var start = data.size();
        final var itemCount = newData.size();
        data.addAll(newData);
        pageCounter++;

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
                .inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final var item = data.get(position);
        holder.setLabelText(item.getFeaturesCode());
        holder.setPriceText(item.getPrice());
        holder.setQuantityText(item.getQuantity());
        holder.setDescriptionText(item.getDescription());
        holder.setClickHandler(v -> {
            itemClickEvent.onItemClick(item, v);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView recyclerItemDescriptionTv;
        private final CardView recyclerItemCv;
        private final TextView recyclerItemLabelTv;
        private final TextView recyclerItemPriceTv;
        private final TextView recyclerItemQuantityTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerItemCv = itemView.findViewById(R.id.recyclerItemCv);
            recyclerItemDescriptionTv = itemView.findViewById(R.id.recyclerItemDescriptionTv);
            recyclerItemLabelTv = itemView.findViewById(R.id.recyclerItemLabelTv);
            recyclerItemPriceTv = itemView.findViewById(R.id.recyclerItemPriceTv);
            recyclerItemQuantityTv = itemView.findViewById(R.id.recyclerItemQuantityTv);
        }

        public void setLabelText(String text) {
            recyclerItemLabelTv.setText(text);
        }

        public void setPriceText(String text) {
            if (text != null && !text.isEmpty()) {
                recyclerItemPriceTv.setText(text + " PLN");
                recyclerItemPriceTv.setVisibility(View.VISIBLE);
            } else {
                recyclerItemPriceTv.setVisibility(View.GONE);
            }
        }

        public void setQuantityText(int quantity) {
            recyclerItemQuantityTv.setText("(" + quantity + " szt.)");
        }

        public void setDescriptionText(String text) {
            recyclerItemDescriptionTv.setText(text);
        }

        public void setClickHandler(View.OnClickListener listener) {
            recyclerItemCv.setOnClickListener(listener);
        }
    }

    public void setItemClickHandler(IItemClickEvent handler) {
        itemClickEvent = handler;
    }

    public interface IItemClickEvent {
        void onItemClick(Item item, View v);
    }
}
