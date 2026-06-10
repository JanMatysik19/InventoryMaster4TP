package com.example.inventorymaster.Modules.ItemInstances;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymaster.Modules.Items.Item;
import com.example.inventorymaster.R;
import com.example.inventorymaster.Utils.Stringer;

import java.util.ArrayList;
import java.util.List;

public class ItemInstanceRecyclerAdapter extends RecyclerView.Adapter<ItemInstanceRecyclerAdapter.ViewHolder>{
    private final List<ItemInstance> data;
    private IItemClickEvent itemClickEvent;
    private final Item item;
    private int pageCounter;

    public ItemInstanceRecyclerAdapter(Item item) {
        this.data = new ArrayList<>();
        this.item = item;
        this.pageCounter = 0;
    }

    public synchronized void appendPageData(List<ItemInstance> newData) {
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
        notifyDataSetChanged();
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
        final var itemInstance = data.get(position);
        var label = "ITEM-" + Stringer.padLeft(itemInstance.getSequenceNumber(), 3);
        if (itemInstance.getBoxCode() != null && !itemInstance.getBoxCode().isEmpty()) {
            label += " (" + itemInstance.getBoxCode() + ")";
        } else {
            label += " (BRAK)";
        }
        holder.setLabelText(label);
        holder.setClickHandler(v -> {
            itemClickEvent.onItemClick(this.item, itemInstance, v);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView recyclerItemCv;
        private final TextView recyclerItemLabelTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerItemCv = itemView.findViewById(R.id.universalCv);
            recyclerItemLabelTv = itemView.findViewById(R.id.universalLabelTv);
        }

        public void setLabelText(String text) {
            recyclerItemLabelTv.setText(text);
        }

        public void setClickHandler(View.OnClickListener listener) {
            recyclerItemCv.setOnClickListener(listener);
        }
    }

    public void setItemClickHandler(IItemClickEvent handler) {
        itemClickEvent = handler;
    }

    public interface IItemClickEvent {
        void onItemClick(Item item, ItemInstance itemInstance, View v);
    }
}
