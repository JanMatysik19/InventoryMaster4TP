package com.example.inventorymaster.Modules.ItemInstances;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.example.inventorymaster.Modules.Items.Item;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class ItemInstanceRecyclerAdapterTest {
    private ItemInstanceRecyclerAdapter adapter;

    @Before
    public void setUp() {
        Item mockItem = mock(Item.class);
        adapter = spy(new ItemInstanceRecyclerAdapter(mockItem));
        // Mock notify methods to avoid NullPointerException (no observers in unit test)
        doNothing().when(adapter).notifyItemRangeInserted(anyInt(), anyInt());
        doNothing().when(adapter).notifyItemRangeRemoved(anyInt(), anyInt());
        doNothing().when(adapter).notifyDataSetChanged();
    }

    @Test
    public void appendPageData_shouldIncreaseItemCount() {
        ItemInstance instance = new ItemInstance(1, 1, 101, 1, "B1");

        adapter.appendPageData(Collections.singletonList(instance));

        assertEquals(1, adapter.getItemCount());
        assertEquals(2, adapter.getPageToPull());
        verify(adapter).notifyItemRangeInserted(0, 1);
    }

    @Test
    public void clearData_shouldResetAdapter() {
        adapter.appendPageData(Collections.singletonList(new ItemInstance(1, 1, 101, 1, "B1")));

        adapter.clearData();

        assertEquals(0, adapter.getItemCount());
        assertEquals(1, adapter.getPageToPull());
        verify(adapter).notifyItemRangeRemoved(0, 1);
    }
}
