package com.example.inventorymaster.Utils;

import com.example.inventorymaster.DataModels.Box;
import com.example.inventorymaster.DataModels.Category;
import com.example.inventorymaster.DataModels.Item;
import com.example.inventorymaster.DataModels.ItemInstance;
import com.example.inventorymaster.Retrofit.Boxes.BoxResponse;
import com.example.inventorymaster.Retrofit.Boxes.BoxService;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Consumer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    public static final String API_URL = "http://localhost:9000/";
    public static final String UNSUCCESSFUL_REQUEST = "Zapytanie do API nie powiodło się";
    public static final String NO_RESULTS_FOUND = "Brak wyników";
    public static final String OTHER_ISSUE = "Zapytanie do API nie powiodło się - sprawdź swoje połączenie internetowe";

    private Consumer<String> errorHandler;
    public final Retrofit retrofit;

    public HttpClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> T createService(Class<T> serviceClass) {
        return (T) retrofit.create(serviceClass.getClass());
    }

    public void errorHandle(String error) {
        if(errorHandler != null) errorHandler.accept(error);
    }

    public void setErrorHandler(Consumer<String> handler) {
        errorHandler = handler;
    }

}
