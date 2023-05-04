package com.example.freechain;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Article extends AppCompatActivity {

    ListView article_view;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        article_view = findViewById(R.id.article_view);

        load_data();

        article_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                hashMap = arrayList.get(i);
                String url = hashMap.get("article_url");
                Display_text.url = url;
                startActivity(new Intent(Article.this,Display_text.class));

            }
        });

    }

    private class Article_adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            view =  layoutInflater.inflate(R.layout.article_design,viewGroup,false);

            TextView header_text;
            header_text = view.findViewById(R.id.article_header);

            hashMap = arrayList.get(i);
            String title_n = hashMap.get("title");
            String article_u = hashMap.get("article_url");

            header_text.setText(title_n);

            

            return view;
        }
    }

    public void load_data(){

        String url = "https://artsbdlimited.com/apps/article.json";

        RequestQueue requestQueue = Volley.newRequestQueue(Article.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String title_name = jsonObject.getString("title");
                        String article_text = jsonObject.getString("article_url");

                        hashMap = new HashMap<>();
                        hashMap.put("title",title_name);
                        hashMap.put("article_url",article_text);
                        arrayList.add(hashMap);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                Article_adapter article_adapter = new Article_adapter();
                article_view.setAdapter(article_adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Article.this, "internet problem", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonArrayRequest);

    }
}