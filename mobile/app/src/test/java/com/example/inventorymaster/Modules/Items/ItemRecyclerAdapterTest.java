package com.example.inventorymaster.Modules.Items;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemRecyclerAdapterTest {
    private ItemRecyclerAdapter adapter;

    @Before
    public void setUp() {
        adapter = spy(new ItemRecyclerAdapter());

        doNothing().when(adapter).notifyItemRangeInserted(anyInt(), anyInt());
        doNothing().when(adapter).notifyItemRangeRemoved(anyInt(), anyInt());
    }

    @Test
    public void appendPageData_shouldIncreaseItemCount() {
        List<Item> items = Arrays.asList(
                new Item(1, "A1", "Desc1", "10", 5),
                new Item(2, "A2", "Desc2", "20", 3)
        );

        adapter.appendPageData(items);

        assertEquals(2, adapter.getItemCount());
        assertEquals(2, adapter.getPageToPull()); // pageCounter: 0 -> 1 -> 2
        verify(adapter).notifyItemRangeInserted(0, 2);
    }

    @Test
    public void appendPageData_withEmptyList_shouldDoNothing() {
        adapter.appendPageData(Collections.emptyList());

        assertEquals(0, adapter.getItemCount());
        assertEquals(1, adapter.getPageToPull());
    }

    @Test
    public void clearData_shouldResetAdapter() {
        adapter.appendPageData(Collections.singletonList(new Item(1, "A1", "D1", "10", 1)));
        
        adapter.clearData();

        assertEquals(0, adapter.getItemCount());
        assertEquals(1, adapter.getPageToPull());
        verify(adapter).notifyItemRangeRemoved(0, 1);
    }
}
