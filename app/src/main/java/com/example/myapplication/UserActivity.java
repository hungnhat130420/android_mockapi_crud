package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    ArrayAdapter adapter;
    String url = "https://60b094a61f26610017ffe811.mockapi.io/Users";
    EditText findEmail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<JSONObject>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        getData(url);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnFind = (Button) findViewById(R.id.btnFind);
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);

        EditText findID = (EditText) findViewById(R.id.findID);
        findEmail = (EditText) findViewById(R.id.findEmail);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(url, findID.getText().toString());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putData(url, findID.getText().toString());
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(url,findID.getText().toString());
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(UserActivity.this,AddFormActivity.class);
                startActivity(intent);
            }
        });


    }

    private void getData(String url) {
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(
                        Request.Method.GET, url, null,
                        response -> {
                            adapter.clear();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    adapter.add(response.get(i));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        error -> {
                            Toast.makeText(this, "Error when get data", Toast.LENGTH_SHORT).show();
                        }
                );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void getData(String url, String id) {
        if (id.isEmpty()) {
            getData(url);
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url + "/" + id, null,
                response -> {
                    adapter.clear();
                    adapter.add(response);
                },
                error -> {
                    Toast.makeText(this, "Error when get data", Toast.LENGTH_SHORT).show();
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void putData(String url, String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", findEmail.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT, url + "/" + id, jsonObject,
                response -> {
                    Log.d("A", response.toString());
                    Toast.makeText(this, "Update thành công!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error when put data", Toast.LENGTH_SHORT).show();
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void deleteData(String url, String id) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE, url + "/" + id, null,
                response -> {
                    Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error when DELETE data", Toast.LENGTH_SHORT).show();
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


}
