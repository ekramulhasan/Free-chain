package com.example.freechain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GridLayout grid_layout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid_layout = findViewById(R.id.grid_layout);

        setItemClick(grid_layout);

    }



    private void setItemClick(GridLayout grid_layout) {

        for (int i=0;i<grid_layout.getChildCount();i++){

              CardView cardView = (CardView) grid_layout.getChildAt(i);
              final int position = i;

              cardView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                      if ( position == 0){

                          Toast.makeText(MainActivity.this, "this is book", Toast.LENGTH_SHORT).show();
                          startActivity(new Intent(MainActivity.this,Book_2.class));
                      } else if (position == 1) {

                          Toast.makeText(MainActivity.this, "this is video", Toast.LENGTH_SHORT).show();
                          startActivity(new Intent(MainActivity.this,video_player.class));

                      }
                      else if (position == 2) {

                          Toast.makeText(MainActivity.this, "this is article", Toast.LENGTH_SHORT).show();
                          startActivity(new Intent(MainActivity.this,Article.class));

                      }
                      else if (position == 3) {

                          Toast.makeText(MainActivity.this, "this is website", Toast.LENGTH_SHORT).show();
                          startActivity(new Intent(MainActivity.this,Website.class));
                      }

                  }
              });

        }


    }
}