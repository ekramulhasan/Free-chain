package com.example.freechain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class Book_2 extends AppCompatActivity {

    RecyclerView recycler_view;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book2);



        recycler_view = findViewById(R.id.recycler_view);

        net_data();


    }

    public class Myadpater extends RecyclerView.Adapter<Myadpater.Myholder> {

        private class Myholder extends RecyclerView.ViewHolder{

            TextView book_name;
            Button read,download;
            ImageView book_img;
            public Myholder(@NonNull View itemView) {
                super(itemView);

                book_name = itemView.findViewById(R.id.book_name);
                book_img = itemView.findViewById(R.id.img_view);
                read = itemView.findViewById(R.id.read);
                download = itemView.findViewById(R.id.download);


            }
        }

        @NonNull
        @Override
        public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View layout_view = layoutInflater.inflate(R.layout.book_sample,parent,false);


            return new Myholder(layout_view);
        }

        @Override
        public void onBindViewHolder(@NonNull Myholder holder, int position) {

            hashMap = arrayList.get(position);
            String book_n = hashMap.get("book_name");
            String book_i = hashMap.get("book_img");
            String book_l = hashMap.get("book_link");
            String download_l = hashMap.get("download_link");


            Picasso.get().load(book_i).into(holder.book_img);
            holder.book_name.setText(book_n);

            holder.read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                Book.url = book_l;
                startActivity(new Intent(Book_2.this,Book.class));

                }
            });

            holder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    RequestQueue requestQueue = Volley.newRequestQueue(Book_2.this);

//                    StringRequest stringRequest = new StringRequest(Request.Method.GET, download_l, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//
//                            Toast.makeText(Book_2.this, "Downloading", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                            Toast.makeText(Book_2.this, "not internet", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//
//                    requestQueue.add(stringRequest);

                    download_start(download_l);
                    Toast.makeText(Book_2.this, "Downloading", Toast.LENGTH_SHORT).show();


                }
            });




        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }


    }

    void net_data(){

        RequestQueue requestQueue = Volley.newRequestQueue(Book_2.this);
        String url = "https://artsbdlimited.com/apps/book_list.json";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Toast.makeText(Book_2.this, "success", Toast.LENGTH_SHORT).show();


                    for (int i=0;i<response.length();i++){

                        try {
                          JSONObject  jsonObject = response.getJSONObject(i);

                            String b_name = jsonObject.getString("book_name");
                            String b_img = jsonObject.getString("book_img");
                            String b_link = jsonObject.getString("book_link");
                            String d_link =jsonObject.getString("download_link");

                            hashMap = new HashMap<>();
                            hashMap.put("book_name",b_name);
                            hashMap.put("book_img",b_img);
                            hashMap.put("book_link",b_link);
                            hashMap.put("download_link",d_link);
                            arrayList.add(hashMap);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                Myadpater myadpater = new Myadpater();
                recycler_view.setAdapter(myadpater);
                recycler_view.setLayoutManager(new LinearLayoutManager(Book_2.this));



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Book_2.this, "internet problem", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonArrayRequest);


    }

    void download_start(String url){

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");
        request.setDescription("Download file...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis());

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);

    }
}