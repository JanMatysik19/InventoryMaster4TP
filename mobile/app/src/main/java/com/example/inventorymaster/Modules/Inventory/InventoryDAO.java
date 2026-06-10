package com.example.inventorymaster.Modules.Inventory;

import com.example.inventorymaster.Models.HttpClientModel;
import com.example.inventorymaster.Utils.CallbackBuilder;

public class InventoryDAO {
    private final InventoryService service;
    private final HttpClientModel httpClientModel;
    private final CallbackBuilder callbackBuilder;
    public InventoryDAO(HttpClientModel httpClientModel) {
        this.httpClientModel = httpClientModel;
        service = httpClientModel.createService(InventoryService.class);
        callbackBuilder = new CallbackBuilder(httpClientModel::handleError);
    }

    public void getBoxes(InventoryDTO.GetTotalBoxesResponse.IHandler handler) {
        service.getTotalBoxes().enqueue(
                callbackBuilder.<InventoryDTO.GetTotalBoxesResponse>call()
                        .onResponse(((call, response, body) -> {
                            if(body == null) return CallbackBuilder.CallbackStatus.UNSUCCESSFUL_REQUEST_ERROR;

                            final var result = body.data();

                            handler.take(result);
                            return CallbackBuilder.CallbackStatus.SUCCESS;
                        }))
                        .onFail((call, status) -> handler.take(0))
                        .build()
        );
    }

    public void getBoxes(int sequenceNumber, InventoryDTO.GetTotalBoxesResponse.IHandler handler) {
        service.getTotalBoxes(sequenceNumber).enqueue(
                callbackBuilder.<InventoryDTO.GetTotalBoxesResponse>call()
                        .onResponse(((call, response, body) -> {
                            if(body == null) return CallbackBuilder.CallbackStatus.UNSUCCESSFUL_REQUEST_ERROR;

                            final var result = body.data();

                            handler.take(result);
                            return CallbackBuilder.CallbackStatus.SUCCESS;
                        }))
                        .onFail((call, status) -> handler.take(0))
                        .build()
        );
    }

    public void getItems(InventoryDTO.GetTotalItemsResponse.IHandler handler) {
        service.getTotalItems().enqueue(
                callbackBuilder.<InventoryDTO.GetTotalItemsResponse>call()
                        .onResponse(((call, response, body) -> {
                            if(body == null) return CallbackBuilder.CallbackStatus.UNSUCCESSFUL_REQUEST_ERROR;

                            final var result = body.data();

                            handler.take(result);
                            return CallbackBuilder.CallbackStatus.SUCCESS;
                        }))
                        .onFail((call, status) -> handler.take(0))
                        .build()
        );
    }

    public void getItems(String search, InventoryDTO.GetTotalItemsResponse.IHandler handler) {
        service.getTotalItems(search).enqueue(
                callbackBuilder.<InventoryDTO.GetTotalItemsResponse>call()
                        .onResponse(((call, response, body) -> {
                            if(body == null) return CallbackBuilder.CallbackStatus.UNSUCCESSFUL_REQUEST_ERROR;

                            final var result = body.data();

                            handler.take(result);
                            return CallbackBuilder.CallbackStatus.SUCCESS;
                        }))
                        .onFail((call, status) -> handler.take(0))
                        .build()
        );
    }

    public void getItemInstances(int itemId, InventoryDTO.GetTotalItemInstancesResponse.IHandler handler) {
        service.getTotalItemInstances(itemId).enqueue(
                callbackBuilder.<InventoryDTO.GetTotalItemInstancesResponse>call()
                        .onResponse(((call, response, body) -> {
                            if(body == null) return CallbackBuilder.CallbackStatus.UNSUCCESSFUL_REQUEST_ERROR;

                            final var result = body.data();

                            handler.take(result);
                            return CallbackBuilder.CallbackStatus.SUCCESS;
                        }))
                        .onFail((call, status) -> handler.take(0))
                        .build()
        );
    }

    public void getItemInstances(int itemId, int sequenceNumber, InventoryDTO.GetTotalItemInstancesResponse.IHandler handler) {
        service.getTotalItemInstances(itemId, sequenceNumber).enqueue(
                callbackBuilder.<InventoryDTO.GetTotalItemInstancesResponse>call()
                        .onResponse(((call, response, body) -> {
                            if(body == null) return CallbackBuilder.CallbackStatus.UNSUCCESSFUL_REQUEST_ERROR;

                            final var result = body.data();

                            handler.take(result);
                            return CallbackBuilder.CallbackStatus.SUCCESS;
                        }))
                        .onFail((call, status) -> handler.take(0))
                        .build()
        );
    }

    public void getSummary(InventoryDTO.GetSummaryResponse.IHandler handler) {
        service.getSummary().enqueue(
                callbackBuilder.<InventoryDTO.GetSummaryResponse>call()
                        .onResponse(((call, response, body) -> {
                            if(body == null) return CallbackBuilder.CallbackStatus.UNSUCCESSFUL_REQUEST_ERROR;

                            final var boxes = body.boxes();
                            final var items = body.items();
                            final var itemInstances = body.itemInstances();
                            final var totalValue = body.value();

                            handler.take(boxes, items, itemInstances, totalValue);
                            return CallbackBuilder.CallbackStatus.SUCCESS;
                        }))
                        .onFail((call, status) -> handler.take(0, 0, 0, 0))
                        .build()
        );
    }
}
