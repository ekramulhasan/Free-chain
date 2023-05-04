package com.example.freechain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class Website extends AppCompatActivity {

    ListView web_site;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);


        web_site = findViewById(R.id.web_site);

        load_data();

        web_site.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                hashMap = arrayList.get(i);
                String web_url = hashMap.get("website_url");
                Display_text.url = web_url;
                startActivity(new Intent(Website.this,Display_text.class));

            }
        });



    }

    private class Webadapter extends BaseAdapter {
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
            view = layoutInflater.inflate(R.layout.website_style,viewGroup,false);

            TextView textView;
            textView = view.findViewById(R.id.web_title);

            hashMap = arrayList.get(i);
            String web_title =  hashMap.get("website_title");

            textView.setText(web_title);

            Animation anim;
            anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.left_to_right);
            view.setAnimation(anim);
            return view;
        }
    }

    void load_data(){
        String url = "https://artsbdlimited.com/apps/website.json";

        RequestQueue queue = Volley.newRequestQueue(Website.this);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i=0;i<response.length();i++){

                    try {
                        JSONObject object = response.getJSONObject(i);
                        String site_title = object.getString("website_title");
                        String site_url = object.getString("website_url");

                        hashMap = new HashMap<>();
                        hashMap.put("website_title",site_title);
                        hashMap.put("website_url",site_url);
                        arrayList.add(hashMap);


                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }


                Webadapter webadapter = new Webadapter();
                web_site.setAdapter(webadapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Website.this, "internet problem", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(arrayRequest);
    }
}