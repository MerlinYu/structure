package com.structure.internet;

import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;

import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

/**
 * Created by yuchao.
 */
public class StructureClient implements Client {

    private OkHttpClient client;
    public StructureClient(OkHttpClient client){
        this.client = client;
    }



    @Override
    public Response execute(Request request) throws IOException {
        return null;
    }

}
