package com.example.freechain;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class video_player extends YouTubeBaseActivity {

    YouTubePlayerView youtube_view;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayer youTubePlayer_;
    final String API_KEY = "AIzaSyDA81hdR9KREhpcIt4ku1elfkVskNQmTD8";
    final String video_code = "8iAT_Dadhj4";

    ListView video_list;
    Button play;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);


        youtubePlayerSetup();
        video_list = findViewById(R.id.video_list);

        data_load();


        video_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("Range")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                hashMap = arrayList.get(i);
                String video_code = hashMap.get("video_id");
                youTubePlayer_.cueVideo(video_code);


            }
        });


    }

    private class Video_adapter extends BaseAdapter {
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
            view = layoutInflater.inflate(R.layout.video_layout,viewGroup,false);

            ImageView thmb;
            TextView title;

            thmb = view.findViewById(R.id.thumbnail);
            title = view.findViewById(R.id.video_title);

            hashMap = arrayList.get(i);
            String video_image = hashMap.get("video_img");
            String video_title2 = hashMap.get("video_title");

            title.setText(video_title2);
            Picasso.get().load(video_image).into(thmb);



            return view;
        }
    }

    void data_load(){


        String url = "https://artsbdlimited.com/apps/video_list.json";

        RequestQueue requestQueue2 = Volley.newRequestQueue(video_player.this);

        JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i=0;i<response.length();i++){

                    try {

                        JSONObject jsonObject = response.getJSONObject(i);
                        String video_i = jsonObject.getString("video_img");
                        String video_t = jsonObject.getString("video_title");
                        String video_Id = jsonObject.getString("video_id");

                        hashMap = new HashMap<>();
                        hashMap.put("video_img",video_i);
                        hashMap.put("video_title",video_t);
                        hashMap.put("video_id",video_Id);
                        arrayList.add(hashMap);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }

                Video_adapter video_adapter = new Video_adapter();
                video_list.setAdapter(video_adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(video_player.this, "internet problem", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue2.add(jsonArrayRequest2);
    }

    public void youtubePlayerSetup(){

        youtube_view = findViewById(R.id.youtube_view);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                if (!b){
                    youTubePlayer_ = youTubePlayer;
                    youTubePlayer_.cueVideo(video_code);
                    youTubePlayer_.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                Toast.makeText(video_player.this, "internet problem", Toast.LENGTH_SHORT).show();

            }

        };

        youtube_view.initialize(API_KEY,onInitializedListener);

    }
}