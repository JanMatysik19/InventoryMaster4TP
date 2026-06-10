package com.example.inventorymaster.Modules.Items;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.inventorymaster.Models.HttpClientModel;

import org.junit.Before;
import org.junit.Test;

import retrofit2.Call;

public class ItemDAOTest {
    private ItemDAO itemDAO;
    private ItemService mockItemService;
    private HttpClientModel mockHttpClientModel;

    @Before
    public void setUp() {
        mockHttpClientModel = mock(HttpClientModel.class);
        mockItemService = mock(ItemService.class);
        when(mockHttpClientModel.createService(ItemService.class)).thenReturn(mockItemService);
        
        itemDAO = new ItemDAO(mockHttpClientModel);
    }

    @Test
    public void getItems_shouldCallService() {
        Call<ItemDTO.GetItemsResponse> mockCall = mock(Call.class);
        when(mockItemService.getItems(anyInt())).thenReturn(mockCall);

        itemDAO.getItems(1, items -> {});

        verify(mockItemService).getItems(1);
        verify(mockCall).enqueue(any());
    }

    @Test
    public void getItems_withSearch_shouldCallService() {
        Call<ItemDTO.GetItemsResponse> mockCall = mock(Call.class);
        when(mockItemService.getItems(anyInt(), anyString())).thenReturn(mockCall);

        itemDAO.getItems(1, "search", items -> {});

        verify(mockItemService).getItems(1, "search");
        verify(mockCall).enqueue(any());
    }

    @Test
    public void getItem_shouldCallService() {
        Call<ItemDTO.GetItemResponse> mockCall = mock(Call.class);
        when(mockItemService.getItem(anyInt())).thenReturn(mockCall);

        itemDAO.getItem(1, item -> {});

        verify(mockItemService).getItem(1);
        verify(mockCall).enqueue(any());
    }

    @Test
    public void addItem_shouldCallService() {
        Call<Void> mockCall = mock(Call.class);
        ItemDTO.AddItemRequest request = new ItemDTO.AddItemRequest("code", "desc", "10");
        when(mockItemService.addItem(any())).thenReturn(mockCall);

        itemDAO.addItem(request, result -> {});

        verify(mockItemService).addItem(request);
        verify(mockCall).enqueue(any());
    }

    @Test
    public void deleteItem_shouldCallService() {
        Call<Void> mockCall = mock(Call.class);
        when(mockItemService.deleteItem(anyInt())).thenReturn(mockCall);

        itemDAO.deleteItem(1, result -> {});

        verify(mockItemService).deleteItem(1);
        verify(mockCall).enqueue(any());
    }

    @Test
    public void updateItem_shouldCallService() {
        Call<Void> mockCall = mock(Call.class);
        ItemDTO.UpdateItemRequest request = new ItemDTO.UpdateItemRequest("code", "desc", "10");
        when(mockItemService.updateItem(anyInt(), any())).thenReturn(mockCall);

        itemDAO.updateItem(1, request, result -> {});

        verify(mockItemService).updateItem(1, request);
        verify(mockCall).enqueue(any());
    }
}
