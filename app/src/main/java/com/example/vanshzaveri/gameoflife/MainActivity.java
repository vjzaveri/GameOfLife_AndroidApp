package com.example.vanshzaveri.gameoflife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GameGrid gameGrid = (GameGrid) findViewById(R.id.grid_view);
        Button next = (Button)findViewById(R.id.next);
        Button reset = (Button)findViewById(R.id.reset);

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gameGrid.next();
            }
        });

        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("Vansh","in reset main");
                gameGrid.resetGame();
            }
        });

    }


}
