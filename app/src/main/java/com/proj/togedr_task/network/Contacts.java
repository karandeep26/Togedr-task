package com.proj.togedr_task.network;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by karan on 23/4/17.
 */

public interface Contacts {
    @GET("/contacts/")
    Call<ServerResponse> getContacts();
}
