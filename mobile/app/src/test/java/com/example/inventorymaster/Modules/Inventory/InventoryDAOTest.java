package com.example.inventorymaster.Modules.Inventory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.inventorymaster.Models.HttpClientModel;

import org.junit.Before;
import org.junit.Test;

import retrofit2.Call;

public class InventoryDAOTest {
    private InventoryDAO inventoryDAO;
    private InventoryService mockInventoryService;

    @Before
    public void setUp() {
        HttpClientModel mockHttpClientModel = mock(HttpClientModel.class);
        mockInventoryService = mock(InventoryService.class);
        when(mockHttpClientModel.createService(InventoryService.class)).thenReturn(mockInventoryService);
        
        inventoryDAO = new InventoryDAO(mockHttpClientModel);
    }

    @Test
    public void getBoxes_shouldCallService() {
        Call<InventoryDTO.GetTotalBoxesResponse> mockCall = mock(Call.class);
        when(mockInventoryService.getTotalBoxes()).thenReturn(mockCall);

        inventoryDAO.getBoxes(count -> {});

        verify(mockInventoryService).getTotalBoxes();
        verify(mockCall).enqueue(any());
    }

    @Test
    public void getBoxes_withSequence_shouldCallService() {
        Call<InventoryDTO.GetTotalBoxesResponse> mockCall = mock(Call.class);
        when(mockInventoryService.getTotalBoxes(123)).thenReturn(mockCall);

        inventoryDAO.getBoxes(123, count -> {});

        verify(mockInventoryService).getTotalBoxes(123);
        verify(mockCall).enqueue(any());
    }
}
