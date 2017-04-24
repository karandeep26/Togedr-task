package com.proj.togedr_task;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.proj.togedr_task.domain.DatabaseHandler;
import com.proj.togedr_task.network.Contacts;
import com.proj.togedr_task.network.ServerResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Contacts contacts;
    DatabaseHandler databaseHandler;
    DataAdapter dataAdapter;
    List<Contact> contactsList;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RestClient restClient = RestClient.getRestClient();
        Button button = (Button) findViewById(R.id.button);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                callApi();
            }
        });
        contacts = restClient.getRetrofitInstance().create(Contacts.class);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHandler = new DatabaseHandler(MainActivity.this);
        contactsList = new ArrayList<>();
        contactsList = databaseHandler.getAllContacts();
        dataAdapter = new DataAdapter(contactsList);
        recyclerView.setAdapter(dataAdapter);

    }

    void callApi() {
        contacts.getContacts().enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response != null) {
                    Log.d("Data", response.body().getContacts().toString());
                    List<Contact> tempList = response.body().getContacts();
                    if (databaseHandler.getContactsCount() > 0) {
                        contactsList = databaseHandler.getAllContacts();
                        dataAdapter.notifyDataSetChanged();
                    } else {
                        for (Contact contact : tempList) {
                            databaseHandler.addContact(contact);
                        }
                        contactsList.clear();
                        contactsList.addAll(databaseHandler.getAllContacts());
                        dataAdapter.notifyDataSetChanged();
                    }

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Some Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
