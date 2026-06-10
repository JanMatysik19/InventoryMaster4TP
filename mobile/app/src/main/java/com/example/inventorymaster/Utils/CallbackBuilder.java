package com.example.inventorymaster.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallbackBuilder {
    private final Consumer<CallbackStatus> errorHandler;

    public CallbackBuilder(Consumer<CallbackStatus> errorHandler) {
        this.errorHandler = errorHandler;
    }

    public <T> Temporary<T> call() {
        return new Temporary<>(errorHandler);
    }

    public static class Temporary<T> {
        private IOnResponse<T> onResponseHandler;
        private IOnFailure<T> onFailureHandler;
        private final Consumer<CallbackStatus> errorHandler;

        public Temporary(Consumer<CallbackStatus> errorHandler) {
            this.errorHandler = errorHandler;
            onResponseHandler = (call, response, body) -> CallbackStatus.SUCCESS;
            onFailureHandler = (call, status) -> {};
        }

        public Temporary<T> onResponse(IOnResponse<T> onResponseHandler) {
            this.onResponseHandler = onResponseHandler;
            return this;
        }

        public Temporary<T> onFail(IOnFailure<T> onFailureHandler) {
            this.onFailureHandler = onFailureHandler;
            return this;
        }

        public Callback<T> build() {
            return new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {

                    if(!response.isSuccessful()) {
                        errorHandler.accept(CallbackStatus.UNSUCCESSFUL_REQUEST_ERROR);
                        onFailureHandler.onFailure(call, CallbackStatus.UNSUCCESSFUL_REQUEST_ERROR);
                        return;
                    }

                    final var status = onResponseHandler.onResponse(call, response, response.body());
                    if(status != CallbackStatus.SUCCESS) {
                        errorHandler.accept(status);
                        onFailureHandler.onFailure(call, status);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                    errorHandler.accept(CallbackStatus.OTHER_ERROR);
                    onFailureHandler.onFailure(call, CallbackStatus.OTHER_ERROR);
                }
            };
        }

        @FunctionalInterface
        public interface IOnResponse<T> {
            CallbackStatus onResponse(@NonNull Call<T> call, @NonNull Response<T> response, @Nullable T body);
        }

        @FunctionalInterface
        public interface IOnFailure<T> {
            void onFailure(@NonNull Call<T> call, @NonNull CallbackStatus status);
        }
    }

    public enum CallbackStatus {
        // Error
        EMPTY_RESULT_ERROR("Puste"),
        NOT_FOUND_ERROR("Nie znaleziono"),
        UNSUCCESSFUL_REQUEST_ERROR("Nieudane zapytanie do API"),
        OTHER_ERROR("Problem z zapytaniem do API - sprawdź połączenie internetowe"),

        // Success
        SUCCESS("Zapytanie zakończone pomyślnie");

        private final String message;
        CallbackStatus(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
