package com.example.inventorymaster.Modules.ItemInstances;

import com.example.inventorymaster.Models.HttpClientModel;
import com.example.inventorymaster.Utils.CallbackBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ItemInstanceDAO {
    private final ItemInstanceService service;
    private final HttpClientModel httpClientModel;
    private final CallbackBuilder callbackBuilder;

    public ItemInstanceDAO(HttpClientModel httpClientModel) {
        this.httpClientModel = httpClientModel;
        service = httpClientModel.createService(ItemInstanceService.class);
        callbackBuilder = new CallbackBuilder(httpClientModel::handleError);
    }

    public void getItemInstances(int page, int itemId, ItemInstanceDTO.GetItemInstancesResponse.IHandler handler) {
        service.getItemInstances(page, itemId).enqueue(
                callbackBuilder.<ItemInstanceDTO.GetItemInstancesResponse>call()
                        .onResponse(((call, response, body) -> {
                            if(body == null) return CallbackBuilder.CallbackStatus.UNSUCCESSFUL_REQUEST_ERROR;

                            final var result = body.data();
                            if(result == null || result.isEmpty()) return CallbackBuilder.CallbackStatus.EMPTY_RESULT_ERROR;

                            handler.take(result);
                            return CallbackBuilder.CallbackStatus.SUCCESS;
                        }))
                        .onFail((call, status) -> handler.take(List.of()))
                        .build()
        );
    }

    public void getItemInstances(int page, int itemId, int sequenceNumber, ItemInstanceDTO.GetItemInstancesResponse.IHandler handler) {
        service.getItemInstances(page, itemId, sequenceNumber).enqueue(
                callbackBuilder.<ItemInstanceDTO.GetItemInstancesResponse>call()
                        .onResponse(((call, response, body) -> {
                            if(body == null) return CallbackBuilder.CallbackStatus.UNSUCCESSFUL_REQUEST_ERROR;

                            final var result = body.data();
                            if(result == null || result.isEmpty()) return CallbackBuilder.CallbackStatus.EMPTY_RESULT_ERROR;

                            handler.take(result);
                            return CallbackBuilder.CallbackStatus.SUCCESS;
                        }))
                        .onFail((call, status) -> handler.take(List.of()))
                        .build()
        );
    }

    public void addItemInstance(int itemId, Consumer<Boolean> handler) {
        service.addItemInstance(new ItemInstanceDTO.AddItemInstanceRequest(itemId)).enqueue(
                callbackBuilder.<Void>call()
                        .onResponse(((call, response, body) -> {
                            handler.accept(true);
                            return CallbackBuilder.CallbackStatus.SUCCESS;
                        }))
                        .onFail((call, status) -> handler.accept(false))
                        .build()
        );
    }

    public void deleteItemInstance(int id, Consumer<Boolean> handler) {
        service.deleteItemInstance(id).enqueue(
                callbackBuilder.<Void>call()
                        .onResponse(((call, response, body) -> {
                            handler.accept(true);
                            return CallbackBuilder.CallbackStatus.SUCCESS;
                        }))
                        .onFail((call, status) -> handler.accept(false))
                        .build()
        );
    }

    public void moveItemInstance(int id, ItemInstanceDTO.MoveItemInstanceRequest request, Consumer<Boolean> handler) {
        service.moveItemInstance(id, request).enqueue(
                callbackBuilder.<Void>call()
                        .onResponse(((call, response, body) -> {
                            handler.accept(true);
                            return CallbackBuilder.CallbackStatus.SUCCESS;
                        }))
                        .onFail((call, status) -> handler.accept(false))
                        .build()
        );
    }
}
