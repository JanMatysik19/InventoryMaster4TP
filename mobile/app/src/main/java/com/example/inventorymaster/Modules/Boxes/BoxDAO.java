package com.example.inventorymaster.Modules.Boxes;

import com.example.inventorymaster.Models.HttpClientModel;
import com.example.inventorymaster.Utils.CallbackBuilder;

import java.util.List;
import java.util.function.Consumer;

public class BoxDAO {
    private final BoxService service;
    private final HttpClientModel httpClientModel;
    private final CallbackBuilder callbackBuilder;
    public BoxDAO(HttpClientModel httpClientModel) {
        this.httpClientModel = httpClientModel;
        service = httpClientModel.createService(BoxService.class);
        callbackBuilder = new CallbackBuilder(httpClientModel::handleError);
    }

    public void getBoxes(int page, BoxDTO.GetBoxesResponse.IHandler handler) {
        service.getBoxes(page).enqueue(
                callbackBuilder.<BoxDTO.GetBoxesResponse>call()
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

    public void getBoxes(int page, int sequenceNumber, BoxDTO.GetBoxesResponse.IHandler handler) {
        service.getBoxes(page, sequenceNumber).enqueue(
                callbackBuilder.<BoxDTO.GetBoxesResponse>call()
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

    public void getBox(int id, BoxDTO.GetBoxResponse.IHandler handler) {
        service.getBox(id).enqueue(
                callbackBuilder.<BoxDTO.GetBoxResponse>call()
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

    public void deleteBox(int id, Consumer<Boolean> handler) {
        service.deleteBox(id).enqueue(
                callbackBuilder.<Void>call()
                .onResponse(((call, response, body) -> {
                    handler.accept(true);
                    return CallbackBuilder.CallbackStatus.SUCCESS;
                }))
                .onFail((call, status) -> handler.accept(false))
                .build()
        );
    }
    
    public void addBox(Consumer<Boolean> handler) {
        service.addBox().enqueue(
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
