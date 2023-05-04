package com.example.freechain;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Display_text extends AppCompatActivity {

    WebView display_article;

    public static String url = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_text);

        display_article = findViewById(R.id.display_article);

        display_article.loadUrl(url);
        display_article.getSettings().setJavaScriptEnabled(true);
        display_article.getSettings().setSupportZoom(true);
        display_article.getSettings().setDisplayZoomControls(false);
        display_article.getSettings().setBuiltInZoomControls(true);
        display_article.setWebViewClient(new WebViewClient());


        display_article.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String user_agent, String s2, String s3, long l) {

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setTitle("Download");
                request.setDescription("Downloading your file");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis());

                DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                dm.enqueue(request);

                Toast.makeText(Display_text.this, "Downloading", Toast.LENGTH_SHORT).show();


            }
        });


    }

    @Override
    public void onBackPressed() {


        if (display_article.canGoBack()){
            display_article.goBack();
        }
        else {
            finish();
        }
    }
}