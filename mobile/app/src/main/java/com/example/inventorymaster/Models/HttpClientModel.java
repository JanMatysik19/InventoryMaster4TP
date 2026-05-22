package com.example.inventorymaster.Models;

import android.util.Log;

import com.example.inventorymaster.BuildConfig;
import java.util.function.Consumer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClientModel {
    public static final String API_URL = BuildConfig.API_URL;
    public static final String UNSUCCESSFUL_REQUEST = "Zapytanie do API nie powiodło się";
    public static final String NO_RESULTS_FOUND = "Brak wyników";
    public static final String OTHER_ISSUE = "Zapytanie do API nie powiodło się - sprawdź swoje połączenie internetowe";

    private Consumer<String> errorHandler;
    public final Retrofit retrofit;

    public HttpClientModel() {
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        setErrorHandler((err) -> {
            Log.println(Log.ERROR, "HTTP CLIENT", err);
        });
    }

    public <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public void errorHandle(String error) {
        if(errorHandler != null) errorHandler.accept(error);
    }

    public void setErrorHandler(Consumer<String> handler) {
        errorHandler = handler;
    }

}
