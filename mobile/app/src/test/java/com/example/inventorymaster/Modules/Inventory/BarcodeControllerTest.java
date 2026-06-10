package com.example.inventorymaster.Modules.Inventory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;

import com.example.inventorymaster.Modules.ItemInstances.ItemInstance;
import com.example.inventorymaster.Modules.ItemInstances.ItemInstanceDAO;
import com.example.inventorymaster.Modules.ItemInstances.ItemInstanceDTO;
import com.example.inventorymaster.Modules.Items.Item;
import com.example.inventorymaster.Modules.Items.ItemDAO;
import com.example.inventorymaster.Modules.Items.ItemDTO;
import com.journeyapps.barcodescanner.ScanOptions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import java.util.Collections;
import java.util.List;

public class BarcodeControllerTest {
    private BarcodeController barcodeController;
    private Context mockContext;
    private ItemDAO mockItemDAO;
    private ItemInstanceDAO mockItemInstanceDAO;
    private MockedStatic<Toast> mockedToast;

    @Before
    public void setUp() {
        mockContext = mock(Context.class);
        mockItemDAO = mock(ItemDAO.class);
        mockItemInstanceDAO = mock(ItemInstanceDAO.class);
        barcodeController = new BarcodeController(mockContext, mockItemDAO, mockItemInstanceDAO);
        mockedToast = mockStatic(Toast.class);
        
        Toast mockToast = mock(Toast.class);
        mockedToast.when(() -> Toast.makeText(any(), anyString(), anyInt())).thenReturn(mockToast);
    }

    @After
    public void tearDown() {
        mockedToast.close();
    }

    @Test
    public void launch_shouldCallLauncherWithCorrectOptions() {
        ActivityResultLauncher<ScanOptions> mockLauncher = mock(ActivityResultLauncher.class);
        
        barcodeController.launch(mockLauncher);
        
        verify(mockLauncher).launch(any(ScanOptions.class));
    }

    @Test
    public void handleResult_withInvalidFormat_shouldShowToast() {
        barcodeController.handleResult("ssssssssssssssssssssssssssssssssssssssssssssss");

        mockedToast.verify(() -> Toast.makeText(eq(mockContext), eq("Nieobsługiwany format kodu"), anyInt()));
    }

    @Test
    public void handleResult_withInvalidSequenceNumber_shouldShowToast() {
        barcodeController.handleResult("ABC-niecyferkowacyferkaii");

        mockedToast.verify(() -> Toast.makeText(eq(mockContext), eq("Nieobsługiwany format kodu"), anyInt()));
    }

    @Test
    public void handleResult_whenItemNotFound_shouldShowToast() {
        String contents = "ABC-123";
        doAnswer(invocation -> {
            ItemDTO.GetItemsResponse.IHandler handler = invocation.getArgument(2);
            handler.take(Collections.emptyList());
            return null;
        }).when(mockItemDAO).getItems(anyInt(), eq("ABC"), any());

        barcodeController.handleResult(contents);

        mockedToast.verify(() -> Toast.makeText(eq(mockContext), eq("Nie znaleziono artykułu o tym kodzie"), anyInt()));
    }

    @Test
    public void handleResult_whenItemFoundButCodeMismatch_shouldShowToast() {
        String contents = "ABC-123";
        Item item = new Item(1, "XYZ", "Desc", "10", 5);
        doAnswer(invocation -> {
            ItemDTO.GetItemsResponse.IHandler handler = invocation.getArgument(2);
            handler.take(List.of(item));
            return null;
        }).when(mockItemDAO).getItems(anyInt(), eq("ABC"), any());

        barcodeController.handleResult(contents);

        mockedToast.verify(() -> Toast.makeText(eq(mockContext), eq("Nie znaleziono artykułu o tym kodzie"), anyInt()));
    }

    @Test
    public void handleResult_whenInstanceNotFound_shouldShowToast() {
        String contents = "ABC-123";
        Item item = new Item(1, "ABC", "Desc", "10", 5);
        
        doAnswer(invocation -> {
            ItemDTO.GetItemsResponse.IHandler handler = invocation.getArgument(2);
            handler.take(List.of(item));
            return null;
        }).when(mockItemDAO).getItems(anyInt(), eq("ABC"), any());

        doAnswer(invocation -> {
            ItemInstanceDTO.GetItemInstancesResponse.IHandler handler = invocation.getArgument(3);
            handler.take(Collections.emptyList());
            return null;
        }).when(mockItemInstanceDAO).getItemInstances(anyInt(), eq(1), eq(123), any());

        barcodeController.handleResult(contents);

        mockedToast.verify(() -> Toast.makeText(eq(mockContext), eq("Nie znaleziono konkretnej instancji (numer: 123)"), anyInt()));
    }

    @Test
    public void handleResult_whenSuccess_shouldStartActivity() {
        String contents = "ABC-123";
        Item item = new Item(1, "ABC", "Desc", "10", 5);
        ItemInstance instance = new ItemInstance(1, 1, 123, 1, "BOX1");

        doAnswer(invocation -> {
            ItemDTO.GetItemsResponse.IHandler handler = invocation.getArgument(2);
            handler.take(List.of(item));
            return null;
        }).when(mockItemDAO).getItems(anyInt(), eq("ABC"), any());

        doAnswer(invocation -> {
            ItemInstanceDTO.GetItemInstancesResponse.IHandler handler = invocation.getArgument(3);
            handler.take(List.of(instance));
            return null;
        }).when(mockItemInstanceDAO).getItemInstances(anyInt(), eq(1), eq(123), any());

        try (MockedConstruction<Intent> mockedIntent = mockConstruction(Intent.class)) {
            barcodeController.handleResult(contents);

            mockedToast.verify(() -> Toast.makeText(eq(mockContext), eq("Pomyślnie zidentyfikowano artykuł: " + contents), anyInt()));
            verify(mockContext).startActivity(any(Intent.class));
            
            Intent constructed = mockedIntent.constructed().get(0);
            verify(constructed).putExtra("item", item);
            verify(constructed).putExtra("instance", instance);
        }
    }
}
