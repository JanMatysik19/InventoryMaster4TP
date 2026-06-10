package com.example.inventorymaster.Modules.Items;

import com.example.inventorymaster.Models.HttpClientModel;
import com.example.inventorymaster.Utils.CallbackBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ItemDAO {
    private final ItemService service;
    private final CallbackBuilder callbackBuilder;
    
    public ItemDAO(HttpClientModel httpClientModel) {
        service = httpClientModel.createService(ItemService.class);
        callbackBuilder = new CallbackBuilder(httpClientModel::handleError);
    }

    public void getItems(int page, ItemDTO.GetItemsResponse.IHandler handler) {
        service.getItems(page).enqueue(
                callbackBuilder.<ItemDTO.GetItemsResponse>call()
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

    public void getItems(int page, String search, ItemDTO.GetItemsResponse.IHandler handler) {
        service.getItems(page, search).enqueue(
                callbackBuilder.<ItemDTO.GetItemsResponse>call()
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

    public void getItem(int id, ItemDTO.GetItemResponse.IHandler handler) {
        service.getItem(id).enqueue(
                callbackBuilder.<ItemDTO.GetItemResponse>call()
                        .onResponse(((call, response, body) -> {
                            if(body == null) return CallbackBuilder.CallbackStatus.UNSUCCESSFUL_REQUEST_ERROR;

                            final var result = body.data();
                            if(result == null) return CallbackBuilder.CallbackStatus.EMPTY_RESULT_ERROR;

                            handler.take(result);
                            return CallbackBuilder.CallbackStatus.SUCCESS;
                        }))
                        .onFail((call, status) -> handler.take(null))
                        .build()
        );
    }

    public void addItem(ItemDTO.AddItemRequest data, Consumer<Boolean> handler) {
        service.addItem(data).enqueue(
                callbackBuilder.<Void>call()
                        .onResponse(((call, response, body) -> {
                            handler.accept(true);
                            return CallbackBuilder.CallbackStatus.SUCCESS;
                        }))
                        .onFail((call, status) -> handler.accept(false))
                        .build()
        );
    }

    public void deleteItem(int id, Consumer<Boolean> handler) {
        service.deleteItem(id).enqueue(
                callbackBuilder.<Void>call()
                        .onResponse(((call, response, body) -> {
                            handler.accept(true);
                            return CallbackBuilder.CallbackStatus.SUCCESS;
                        }))
                        .onFail((call, status) -> handler.accept(false))
                        .build()
        );
    }

    public void updateItem(int id, ItemDTO.UpdateItemRequest request, Consumer<Boolean> handler) {
        service.updateItem(id, request).enqueue(
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
