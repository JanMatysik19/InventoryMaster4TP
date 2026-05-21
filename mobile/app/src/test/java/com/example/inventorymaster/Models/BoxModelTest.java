package com.example.inventorymaster.Models;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.inventorymaster.Retrofit.Boxes.BoxResponse;
import com.example.inventorymaster.Retrofit.Boxes.BoxService;
import com.example.inventorymaster.Utils.HttpClient;

import org.junit.Before;
import org.junit.Test;

import retrofit2.Call;

public class BoxModelTest {
    private BoxModel boxModel;
    private BoxService mockBoxService;
    private Call<BoxResponse.Many> mockCall;

    @Before
    public void setUp() {
        HttpClient mockHttpClient = mock(HttpClient.class);
        mockBoxService = mock(BoxService.class);
        mockCall = mock(Call.class);

        when(mockHttpClient.createService(BoxService.class)).thenReturn(mockBoxService);
        when(mockBoxService.getBoxes()).thenReturn(mockCall);

        boxModel = new BoxModel(mockHttpClient);
    }

    @Test
    public void getBoxes_shouldCallService() {
        // When
        boxModel.getBoxes(boxes -> {});

        // Then
        verify(mockBoxService).getBoxes();
        verify(mockCall).enqueue(any());
    }

    @Test
    public void addBox_shouldCallService() {
        // Given
        Call<Void> mockAddCall = mock(Call.class);
        when(mockBoxService.addBox()).thenReturn(mockAddCall);

        // When
        boxModel.addBox(result -> {});

        // Then
        verify(mockBoxService).addBox();
        verify(mockAddCall).enqueue(any());
    }

    @Test
    public void getBox_shouldCallService() {
        // Given
        Call<BoxResponse.One> mockOneCall = mock(Call.class);
        when(mockBoxService.getBox(1)).thenReturn(mockOneCall);

        // When
        boxModel.getBox(1, box -> {});

        // Then
        verify(mockBoxService).getBox(1);
        verify(mockOneCall).enqueue(any());
    }

    @Test
    public void deleteBox_shouldCallService() {
        // Given
        Call<Void> mockDeleteCall = mock(Call.class);
        when(mockBoxService.deleteBox(1)).thenReturn(mockDeleteCall);

        // When
        boxModel.deleteBox(1, result -> {});

        // Then
        verify(mockBoxService).deleteBox(1);
        verify(mockDeleteCall).enqueue(any());
    }
}
