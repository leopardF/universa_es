package com.leopard.universa.utils;

import okhttp3.*;

import java.io.IOException;

public class HttpUtil {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    public static String doGet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        try {
            return response.body().string();
        } finally {
            if (response != null) {
                if (response.body() != null) {
                    response.body().close();
                }
                response.close();
            }
        }
    }

    public static String doPost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        try {
            return response.body().string();
        } finally {
            if (response != null) {
                if (response.body() != null) {
                    response.body().close();
                }
                response.close();
            }
        }
    }

}
