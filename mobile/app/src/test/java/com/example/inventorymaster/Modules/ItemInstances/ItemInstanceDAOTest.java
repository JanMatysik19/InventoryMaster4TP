package com.example.inventorymaster.Modules.ItemInstances;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.inventorymaster.Models.HttpClientModel;

import org.junit.Before;
import org.junit.Test;

import retrofit2.Call;

public class ItemInstanceDAOTest {
    private ItemInstanceDAO itemInstanceDAO;
    private ItemInstanceService mockItemInstanceService;

    @Before
    public void setUp() {
        HttpClientModel mockHttpClientModel = mock(HttpClientModel.class);
        mockItemInstanceService = mock(ItemInstanceService.class);
        when(mockHttpClientModel.createService(ItemInstanceService.class)).thenReturn(mockItemInstanceService);
        
        itemInstanceDAO = new ItemInstanceDAO(mockHttpClientModel);
    }

    @Test
    public void getItemInstances_shouldCallService() {
        Call<ItemInstanceDTO.GetItemInstancesResponse> mockCall = mock(Call.class);
        when(mockItemInstanceService.getItemInstances(anyInt(), anyInt())).thenReturn(mockCall);

        itemInstanceDAO.getItemInstances(1, 10, items -> {});

        verify(mockItemInstanceService).getItemInstances(1, 10);
        verify(mockCall).enqueue(any());
    }

    @Test
    public void getItemInstances_withSequence_shouldCallService() {
        Call<ItemInstanceDTO.GetItemInstancesResponse> mockCall = mock(Call.class);
        when(mockItemInstanceService.getItemInstances(anyInt(), anyInt(), anyInt())).thenReturn(mockCall);

        itemInstanceDAO.getItemInstances(1, 10, 100, items -> {});

        verify(mockItemInstanceService).getItemInstances(1, 10, 100);
        verify(mockCall).enqueue(any());
    }

    @Test
    public void addItemInstance_shouldCallService() {
        Call<Void> mockCall = mock(Call.class);
        when(mockItemInstanceService.addItemInstance(any())).thenReturn(mockCall);

        itemInstanceDAO.addItemInstance(10, result -> {});

        verify(mockItemInstanceService).addItemInstance(any(ItemInstanceDTO.AddItemInstanceRequest.class));
        verify(mockCall).enqueue(any());
    }

    @Test
    public void deleteItemInstance_shouldCallService() {
        Call<Void> mockCall = mock(Call.class);
        when(mockItemInstanceService.deleteItemInstance(anyInt())).thenReturn(mockCall);

        itemInstanceDAO.deleteItemInstance(1, result -> {});

        verify(mockItemInstanceService).deleteItemInstance(1);
        verify(mockCall).enqueue(any());
    }

    @Test
    public void moveItemInstance_shouldCallService() {
        Call<Void> mockCall = mock(Call.class);
        ItemInstanceDTO.MoveItemInstanceRequest request = new ItemInstanceDTO.MoveItemInstanceRequest(5);
        when(mockItemInstanceService.moveItemInstance(anyInt(), any())).thenReturn(mockCall);

        itemInstanceDAO.moveItemInstance(1, request, result -> {});

        verify(mockItemInstanceService).moveItemInstance(1, request);
        verify(mockCall).enqueue(any());
    }
}
