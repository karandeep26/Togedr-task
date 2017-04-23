package com.proj.togedr_task;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.proj.togedr_task.domain.DatabaseHandler;
import com.proj.togedr_task.network.Contacts;
import com.proj.togedr_task.network.ServerResponse;

import org.greenrobot.greendao.database.Database;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RestClient restClient=RestClient.getRestClient();
        Contacts contacts=restClient.getRetrofitInstance().create(Contacts.class);
       contacts.getContacts().enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response!=null){
                    Log.d("Data",response.body().getContacts().toString());
                    DatabaseHandler databaseHandler=new DatabaseHandler(MainActivity.this);
                    List<Contact> contactsList=response.body().getContacts();
                    for(Contact contact:contactsList){
                        databaseHandler.addContact(contact);
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });

    }
}
