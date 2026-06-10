package com.example.inventorymaster.Utils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallbackBuilderTest {
    private CallbackBuilder callbackBuilder;
    private Consumer<CallbackBuilder.CallbackStatus> mockErrorHandler;

    @Before
    public void setUp() {
        mockErrorHandler = mock(Consumer.class);
        callbackBuilder = new CallbackBuilder(mockErrorHandler);
    }

    @Test
    public void onResponse_whenSuccessful_shouldNotCallErrorHandler() {
        Callback<String> callback = callbackBuilder.<String>call()
                .onResponse((call, response, body) -> CallbackBuilder.CallbackStatus.SUCCESS)
                .build();

        Call<String> mockCall = mock(Call.class);
        Response<String> mockResponse = Response.success("OK");

        callback.onResponse(mockCall, mockResponse);

        verify(mockErrorHandler, never()).accept(any());
    }

    @Test
    public void onResponse_whenStatusIsError_shouldCallErrorHandler() {
        Callback<String> callback = callbackBuilder.<String>call()
                .onResponse((call, response, body) -> CallbackBuilder.CallbackStatus.UNSUCCESSFUL_REQUEST_ERROR)
                .build();

        Call<String> mockCall = mock(Call.class);
        Response<String> mockResponse = Response.success("OK");

        callback.onResponse(mockCall, mockResponse);

        verify(mockErrorHandler).accept(CallbackBuilder.CallbackStatus.UNSUCCESSFUL_REQUEST_ERROR);
    }

    @Test
    public void onFailure_shouldCallErrorHandlerWithOtherError() {
        Callback<String> callback = callbackBuilder.<String>call().build();
        Call<String> mockCall = mock(Call.class);
        Throwable mockThrowable = new RuntimeException("Network error");

        callback.onFailure(mockCall, mockThrowable);

        verify(mockErrorHandler).accept(CallbackBuilder.CallbackStatus.OTHER_ERROR);
    }
}
