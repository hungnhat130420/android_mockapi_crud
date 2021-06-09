package com.example.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddFormActivity extends AppCompatActivity {
    TextView txtAddID, txtAddName, txtAddEmail, txtAddPass;
    String url = "https://60b094a61f26610017ffe811.mockapi.io/Users";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);

        txtAddID = (TextView) findViewById(R.id.txtAddID);
        txtAddEmail = (TextView) findViewById(R.id.txtAddMail);
        txtAddName = (TextView) findViewById(R.id.txtAddName);
        txtAddPass = (TextView) findViewById(R.id.txtAddPass);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData(url);
            }
        });

    }

    private void postData(String url) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", txtAddName.getText().toString());
            jsonObject.put("email", txtAddEmail.getText().toString());
            jsonObject.put("password", txtAddPass.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                response -> {
                    Log.d("A", response.toString());
                    Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error when POST data", Toast.LENGTH_SHORT).show();
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}
